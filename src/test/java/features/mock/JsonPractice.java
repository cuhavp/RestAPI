package features.mock;

import io.qameta.allure.Description;
import io.qameta.allure.Issue;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class JsonPractice {
    private String JSON;
    private Response response;

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "http://restapi.demoqa.com/utilities/books/getallbooks";
        RequestSpecification request = RestAssured.given();

        response = request.get();
        response.then().log().all();
        JSON = response.jsonPath().toString();
    }


    @Issue("")
    @Description("")
    @Test()
    public void getList() {
        final List<String> listBookTitle = response.jsonPath().get("books.title");
        assertThat(listBookTitle.size(), equalTo(8));
    }

    @Test
    public void thirdBookTitle() {
        String title = response.jsonPath().getString("books[2].title");
        assertThat(title, equalTo("Speaking JavaScript"));

    }

    @Test
    public void bookWithLessTPagesLessThan() {
        List<Map<String, ?>> books = response.path("books.findAll { it.pages <= 100 }");
        System.out.println(books);
        assertThat(books.size(), equalTo(7));
    }

    @Test
    public void bookWithArgAuthor() {
        String author = "Richard E. Silverman";
        Map<String, ?> books = response.path("books.find { it.author == 'Richard E. Silverman'}");
        assertThat(books.get("author"), equalTo(author));
    }


}
