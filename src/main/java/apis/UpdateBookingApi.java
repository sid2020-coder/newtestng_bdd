package apis;

import constants.ApiPath;
import http.BaseApi;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

public class UpdateBookingApi extends BaseApi {
    public UpdateBookingApi() {
        super();
        super.logAllSpecificRequestDetail(LogDetail.BODY).logAllResponseData();
        super.setContentType(ContentType.JSON);
    }

    public Response updateBooking(Map<String,Object> createBookingPayload, int bookingId,
                                  String username,String password){
        return getUpdateBookingAPiResponse(createBookingPayload,bookingId,username,password);
    }

    public Response updateBooking(CreateBookingRequest createBookingRequest,int bookingId,
                                  String username,String password){
        return getUpdateBookingAPiResponse(createBookingRequest,bookingId,username,password);
    }

    private Response getUpdateBookingAPiResponse(Object createBookingPayload, int bookingId,
                                                 String username,String password) {
        super.setBasePath(ApiPath.UPDATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        super.setPathParam("bookingId", bookingId);
        super.setBasicAuth(username, password);
        return super.sendRequest(ApiPath.UPDATE_BOOKING.getHttpMethodType());
    }
}
