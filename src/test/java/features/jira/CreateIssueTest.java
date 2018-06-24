package features.jira;


import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateIssueTest {

    @BeforeClass
    public void init() {
        RestAssured.baseURI = "https://testingvn.atlassian.net";
        RequestSpecification requestSpecification = new RequestSpecBuilder()
                .addHeader("Content-Type", "application/json").build();
        RestAssured.requestSpecification = requestSpecification;

    }

    @Test
    public void user_could_be_change_new_issue_status() {
//       Response createIssueReps= createIssue();
//       assignIssue();
//       changeIssueStatus();
//       addComment();

    }


    public Response getIssue(String id) {
        return given()
                .auth().preemptive().basic("api@testing.edu.vn", "LlQLrAAZ0tFsG3nABxIE9DBE")
                .when().log().all()
                .get("/rest/api/2/issue/" + id);

    }


    public Response createIssue() {
        return given()
                .auth().preemptive().basic("api@testing.edu.vn", "LlQLrAAZ0tFsG3nABxIE9DBE")
                .body(generateNewIssuePayload())
                .when()
                .post("/rest/api/2/issue");

    }
    public String generateNewIssuePayload(){
        return "{ \n" +
                "  \"fields\": \n" +
                "  {\n" +
                "    \"project\": \n" +
                "      {\n" +
                "        \"key\":\"API1\"\n" +
                "      },\n" +
                "    \"summary\": \"REST-API for adding new issue sample\",\n" +
                "    \"description\": \"Creating my first bug\",\n" +
                "    \"issuetype\": \n" +
                "      {\n" +
                "      \"name\": \"\"\n" +
                "      }\n" +
                "  }\n" +
                "}";
    }
    public String createNewBug(String summary,String description){
        return String.format("{ \n" +
                "  \"fields\": \n" +
                "  {\n" +
                "    \"project\": \n" +
                "      {\n" +
                "        \"key\":\"API1\"\n" +
                "      },\n" +
                "    \"summary\": \"%s\",\n",summary +
                "    \"description\": \"%s\",\n",description +
                "    \"issuetype\": \n" +
                "      {\n" +
                "      \"name\": \"Bug\"\n" +
                "      }\n" +
                "  }\n" +
                "}");

    }
    public Response assignIssue(String id, String assignee) {

        return given()
                .auth().preemptive().basic("api@testing.edu.vn", "LlQLrAAZ0tFsG3nABxIE9DBE")
                .body(String.format("{\"name\":\"%s\"}", assignee))
                .when()
                .put(String.format("rest/api/2/issue/%s/assignee", id));

    }

    public Response changeIssueStatus(String id, String newStatus) {


        return given()
                .auth().preemptive().basic("api@testing.edu.vn", "LlQLrAAZ0tFsG3nABxIE9DBE")
                .body(String.format("{\n" +
                        "    \"update\": {\n" +
                        "        \"comment\": [\n" +
                        "            {\n" +
                        "                \"add\": {\n" +
                        "                    \"body\": \"Comment added when in progress issue\"\n" +
                        "                }\n" +
                        "            }\n" +
                        "        ]\n" +
                        "    },\n" +
                        "    \"transition\": {\n" +
                        "        \"id\": \"%s\"\n" +
                        "    }\n" +
                        "}", newStatus))
                .when()
                .post(String.format("rest/api/2/issue/%s/transitions", id));

    }


    public Response addComment(String id, String comment) {

        return given()
                .auth().preemptive().basic("api@testing.edu.vn", "LlQLrAAZ0tFsG3nABxIE9DBE")
                .body(String.format("{\n" +
                        "    \"body\": \"%s\"\n" +
                        "}", comment))
                .when().log().all()
                .post(String.format("rest/api/2/issue/%s/comment", id));

    }

}
