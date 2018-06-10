package features.twitter;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Basic9 {

    String ConsumerKey = "ztXsFr0ipQTIDue4MD20s4aL3";
    String ConsumerSecret = "kuOlC5wB1DZ8ahgOsEos1zLa7C4fLmlpSSB5QEJmFFEX70CT2n";
    String Token = "917476985835851778-vOmu4mScebK0zi7hTPXgprO9A7BMLsw";
    String TokenSecret = "tCw78M48rJC2mNWpNvZJuXhGUqFgQ8mqowdsjrDbwbQLs";
    String id;

    @Test
    public void getLatestTweet() {

        RestAssured.baseURI = "https://api.twitter.com/1.1/statuses";
        Response res = given().auth().oauth(ConsumerKey, ConsumerSecret, Token, TokenSecret)
                .queryParam("count", "1")
                .when().get("/home_timeline.json").then().extract().response();

        String response = res.asString();
        System.out.println(response);
        JsonPath js = new JsonPath(response);

        System.out.println(js.get("text").toString());
        System.out.println(js.get("id").toString());


    }
}