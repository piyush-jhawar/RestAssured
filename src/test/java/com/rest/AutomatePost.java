package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomatePost {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://api.postman.com").
        addHeader("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
        setContentType(ContentType.JSON).
        log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
        expectStatusCode(200).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_create_workspace() {
        String payload = "{\n" +
                "    \"workspace\":\n" +
                "{\n" +
                "    \"name\": \"API Lifecycle Workspace\",\n" +
                "    \"type\": \"team\",\n" +
                "    \"description\": \"Creating a Team Workspace using the Postman API\"\n" +
                "}\n" +
                "}    ";
        given().
                body(payload).
        when().
                post("/workspaces").
        then().spec(responseSpecification)
        .assertThat().
        body("workspace.name", is(equalTo("API Lifecycle Workspace")),
                "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_create_workspace_non_bdd() {
        String payload = "{\n" +
                "    \"workspace\":\n" +
                "{\n" +
                "    \"name\": \"API Lifecycle Workspace\",\n" +
                "    \"type\": \"team\",\n" +
                "    \"description\": \"Creating a Team Workspace using the Postman API\"\n" +
                "}\n" +
                "}    ";

        Response response = with().
                body(payload).
                post("/workspaces");
        assertThat(response.path("workspace.name"), is(equalTo("API Lifecycle Workspace")));
        assertThat(response.path("workspace.id"), matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_create_workspace_file() {
        File file = new File("src/main/resources/CreateWorkspacePayload.json");
        given().
                body(file).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("API Lifecycle Workspace")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_create_workspace_map() {
        HashMap<String, Object> mainobject = new HashMap();
        HashMap<String, String> nestedobject = new HashMap();
        nestedobject.put("name", "API Lifecycle Workspace");
        nestedobject.put("type", "team");
        nestedobject.put("description", "Creating a Team Workspace using the Postman API");

        mainobject.put("workspace", nestedobject);

        given().
                body(mainobject).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("API Lifecycle Workspace")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void validate_create_workspace_json_array_as_list() {
        HashMap<String, Object> mainobject = new HashMap();
        HashMap<String, String> nestedobject = new HashMap();
        nestedobject.put("name", "API Lifecycle Workspace");
        nestedobject.put("type", "team");
        nestedobject.put("description", "Creating a Team Workspace using the Postman API");

        mainobject.put("workspace", nestedobject);

        given().
                body(mainobject).
                when().
                post("/workspaces").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("API Lifecycle Workspace")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }


}
