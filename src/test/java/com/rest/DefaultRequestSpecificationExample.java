package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.response.Response;
import io.restassured.specification.QueryableRequestSpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.SpecificationQuerier;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class DefaultRequestSpecificationExample {

    @BeforeClass
    public void beforeClass() {
//        requestSpecification = with().
//                baseUri("https://api.postman.com").
//                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e");
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();
        requestSpecBuilder.setBaseUri("https://api.postman.com");
        requestSpecBuilder.addHeader("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e");
        requestSpecBuilder.log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

    }

    @Test
    public void bdd_to_no_bdd() {
//        Response response = requestSpecification.get("/workspaces");
        Response response = get("/workspaces");
//        System.out.println(response.asString());
        assertThat(response.statusCode(), is(equalTo(200)));
        assertThat(response.path("workspaces[0].name"), is(equalTo("Team Workspace")));
    }

    @Test
    public void query_test() {
        QueryableRequestSpecification queryableRequestSpecification = SpecificationQuerier.query(requestSpecification);
        System.out.println(queryableRequestSpecification.getBaseUri());
        System.out.println(queryableRequestSpecification.getHeaders());

    }

}
