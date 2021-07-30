package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class RequestPayloadAsJsonArray {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
        addHeader("x-mock-match-request-body", "true").
        setContentType("application/json").
//        setContentType(ContentType.JSON).
//        setConfig(config.encoderConfig(EncoderConfig.encoderConfig().
//                appendDefaultContentCharsetToContentTypeIfUndefined(false))).
        log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
        expectStatusCode(200).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_create_workspace_json_array_as_list() {
        HashMap<String,String> obj5001 = new HashMap();
        obj5001.put("id", "5001");
        obj5001.put("type", "None");
        HashMap<String,String> obj5002 = new HashMap();
        obj5002.put("id", "5002");
        obj5002.put("type", "Glazed");

        List<HashMap<String, String>> jsonList = new ArrayList();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        given().
                body(jsonList).
                when().
                post("/post").
                then().spec(responseSpecification)
                .assertThat().
                body("msg", is(equalTo("success")));
    }

}
