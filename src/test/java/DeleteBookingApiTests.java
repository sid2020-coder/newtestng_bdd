import Util.ApiRequestHelper;
import apis.CreateBookingApi;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import apis.DeleteBookingApi;
import static org.hamcrest.Matchers.*;

public class DeleteBookingApiTests {
    private DeleteBookingApi deleteBookingApi;
    @BeforeClass
    public void initApi(){
        this.deleteBookingApi = new DeleteBookingApi();
    }

    @Test
    public void deleteBookingTest(){
        var createBookingApi1 = new CreateBookingApi();
        //create booking
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest("zah","ajitesh",1010,
                false,"nothing else","2025-06-01","2025-07-01");
        //create local variable

        var createBookingApiResponse = createBookingApi1.createNewBooking(createBookingPayload)
                                                        .then().assertThat().statusCode(200)
                                                        .and().body("bookingid", is(not(equalTo(0))));

        //extarct boooking id from create booking
        var bookingId = createBookingApiResponse.extract().jsonPath().getInt("bookingid");
        // fetch these from environment variables
        var username = System.getenv("BOOK_USERNAME");
        var password = System.getenv("BOOK_PASSWORD");

        var deleteBookingApiResponse = this.deleteBookingApi.deleteBookingsById(bookingId, "admin", "password123")
                                                            .then().assertThat().statusCode(201);
    }

}
