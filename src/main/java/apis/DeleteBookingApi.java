package apis;

import constants.ApiPath;
import http.BaseApi;
import io.restassured.response.Response;

public class DeleteBookingApi extends BaseApi {
    public DeleteBookingApi() {
        super();
        super.logAllRequestData()
                .logAllResponseData();
    }

    public Response deleteBookingsById(int bookingId,String username, String password){
        super.setBasePath(ApiPath.DELETE_BOOKING.getApiPath());
        super.setPathParam("bookingId",bookingId);
        super.setBasicAuth(username, password);
        return super.sendRequest(ApiPath.DELETE_BOOKING.getHttpMethodType());
    }
}
