package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class RequestSpecificationExample {
    public RequestSpecification requestSpecification;

    @BeforeClass
    public void beforeClass() {
//        requestSpecification = with().
//                baseUri("https://api.postman.com").
//                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e");
        requestSpecBuilder.log(LogDetail.ALL);
        requestSpecification = requestSpecBuilder.build();

    }

    @Test
    public void validate_status_code() {
//        given(requestSpecification).
        given().spec(requestSpecification).
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void validate_response_body() {
//        given(requestSpecification).
        given().spec(requestSpecification).
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces[0].name", equalTo("Team Workspace"));
    }

    @Test
    public void bdd_to_no_bdd() {
//        Response response = requestSpecification.get("/workspaces");
        Response response = given().spec(requestSpecification).get("/workspaces");
//        System.out.println(response.asString());
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"), is(equalTo("Team Workspace")));
    }




}
