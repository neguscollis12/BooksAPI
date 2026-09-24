package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Status;
import com.automation.bookstore.utils.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class StatusTest {
    Response response;

    @Test
    public void statusTest() {
        String ExceptedStatusMessage = "OK";
        Status status = new Status();
        response =  ApiRequestBuilder.getResponse(ApiConfig.STATUS_ENDPOINT);
        status.setStatus(response.getStatusCode());
        Assert.assertEquals(status.getStatus(), ApiConfig.HTTP_OK, "Status response status should be OK");
        status.setMessage(response.jsonPath().getString("status"));
        Assert.assertNotNull(status.getMessage(), "Status message should not be null");
        Assert.assertEquals(status.getMessage(), ExceptedStatusMessage, "Status message should match status message");

    }
}
