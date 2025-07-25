import Util.ApiRequestHelper;
import apis.CreateBookingApi;
import apis.DeleteBookingApi;
import apis.GetBookingApi;
import apis.UpdateBookingApi;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.equalTo;

public class CRUDTests extends BaseTest{

    @Test(description = "CRUD Opreation",dataProvider = "bookingDataWithForLoop")
    public void curdTest(String firstname,String lastname,Boolean depositPaid,
                         String addtionalNeeds,Long totalPrice,String checkIndate,String checkOutDate){

        var createBookingApi = new CreateBookingApi();
        var getBookingApi = new GetBookingApi();
        var updateBookingApi = new UpdateBookingApi();
        var deleteBookingApi = new DeleteBookingApi();

        //create booking step1
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest(firstname,lastname,Math.toIntExact(totalPrice),
                depositPaid,addtionalNeeds,checkIndate,checkOutDate);
        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                                                        .then().assertThat().statusCode(200)
                                                        .and().body("bookingid", is(not(equalTo(0))));

        //extarct boooking id from create booking
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");



        //retrieve booking -step2
        var getBookingByIdResponse = getBookingApi.getBookingsById(bookingId);

       this.validateRetrievedBookingDataFromGetApi(firstname, lastname, depositPaid, addtionalNeeds, totalPrice, checkIndate, checkOutDate, getBookingByIdResponse);

        //update booking - step3
        //replace few details in create booking payload and send it as request payload to update api
       var updatedLastname = this.faker.name().lastName();
       var updatedTotalPrice = Math.toIntExact(this.faker.number().randomNumber(3,true));
       var updatedDepositpaid = this.faker.bool().bool();
        createBookingPayload.replace("lastname",updatedLastname);
        createBookingPayload.replace("totalprice",updatedTotalPrice);
        createBookingPayload.replace("depositpaid",updatedDepositpaid);

        // auth credentials from Environment variables
        var username = System.getenv("BOOK_USERNAME");
        var password = System.getenv("BOOK_PASSWORD");

        var updateBookingApiResponse = updateBookingApi.updateBooking(createBookingPayload,bookingId,"admin","password123")
                                                       .then().assertThat().statusCode(200)
                                                       .and().body("lastname",is(equalTo(updatedLastname)))
                                                        .and().body("totalprice",is(equalTo(updatedTotalPrice)))
                                                       .and().body("depositpaid",is(equalTo(updatedDepositpaid)));


        //delete booking
        var deleteBookingApiResponse = deleteBookingApi.deleteBookingsById(bookingId, "admin", "password123")
                                                       .then().assertThat().statusCode(201);
        //verify booking id deleted
        getBookingApi.getBookingsById(bookingId).then().assertThat().statusCode(404);

    }

    private static void validateRetrievedBookingDataFromGetApi(String firstname, String lastname, Boolean depositPaid, String addtionalNeeds, Long totalPrice, String checkIndate, String checkOutDate, Response getBookingByIdResponse) {
        getBookingByIdResponse
                .then().assertThat().statusCode(200)
                .and().body("firstname",is(equalTo(firstname)))
                .and().body("lastname",is(equalTo(lastname)))
                .and().body("totalprice",is(equalTo(Math.toIntExact(totalPrice))))
                .and().body("depositpaid",is(equalTo(depositPaid)))
                .and().body("additionalneeds", is(equalTo(addtionalNeeds)))
                .and().rootPath("bookingdates")
                .and().body("checkin",is(equalTo(checkIndate)))
                .and().body("checkout",is(equalTo(checkOutDate)))
                .and().detachRootPath("bookingdates");
    }

}
