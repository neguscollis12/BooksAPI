package com.automation.bookstore.config;

public class ApiConfig {
    public static final String BASE_URL = "https://simple-books-api.click";
    public static final String STATUS_ENDPOINT = "/status";
    public static final String LIST_OF_BOOKS_ENDPOINT = "/books";
    public static final String SINGLE_BOOKS_ENDPOINT = "/books/";
    public static final String SUBMIT_AN_ORDERS_ENDPOINT = "/orders";
    public static final String GET_ALL_ORDERS_ENDPOINT = "/orders";
    public static final String GET_AN_ORDERS_ENDPOINT = "/orders/";
    public static final String UPDATE_AN_ORDERS_ENDPOINT = "/orders/";
    public static final String DELETE_AN_ORDER_ENDPOINT = "/orders/";
    public static final String API_AUTHENTICATION_ENDPOINT = "/api-clients/";

    public static final int HTTP_OK = 200;
    public static final int HTTP_CREATED = 201;
    public static final int HTTP_NO_CONTENT = 204;
    public static final int HTTP_BAD_REQUEST = 400;
    public static final int HTTP_UNAUTHORIZED = 401;
    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;
    public static final int HTTP_CONFLICT = 409;



}
