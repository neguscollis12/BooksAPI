package com.automation.bookstore.utils;

import com.automation.bookstore.config.ApiConfig;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ApiRequestBuilder {
    public static RequestSpecification getBaseRequest() {
        return given()
                .baseUri(ApiConfig.BASE_URL)
                .contentType("application/json")
                .accept("application/json");
    }

    public static RequestSpecification getBaseRequestWithAuth(String token) {
        return getBaseRequest()
                .header("Authorization", "Bearer " + token);
    }

    public static Response getResponse(String endpoint) {
        return getBaseRequest()
                .when()
                .get(endpoint);
    }

    public static Response postResponse(String endpoint, String body) {
        return getBaseRequest()
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response postResponseWithAuth(String endpoint, String body,  String token) {
        return getBaseRequestWithAuth(token)
                .body(body)
                .when()
                .post(endpoint);
    }

    public static Response patchRequestWithAuth(String endpoint, String body, String token) {
        return getBaseRequestWithAuth(token)
                .body(body)
                .when()
                .patch(endpoint);
    }

    public static Response deleteRequestWithAuth(String endpoint, String token) {
        return getBaseRequestWithAuth(token)
                .when()
                .delete(endpoint);
    }
}
