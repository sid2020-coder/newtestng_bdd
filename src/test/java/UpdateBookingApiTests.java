import Util.ApiRequestHelper;
import Util.TestdataHelper;
import apis.CreateBookingApi;
import apis.DeleteBookingApi;
import apis.UpdateBookingApi;
import com.github.javafaker.Faker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;

public class UpdateBookingApiTests extends BaseTest{

private final Faker faker = TestdataHelper.getFaker();

    @Test(dataProvider = "bookingDataWithForLoop")
    public void updateAndvalidateStatusCode(String firstname,String lastname,Boolean depositPaid,
                                            String addtionalNeeds,Long totalPrice,String checkIndate,String checkOutDate){
        var updateBookingApi = new UpdateBookingApi();
        var deleteBookingApi = new DeleteBookingApi();
        var createBookingApi = new CreateBookingApi();
        //create booking
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest(firstname,lastname,Math.toIntExact(totalPrice),
                depositPaid,addtionalNeeds,checkIndate,checkOutDate);
        //create local variable

        var createBookingApiResponse = createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

        //extarct boooking id from create booking
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");

        //replace few details in createbookingpayload and send it as request payload to update api

        createBookingPayload.replace("lastname",this.faker.name().lastName());
        createBookingPayload.replace("totalprice",this.faker.number().randomNumber(3,true));
        createBookingPayload.replace("depositpaid",this.faker.bool().bool());

        // auth credentials from Environment variables
        var username = System.getenv("BOOK_USERNAME");
        var password = System.getenv("BOOK_PASSWORD");
        var updateBookingApiResponse = updateBookingApi.updateBooking(createBookingPayload,bookingId,"admin","password123")
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

        //delete booking
        var deleteBookingApiResponse = deleteBookingApi.deleteBookingsById(bookingId, "admin", "password123")
                                                            .then().assertThat().statusCode(201);

    }


}
