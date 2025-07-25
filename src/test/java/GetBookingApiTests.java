import apis.GetBookingApi;
import listners.RetryAnalyzer;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GetBookingApiTests {

    @Test(description = "Basic HTTP request to GET all bookings")
    public void validateStatuCodeForGetBookingIdApi(){
        var getBookingApi = new GetBookingApi();
        var getBookingidsResponse = getBookingApi.getAllBookingIds()
                .then().assertThat().statusCode(200);

    }

    @Test(description = "Basic HTTP request to GET booking by ID")
    public void validateStatuCodeForGetBookingByIdApi(){
        var getBookingApi = new GetBookingApi();
        var getBookingByIdResponse = getBookingApi.getBookingsById(802)
                .then().assertThat().statusCode(200);

    }
}
