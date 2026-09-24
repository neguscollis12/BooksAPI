package com.automation.bookstore.tests;

import com.automation.bookstore.config.ApiConfig;
import com.automation.bookstore.models.Book;
import com.automation.bookstore.utils.ApiRequestBuilder;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

public class ListOfBooksTest {
    private Response response;
    private List<Book> books;

    @BeforeClass
    public void setup() {
        Book book = new Book();
        response = ApiRequestBuilder.getResponse(ApiConfig.LIST_OF_BOOKS_ENDPOINT);

        book.setStatusCode(response.getStatusCode());
        books =  Arrays.asList(response.as(Book[].class));

    }

    @Test
    public void testListSize() {
        Assert.assertEquals(books.size(), 6, "Books size should be 6");
    }

    @Test
    public void testEachBookFields(){
        for (Book book : books) {
            Assert.assertTrue(book.getId() > 0 && book.getId() <7, "id should be greater than 0 and less than 7 ");
            Assert.assertNotNull(book.getName(), "Name should not be null");
            Assert.assertFalse(book.getName().isEmpty(), "Name should not be empty");
            Assert.assertNotNull(book.getType(), "Type should not be null");
            Assert.assertTrue(book.getType().equalsIgnoreCase("fiction") ||
                    book.getType().equalsIgnoreCase("non-fiction"), "Type should be either fiction or non-fiction");
            if (book.getId() == 2){
                Assert.assertFalse(book.isAvailable(), "Book should not be available");
            }else {
                Assert.assertTrue(book.isAvailable(), "Book should be available");
            }

        }
    }

    @Test(dataProvider = "specificBookTestData")
    public void testSpecificBookById(int bookId, String expectedName, String expectedType, boolean expectedAvailable) {


        Book book = books.stream()
                .filter(b -> b.getId() == bookId)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Book with id " + bookId + " not found"));
        Assert.assertEquals(book.getName(), expectedName, "Book name should be " + expectedName);
        Assert.assertEquals(book.getType(), expectedType, "Book type should be " + expectedType);
        Assert.assertEquals(book.isAvailable(), expectedAvailable, "Book should be available");
    }

    @DataProvider(name = "specificBookTestData")
    public Object[][] specificBookTestData() {
        return new Object[][]{
                {1,"The Russian", "fiction", true},
                {2,"Just as I Am", "non-fiction", false},
                {3,"The Vanishing Half", "fiction", true},
                {4,"The Midnight Library", "fiction", true},
                {5, "Untamed", "non-fiction", true},
                {6,"Viscount Who Loved Me",  "fiction", true},
        };
    }

    @Test
    public void testAllFictionBooksAreAvailable() {
        books.stream()
                .filter(b -> b.getType().equalsIgnoreCase("fiction"))
                .forEach(b -> Assert.assertTrue(b.isAvailable(), "Fiction book " + b.getName() + " should be available"));
    }

}
