package http;

import config.propertyUtil;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public abstract class BaseApi {

    private final RequestSpecification requestSpecification;
    public BaseApi() {
        this.requestSpecification = RestAssured.given()
                .baseUri(propertyUtil.getConfig().baseUrl())
                .filter(new AllureRestAssured());

    }

    protected BaseApi setRequestBody(Object object){
        this.requestSpecification.body(object);
        return this;
    }

    protected void setBasePath(String basePath){
        this.requestSpecification.basePath(basePath);
    }

    protected  BaseApi setContentType(ContentType contentType){
        this.requestSpecification.contentType(contentType);
        return this;
    }

    protected BaseApi setBasicAuth(String username,String password){
        this.requestSpecification.auth().preemptive().basic(username, password);
        return this;
    }

    protected void setPathParam(String paramName,Object paramValue){
        this.requestSpecification.pathParam(paramName,paramValue);
    }
    public BaseApi logAllRequestData(){
        this.requestSpecification.filter( new RequestLoggingFilter());
        return this;
    }

    public BaseApi logAllSpecificRequestDetail(LogDetail logDetail){
        this.requestSpecification.filter(new RequestLoggingFilter(logDetail));
        return this;
    }

    public BaseApi logAllResponseData(){
        this.requestSpecification.filter( new ResponseLoggingFilter());
        return this;
    }

    public BaseApi logAllSpecificResponseDetail(LogDetail logDetail){
        this.requestSpecification.filter(new ResponseLoggingFilter(logDetail));
        return this;
    }

    protected Response sendRequest(Method methodType){
        final RequestSpecification when = this.requestSpecification.when();
        return switch (methodType){
            case GET -> when.get();
            case PUT -> when.put();
            case POST -> when.post();
            case DELETE -> when.delete();
            case PATCH -> when.patch();
            default -> throw new IllegalArgumentException("Input method type not supported");
        };
    }

}
