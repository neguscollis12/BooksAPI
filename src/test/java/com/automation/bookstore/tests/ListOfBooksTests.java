package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Book;
import com.automation.bookstore.utils.Utils.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ListOfBooksTests {
    private Response response;
    private List<Book> books;

    public void setUp(String endpoint) {
        response = ApiRequestBuilder.getResponse(endpoint);
        books = Arrays.asList(response.as(Book[].class));
    }

    @Test(testName = "BK-01 - Verifying GET /books With No Parameters" +
            "200; Array of Books, Each With Id, Name, Type, Available", priority = 1)
    public void testEachBookFields() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT);
        for (Book book : books) {
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200 OK");
            Assert.assertTrue(book.getId() > 0 && book.getId() < 7, "Book ID should be 1 - 6");
            Assert.assertNotNull(book.getName(), "Book name should not be null");
            Assert.assertFalse(book.getName().isEmpty(), "Book name should not be empty");
            Assert.assertNotNull(book.getType(), "Book type should not be null");
            Assert.assertTrue(book.getType().equalsIgnoreCase("fiction") ||
                    book.getType().equalsIgnoreCase("non-fiction"), "Book type should be fiction or non-fiction");
            if (book.getId() == 2) {
                Assert.assertFalse(book.isAvailable(), "Book should not be available");
            } else {
                Assert.assertTrue(book.isAvailable(), "Book should be available");
            }
        }
    }

    @Test(testName = "BK-02 - Verifying '?type=fiction'" +
            "Has 200 Status Code and Every Book Type is Fiction", priority = 2)
    public void testEachBookTypeIsFiction() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT + "?type=fiction");
        for (Book book : books) {
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200 OK");
            Assert.assertEquals(book.getType(), "fiction", "Book type should be fiction");
        }
    }

    @Test(testName = "BK-03 - Verifying '?type=non-fiction'" +
            "Has 200 Status Code and Every Book Type is Fiction", priority = 2)
    public void testEachBookTypeIsNonFiction() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT + "?type=non-fiction");
        for (Book book : books) {
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200 OK");
            Assert.assertEquals(book.getType(), "non-fiction", "Book type should be non-fiction");
        }
    }


    @Test(testName = "BK-04 - Verifying 200 Status and At Most 5 Books")
    public void testAtMost5Books() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT + "?limit=5");
        Assert.assertTrue(books.size() == 5, "Books size should be 5");
        for(Book book : books){
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200 OK");
        }
    }

    @Test(testName = "BK-05 Verifying 200 status and 1 book display")
    public void test1Book() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT + "?limit=1 ");
        Assert.assertTrue(books.size() == 1, "Books size should be only 1");
        for(Book book : books) {
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200");
        }
    }

    @Test(testName = "BK-07 Verifying '?type=fiction&limit=2 (combined)'"+
            "Has 200 Status Code and Every Book Type is Fiction and At Most 2 Books", priority = 2)
    public void testEachBookTypeIsFictionAndAtMost2Books() {
        setUp(ApiConfig.LIST_OF_BOOKS_ENDPOINT + "?type=fiction&limit=2");
        Assert.assertTrue(books.size() == 2, "Books size should only be 2");

        for(Book book : books) {
            book.setStatusCode(response.getStatusCode());
            Assert.assertEquals(book.getStatusCode(), ApiConfig.HTTP_OK, "Status code should be 200");
            Assert.assertEquals(book.getType(), "fiction", "Book type should be fiction");

        }
    }

}



