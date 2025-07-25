import Util.ApiRequestHelper;
import Util.TestdataHelper;
import apis.CreateBookingApi;
import static org.hamcrest.Matchers.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pojo.request.Bookingdates;
import pojo.request.CreateBookingRequest;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CreateBookingApiTests {

    private CreateBookingApi createBookingApi;

    @BeforeClass
    public void initApi(){
        this.createBookingApi = new CreateBookingApi();
    }

    @DataProvider(name="bookingDataWithForLoop")
    public Object[][] bookingDataProviderWithLoop(){
        var faker = TestdataHelper.getFaker();
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;
        List<Object> list = new ArrayList<>();
        for(int i=0;i<5;i++){
            Object[] objects = new Object[]{
                    name.firstName(),name.lastName(),faker.bool().bool(),
                    faker.food().dish(),faker.number().randomNumber(3,true),
                    TestdataHelper.getfutureDate(10,dateFormatter),
                    TestdataHelper.getfutureDate(14,dateFormatter)};
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }
    @Test(dataProvider = "bookingDataWithForLoop")
    public void createAndvalidateStatusCode(String firstname,String lastname,Boolean depositPaid,
                                            String addtionalNeeds,Long totalPrice,String checkIndate,String checkOutDate){
       // var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest("zah","ajitesh",1010,false,"nothing else","2025-06-01","2025-07-01");
        var createBookingPayload = ApiRequestHelper.getCreateBookingApiRequest(firstname,lastname,Math.toIntExact(totalPrice),
                depositPaid,addtionalNeeds,checkIndate,checkOutDate);
        var createBookingApiResponse = this.createBookingApi.createNewBooking(createBookingPayload)
                .then().assertThat().statusCode(200)
                .and().body("bookingid", is(not(equalTo(0))));

    }

}
