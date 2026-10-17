package com.automation.bookstore.utils.tests;


import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Status;
import com.automation.bookstore.utils.Utils.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class StatusTest {

    Response response;

    Status status;

    @Test(testName = "ST-01 - Verify the API is OK", priority = 1)
    public void statusTest(){
        status = new Status();
        response = ApiRequestBuilder.getResponse(ApiConfig.STATUS_ENDPOINT);
        status.setStatusCode(response.getStatusCode());
        Assert.assertEquals(status.getStatusCode(), ApiConfig.HTTP_OK, "The status code should be 200");
        status.setMessage(response.jsonPath().getString("status"));
        Assert.assertNotNull(status.getMessage(), "The message should be null");
        Assert.assertEquals(status.getMessage(), ApiConfig.SUCCESSFUL_MESSAGE ,  "The message should be OK");
    }
}
