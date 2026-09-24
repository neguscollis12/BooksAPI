package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Book;
import com.automation.bookstore.utils.ApiRequestBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class SingleBookTest {

    private Response response;
    private Book book;


    @Test(dataProvider = "SingleBookTestData")
    public void testSingleBookFields(String id, boolean exceptedAvailable, String exceptedName, double exceptedPrice, String exceptedAuthor, String exceptedType) {
        response = ApiRequestBuilder.getResponse(ApiConfig.SINGLE_BOOKS_ENDPOINT + id);
        book = response.as(Book.class);
        book.setStatusCode(response.getStatusCode());
        book.setAvailable(response.jsonPath().getBoolean("available"));
        book.setName(response.jsonPath().getString("name"));
        book.setPrice(response.jsonPath().getDouble("price"));
        book.setAuthor(response.jsonPath().getString("author"));
        book.setType(response.jsonPath().getString("type"));

        Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK);
        Assert.assertEquals(book.isAvailable(), exceptedAvailable, "Book available is not excepted");
        Assert.assertEquals(book.getName(), exceptedName, "Book name is not not correct");
        Assert.assertEquals(book.getPrice(), exceptedPrice, "Book price is not correct");
        Assert.assertEquals(book.getAuthor(), exceptedAuthor, "Book author is not correct");
        Assert.assertEquals(book.getType(), exceptedType, "Book type is not correct");
    }

    @DataProvider(name = "SingleBookTestData")
    public Object[][] getSingleBookTestData() {
        return new Object[][]{
                {"1", true, "The Russian", 12.98, "James Patterson and James O. Born", "fiction"},
                {"2", false, "Just as I Am", 20.33, "Cicely Tyson", "non-fiction"},
                {"3", true, "The Vanishing Half", 16.2, "Brit Bennett", "fiction"},
                {"4", true, "The Midnight Library", 15.6, "Matt Haig", "fiction"},
                {"5", true, "Untamed", 15.5, "Glennon Doyle", "non-fiction"},
                {"6", true, "Viscount Who Loved Me", 15.6, "Julia Quinn", "fiction"},
        };
    }


}
