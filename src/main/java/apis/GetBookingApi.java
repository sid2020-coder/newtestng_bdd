package apis;

import constants.ApiPath;
import http.BaseApi;
import io.restassured.response.Response;

public class GetBookingApi extends BaseApi {

    public GetBookingApi() {
        super();
        super.logAllRequestData()
                .logAllResponseData();
    }

    public Response getAllBookingIds(){
        super.setBasePath(ApiPath.GET_BOOKING_IDS.getApiPath());
        return super.sendRequest(ApiPath.GET_BOOKING_IDS.getHttpMethodType());
    }

    public Response getBookingsById(int bookingId){
        super.setBasePath(ApiPath.GET_BOOKING.getApiPath());
        super.setPathParam("bookingId",bookingId);
        return super.sendRequest(ApiPath.GET_BOOKING.getHttpMethodType());
    }
}
