package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Book;
import com.automation.bookstore.utils.ApiRequestBuilder;
import com.automation.bookstore.utils.RequestBodyBuilder;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;


public class SubmitAnOrderTest {

    private Response response;
    private Response response2;
    private Book book;
    String requestBody;
    String requestBody2;
    private final String customerName = "CustomerName" + System.currentTimeMillis();
    private final String customerEmail = "CustomerEmail" + System.currentTimeMillis() + "@example.com";
    private final String authenticationPath = "requests/Authentication.json";
    private final String submitAnOrderPath = "requests/SubmitAnOrder.json";
    private final String authorizationKey1 = "clientName";
    private final String authorizationKey2 = "clientEmail";
    private final String submitOrderKey1 = "bookId";
    private final String submitOrderKey2 = "customerName";
    String accessToken;



    @Test
    public void testSubmitAnOrder() throws IOException {

        requestBody = RequestBodyBuilder.buildRequestBody(authenticationPath, authorizationKey1, authorizationKey2,  customerName, customerEmail);
        response = ApiRequestBuilder.postResponse(ApiConfig.API_AUTHENTICATION_ENDPOINT, requestBody);
        accessToken = response.jsonPath().getString("accessToken");
        requestBody2 = RequestBodyBuilder.buildRequestBody(submitAnOrderPath, submitOrderKey1, submitOrderKey2, customerName, customerEmail);
        response2 = ApiRequestBuilder.postResponseWithAuth(ApiConfig.SUBMIT_AN_ORDERS_ENDPOINT, requestBody,  accessToken);
        Assert.assertEquals(response.getStatusCode(), ApiConfig.HTTP_CREATED,  "SubmitAnOrder response status code should be created");

    }

    @Test
    public void testSubmitAnOrder2() {
        RestAssured.baseURI = "https://www.nike.com";
        Response response1 = RestAssured.given().when().get("/jordan");
        Response response2 = RestAssured.given().header("Authorization", "Bearer " + accessToken).when().get("/jordan");
        int statusCode1 = response1.statusCode();
        System.out.println(statusCode1);
    }

}
