package com.automation.bookstore.utils;

import java.io.IOException;
import java.util.Map;

public class RequestBodyBuilder {

    public static String buildRequestBody(String resourcePath, String key1, String key2, String value1, String value2) throws IOException {
        return JsonTemplateLoader.load(
                resourcePath,
                Map.of(key1, value1, key2, value2)
        );
    }
}
