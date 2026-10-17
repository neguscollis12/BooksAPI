package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Status;
import com.automation.bookstore.utils.Utils.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StatusTest {

    private Response response;
    Status status;

    @Test(testName = "ST-01 - Verifying The API is OK", priority = 1)
    public void statusTest() {
        status = new Status();
        response = ApiRequestBuilder.getResponse(ApiConfig.STATUS_ENDPOINT);
        status.setStatusCode(response.getStatusCode());
        Assert.assertEquals(status.getStatusCode(), ApiConfig.HTTP_OK, "The Status Code should be 200");
        status.setMessage(response.jsonPath().getString("status"));
        Assert.assertNotNull(status.getMessage(), "The message should not be null");
        Assert.assertEquals(status.getMessage(), ApiConfig.SUCCESSFUL_MESSAGE, "The message should say 'OK'");

    }

    @Test(testName = "ST-02 - Verifying The Response Time Is Under 1-2 Seconds", priority = 3)
    public void responseTimeTest() {
        long startTime = System.currentTimeMillis();
        long elapsedTime = System.currentTimeMillis() - startTime;
        response = ApiRequestBuilder.getResponse(ApiConfig.STATUS_ENDPOINT);
        Assert.assertTrue(elapsedTime < 2000, "Response took" + elapsedTime + " milliseconds - excepted under 2000ms");
    }

    @Test(testName = "ST-03 - Verifying Response Header Content-Type: application/json", priority = 3)
    public void responseHeaderContentTypeTest() {
        response = ApiRequestBuilder.getResponse(ApiConfig.STATUS_ENDPOINT);
        Assert.assertTrue(response.getContentType().startsWith("application/json"), "Response content type should be application/json");
    }

    @Test(testName = "ST-04 - POST/Status Not Found - 404", priority = 3)
    public void statusNotFoundTest() {
        status = new Status();
        response = ApiRequestBuilder.postResponse(ApiConfig.STATUS_ENDPOINT, "");
        status.setStatusCode(response.getStatusCode());
        Assert.assertEquals(status.getStatusCode(), ApiConfig.HTTP_NOT_FOUND, "The Status Code should be 404");
        status.setMessage(response.jsonPath().getString("error"));
        Assert.assertNotNull(status.getMessage(), "The message should not be null");
        Assert.assertEquals(status.getMessage(), ApiConfig.ERROR_MESSAGE, "The message should be 404 Not Found");
    }

}
