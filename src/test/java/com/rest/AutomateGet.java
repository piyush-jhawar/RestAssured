package com.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.restassured.config.LogConfig;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class AutomateGet {

    @Test
    public void validate_get_status_code() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
        when().
                get("/workspaces").
        then().
                log().all().
                assertThat().
                statusCode(200).
                body("workspaces.name", hasItems("Team Workspace", "RestAssuredOm", "My Workspace"),
                        "workspaces.type", hasItems("team", "team", "personal"),
                        "workspaces[0].name", is(equalTo("Team Workspace")),
                        "workspaces.size", is((equalTo(3))),
                        "workspaces.name", hasItem("Team Workspace"));

    }

    @Test
    public void extract_response() {
        Response res = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response();
        System.out.println("Res => " + res.asString());

    }

    @Test
    public void extract_single_value_from_response() {
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
//                asString();
        System.out.println(name);
//        System.out.println(JsonPath.from(res).getString("workspaces[0].name"));
//        JsonPath jsonPath = new JsonPath(res.asString());
//        System.out.println(jsonPath.getString("workspaces[0].name"));
        //System.out.println("Res => " + res.path("workspaces.name"));

    }

    @Test
    public void hamcrest_assert_on_extracted_response() {
        String name = given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                extract().
                response().path("workspaces[0].name");
//        assertThat(name, equalTo("Team Workspace"));
        Assert.assertEquals(name, "Team Workspace");
    }

    @Test
    public void validate_response_body_hamcrest_learnings() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                when().
                get("/workspaces").
                then().
                assertThat().
                statusCode(200).
                body("workspaces.name", containsInAnyOrder("Team Workspace", "RestAssuredOm", "My Workspace"),
                        "workspaces.name", is(not(empty())),
                        "workspaces.name", hasSize(3),
//                        "workspaces.name", everyItem(startsWith("M")),
                        "workspaces[0]", hasKey("id"),
                        "workspaces[0]", hasValue("Team Workspace"),
                        "workspaces[0]", hasEntry("id", "056be8ba-faff-49bb-8570-31dfd3bf66c4"),
                        "workspaces[0]", not(equalTo(Collections.EMPTY_MAP)),
                        "workspaces[0].name", allOf(startsWith("Te"), containsString("Works"))
                        );
//        contains("Team Workspace", "RestAssuredOm", "My Workspace")
    }

    @Test
    public void request_response_logging() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                log().all().
                when().
                get("/workspaces").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_error() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                log().all().
                when().
                get("/workspaces").
                then().
                log().ifError().
                assertThat().
                statusCode(200);
    }

    @Test
    public void log_only_if_validation_fails() {
        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
                config(config.logConfig(LogConfig.logConfig().enableLoggingOfRequestAndResponseIfValidationFails())).
//                log().ifValidationFails().
                when().
                get("/workspaces").
                then().
//                log().ifValidationFails().
                assertThat().
                statusCode(200);
    }

    @Test
    public void logs_blacklist_header() {
        Set<String> headers = new HashSet<String>();
        headers.add("X-Api-Key");
        headers.add("Accept");

        given().
                baseUri("https://api.postman.com").
                header("X-Api-Key", "PMAK-60f507824edda30051a490ac-d3ec9bd04693518cd2d390cb421a07022e").
//                config(config.logConfig(LogConfig.logConfig().blacklistHeader("X-Api-Key"))).
                config(config.logConfig(LogConfig.logConfig().blacklistHeaders(headers))).
//                log().ifValidationFails().
        when().
                get("/workspaces").
                then().
//                log().ifValidationFails().
        assertThat().
                statusCode(200);
    }
}
