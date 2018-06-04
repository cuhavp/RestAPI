package features.mock;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reports.AllureRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

public class ApiSample {
    final String baseURI = "http://jsonplaceholder.typicode.com";
    private RequestSpecification spec;

    @BeforeClass
    public void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri("http://jsonplaceholder.typicode.com")
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Test
    public void testCreateNewPost() throws Exception {
        given().
                spec(spec).
                formParam("title", "foo").
                formParam("body", "bodybar").
                formParam("userId", "1").
                when().
                post("/posts").
                then().
                statusCode(201).
                body("title", equalTo("foo")).body("id", anything());
    }

    @Test
    public void testUpdateExistedPost() throws Exception {
        Response response =
                given().
                        spec(spec).
                        formParam("title", "foo").
                        when().
                        put("/posts/1");

        System.out.print(response.asString());
        response.then().
                body("title", equalTo("foo")).
                statusCode(200);
    }

    @Test
    public void testDeletePost() throws Exception {
        given().spec(spec).
                when().
                delete("/posts/2").
                then().
                statusCode(204);
    }

    @Test
    public void testReadComments() throws Exception {
        given().
                spec(spec).
                param("postId", 1).
                when().
                get("/comments").
                then().
                body(".", hasSize(5));
    }

}