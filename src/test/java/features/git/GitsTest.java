package features.git;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import reports.AllureRestAssured;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class GitsTest {
    private RequestSpecification spec;

    @BeforeClass
    public void initSpec() {
        spec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addFilter(new AllureRestAssured())
                .build();
    }

    @Test
    public void getCommentsOfSpecificUser() {
        given()
                .spec(spec)
                .when()
                .get("/gists/675504e6d8eba38766561520155d90d1/comments").then()
                .body("find {it.user.login== \"g4-Tu-Nguyen\"}.body", equalTo("API autotest"));

    }
}
