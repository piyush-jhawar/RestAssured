package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;

public class AutomateDelete {

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
    public void validate_delete_workspace() {
        String workspaceId = "f6a90418-7f7f-4912-95a3-1bda56640bd4";

        given().
        when().
                delete("workspaces/" + workspaceId).
        then().spec(responseSpecification)
        .assertThat().
        body("workspace.id", matchesPattern("^[a-z0-9-]{36}$"),
                "workspace.id", is(equalTo(workspaceId)));
    }


}
