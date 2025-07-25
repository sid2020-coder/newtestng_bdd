import Util.TestdataHelper;
import com.github.javafaker.Faker;
import org.testng.annotations.DataProvider;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class BaseTest {
protected final Faker faker = TestdataHelper.getFaker();
    @DataProvider(name="bookingDataWithForLoop",parallel = true)
    public Object[][] bookingDataProviderWithLoop(){
        var faker = this.faker;
        var name = faker.name();
        var dateFormatter = DateTimeFormatter.ISO_DATE;
        List<Object> list = new ArrayList<>();
        for(int i=0;i<2;i++){
            Object[] objects = new Object[]{
                    name.firstName(),name.lastName(),faker.bool().bool(),
                    faker.food().dish(),faker.number().randomNumber(3,true),
                    TestdataHelper.getfutureDate(10,dateFormatter),
                    TestdataHelper.getfutureDate(14,dateFormatter)};
            list.add(objects);
        }
        return list.toArray(new Object[0][]);
    }
}
