package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.utils.ApiRequestBuilder;
import com.automation.bookstore.utils.JsonTemplateLoader;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Map;

public class AuthenticationTest {

    private final String clientName = "TestClientName" + System.currentTimeMillis();
    private final String clientEmail = "TestClientEmail" + System.currentTimeMillis() + "@exapmle.com";
    String requestBody;
    int statusCode;
    String accessToken;
    Response response;

    @Test
    public void testRegisterNewApiClient() throws IOException {
        requestBody = buildRequestBody(clientName, clientEmail);
        response = ApiRequestBuilder.postResponse(ApiConfig.API_AUTHENTICATION_ENDPOINT, requestBody);
        statusCode = response.getStatusCode();

        accessToken = response.jsonPath().getString("accessToken");
        Assert.assertEquals(statusCode, ApiConfig.HTTP_CREATED);
        Assert.assertNotNull(accessToken, "Access token should not be null");
        Assert.assertFalse(accessToken.isEmpty(), "Access token should not be empty");

    }

    @Test
    public void testRigisterClientWithMissingName() throws IOException {
        requestBody = buildRequestBody("", clientEmail);
        response = ApiRequestBuilder.postResponse(ApiConfig.API_AUTHENTICATION_ENDPOINT, requestBody);
        statusCode = response.getStatusCode();

        Assert.assertTrue(statusCode == ApiConfig.HTTP_BAD_REQUEST, "Register client with missing name should return 400 status code");
    }

    @Test
    public void testRigisterClientWithMissingEmail() throws IOException {
        requestBody = buildRequestBody(clientName, "");
        response = ApiRequestBuilder.postResponse(ApiConfig.API_AUTHENTICATION_ENDPOINT, requestBody);
        statusCode = response.getStatusCode();

        Assert.assertTrue(statusCode == ApiConfig.HTTP_BAD_REQUEST,  "Register client with missing email should return 400 status code");
    }




    private String buildRequestBody(String clientName, String clientEmail) throws IOException {
        return JsonTemplateLoader.load(
                "requests/Authentication.json",
                Map.of("clientName", clientName, "clientEmail", clientEmail)
        );
    }
}
