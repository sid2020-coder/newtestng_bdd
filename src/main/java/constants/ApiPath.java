package constants;

import io.restassured.http.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ApiPath {
    GET_BOOKING("/booking/{bookingId}", Method.GET),
    GET_BOOKING_IDS("/booking",Method.GET),
    CREATE_BOOKING("/booking",Method.POST),
    DELETE_BOOKING("/booking/{bookingId}",Method.DELETE),
    UPDATE_BOOKING("/booking/{bookingId}",Method.PUT);

    private final String apiPath;
    private final Method httpMethodType;

}
