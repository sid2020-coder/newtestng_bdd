package apis;

import constants.ApiPath;
import http.BaseApi;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import pojo.request.CreateBookingRequest;

import java.util.Map;

public class CreateBookingApi extends BaseApi {
    public CreateBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData();
        super.setContentType(ContentType.JSON);
    }

    public Response createNewBooking(Map<String,Object> createBookingPayload){
        return getCreateBookingAPiResponse(createBookingPayload);
    }

    public Response createNewBooking(CreateBookingRequest createBookingRequest){
        return getCreateBookingAPiResponse(createBookingRequest);
    }

    private Response getCreateBookingAPiResponse(Object createBookingPayload) {
        super.setBasePath(ApiPath.CREATE_BOOKING.getApiPath());
        super.setRequestBody(createBookingPayload);
        return super.sendRequest(ApiPath.CREATE_BOOKING.getHttpMethodType());
    }
}
