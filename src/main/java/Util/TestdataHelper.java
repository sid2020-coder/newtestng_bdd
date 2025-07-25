package Util;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TestdataHelper {
    private final static Faker faker = Faker.instance() ;

    public static String getfutureDate(int plusDays, DateTimeFormatter dateTimeFormatter){
        return LocalDate.now().plusDays(plusDays)
                .format(dateTimeFormatter);
    }

    public static Faker getFaker(){
        return faker;
    }
}
