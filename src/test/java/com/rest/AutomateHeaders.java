package com.rest;

import static io.restassured.RestAssured.*;


import io.restassured.http.Header;
import io.restassured.http.Headers;
import org.testng.annotations.Test;

import java.util.*;

public class AutomateHeaders {

    @Test
    public void multiple_headers() {
//        Header header = new Header("header", "value2");
//        Header mheader = new Header("x-mock-match-request-headers", "header");
//        Headers headers = new Headers(header, mheader);

        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                headers(headers).
//                header(header)
//                header(mheader).

        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multi_value_headers() {
        Header header1 = new Header("multiValueHeader", "value1");
        Header header2 = new Header("multiValueHeader", "value2");
        Header header = new Header("header", "value2");
        Header mockheader = new Header("x-mock-match-request-headers", "header");
        Headers headers = new Headers(header1, header2, header, mockheader);

        given().
                baseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                headers(headers).
//                log().all().
//                header(header)
//                header(mheader).
        when().
                get("/get").
                then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void assert_response_headers() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value1");
        headers.put("x-mock-match-request-headers", "header");

        given().
                baseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                headers(headers).
        when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                headers("responseHeader", "resValue1",
                        "X-RateLimit-Limit", "120");
//                header("responseHeader", "resValue1").
//                header("X-RateLimit-Limit", "120");
    }

    @Test
    public void extract_response_headers() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        Headers resheader = given().
                baseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();
//        System.out.println("Header Name => " + resheader.get("responseHeader").getName());
//        System.out.println("Header Value => " + resheader.get("responseHeader").getValue());
//        System.out.println("Header Value Get Value => " + resheader.getValue("responseHeader"));

        for (Header header: resheader) {
            System.out.print("[Header name] = " + header.getName() + ", ");
            System.out.println("[Header value] = " + header.getValue());

        }
    }

    @Test
    public void extract_multivalue_response_headers() {
        HashMap<String, String> headers = new HashMap<>();
        headers.put("header", "value2");
        headers.put("x-mock-match-request-headers", "header");

        Headers resheader = given().
                baseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                headers(headers).
                when().
                get("/get").
                then().
                assertThat().
                statusCode(200).
                extract().
                headers();

        List<String> headerValue = resheader.getValues("multiValueHeader");
        System.out.println(headerValue);
        for (String value: headerValue) {
            System.out.println(value);
        }
    }
}
