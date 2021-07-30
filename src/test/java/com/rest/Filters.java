package com.rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.*;
import static io.restassured.RestAssured.*;

public class Filters {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeClass
    public void beforeClass() throws FileNotFoundException {
        PrintStream fileOutputStream = new PrintStream(new File("restAssured.log"));

        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                addFilter(new RequestLoggingFilter(fileOutputStream)).
                addFilter(new ResponseLoggingFilter(fileOutputStream));
        requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void loggingFilter() {

        given().spec(requestSpecification).
                baseUri("https://postman-echo.com").
//                filter(new RequestLoggingFilter()).
//                filter(new ResponseLoggingFilter()).
                filter(new RequestLoggingFilter(LogDetail.BODY)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS)).
//                log().all().
        when().
                get("/get").
        then().spec(responseSpecification).
//                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void loggingFilter_printstream() throws FileNotFoundException {
        PrintStream printStream = new PrintStream(new File("log.log"));

        given().
                baseUri("https://postman-echo.com").
                filter(new RequestLoggingFilter(LogDetail.BODY, printStream)).
                filter(new ResponseLoggingFilter(LogDetail.STATUS, printStream)).
        when().
                get("/get").
        then().
                assertThat().
                statusCode(200);
    }
}
