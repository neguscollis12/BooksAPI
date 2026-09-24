package com.automation.bookstore.utils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class JsonTemplateLoader {

    public static String load(String resourcePath, Map<String, String> values) throws IOException {
        try(InputStream inputStream = JsonTemplateLoader.class.getClassLoader().getResourceAsStream(resourcePath)){
            if(inputStream == null){
                throw new IOException("Resource not found: " + resourcePath);
            }
            String template = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
            for(Map.Entry<String, String> entry : values.entrySet()){
                template = template.replace("${"  + entry.getKey() + "}", entry.getValue());
            }
            return template;
        }
    }
}
