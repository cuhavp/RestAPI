package features.mock;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Map;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

public class FootballTest {
    @BeforeClass
    public void setUpRestAssured() {
        RestAssured.baseURI = "http://api.football-data.org/";
        RestAssured.basePath = "/v1/";

        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("X-Auth-Token", "be658778111b4152a70b240081651e3e")
                .addHeader("X-Response-Control", "")
                .build();
        RestAssured.requestSpecification = requestSpecification;
    }

    @Test
    public void extractSingleValue_findSingleTeamName() {

        Response response = get("teams/66");
        String teamName = response.path("name");
        System.out.println(teamName);
    }

    @Test
    public void extractSingleValue_findSingleTeamName_specifyJsonPath() {

        Response response = get("teams/66");
        JsonPath jsonPath = new JsonPath(response.asString());
        String teamName = jsonPath.get("name");
        System.out.println(teamName);
    }

    @Test
    public void extractSingleValue_findSingleTeamName_responseAsString() {

        String responseAsString = get("teams/66").asString();
        String teamName = JsonPath.from(responseAsString).get("name");
        System.out.println(teamName);
    }

    @Test
    public void extractSingleValue_findSingleTeamName_getEverythingInOneGo() {

        String teamName = get("teams/66").path("name");
        System.out.println(teamName);
    }


    @Test
    public void extractSingleValue_findSingleTeamName_useAssertion() {

        given().
                when().
                get("teams/66").
                then().
                assertThat().
                body("name", equalTo("Manchester United FC"));
    }

    @Test
    public void extractFirstValueWhenSeveralReturned_findFirstTeamName() {
        Response response = get("competitions/426/teams");
        String firstTeamName = response.path("teams.name[0]");
        System.out.println(firstTeamName);
    }

    @Test
    public void extractLastValueWhenSeveralReturned_findLastTeamName() {

        Response response = get("competitions/426/teams");
        String lastTeamName = response.path("teams.name[-1]");
        System.out.println(lastTeamName);
    }

    @Test
    public void extractListOfValues_findAllTeamNames() {

        Response response = get("competitions/426/teams");
        ArrayList<String> allTeamNames = response.path("teams.name");
        System.out.println(allTeamNames);
    }

    @Test
    public void extractListOfMapsOfElements_findAllTeamData() {
        Response response = get("competitions/426/teams");
        ArrayList<Map<String, ?>> allTeamData = response.path("teams");
        System.out.println(allTeamData);
    }
}
