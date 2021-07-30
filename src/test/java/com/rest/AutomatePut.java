package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class AutomatePut {

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
    public void validate_update_workspace() {
        String workspaceId = "f6a90418-7f7f-4912-95a3-1bda56640bd4";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"New Workspace updated\",\n" +
                "        \"description\": \"Some description\"\n" +
                "    }\n" +
                "}";

        given().
                body(payload).
        when().put("workspaces/" + workspaceId).
        then().spec(responseSpecification)
        .assertThat().
        body("workspace.name", is(equalTo("New Workspace updated")),
        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                "workspace.id", is(equalTo(workspaceId)));
    }

    @Test
    public void validate_update_workspace_path_param() {
        String workspaceId = "f6a90418-7f7f-4912-95a3-1bda56640bd4";
        String payload = "{\n" +
                "    \"workspace\": {\n" +
                "        \"name\": \"New Workspace updated\",\n" +
                "        \"description\": \"Some description\"\n" +
                "    }\n" +
                "}";

        given().
                body(payload).
                pathParam("workspaceId", workspaceId).
                when().
                put("workspaces/{workspaceId}").
                then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("New Workspace updated")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                        "workspace.id", is(equalTo(workspaceId)));
    }


}
