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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class AutomateComplexJsonPayload {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
        setBaseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
        addHeader("x-mock-match-request-body", "true").
        setContentType(ContentType.JSON).
        log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
//        expectStatusCode(200).
        expectContentType(ContentType.JSON).
        log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_complex_json_payload() {
        List<Integer> idArrayList = new ArrayList<>();
        idArrayList.add(5);
        idArrayList.add(9);
        HashMap<String, Object> batterHashMap2 = new HashMap<>();
        batterHashMap2.put("id", idArrayList);
        batterHashMap2.put("type", "Chocolate");
        HashMap<String, Object> batterHashMap1 = new HashMap<>();
        batterHashMap1.put("id", "1001");
        batterHashMap1.put("type", "Regular");

        List<HashMap<String, Object>> batter = new ArrayList<>();
        batter.add(batterHashMap1);
        batter.add(batterHashMap2);

        HashMap<String, List> batters = new HashMap<>();
        batters.put("batter", batter);

        List<String> typeArrayList = new ArrayList<>();
        typeArrayList.add("test1");
        typeArrayList.add("test2");
        HashMap<String, Object> toppingHashMap2 = new HashMap<>();
        toppingHashMap2.put("id", "5002");
        toppingHashMap2.put("type", typeArrayList);
        HashMap<String, String> toppingHashMap1 = new HashMap<>();
        toppingHashMap1.put("id", "5001");
        toppingHashMap1.put("type", "None");

        List<HashMap> topping = new ArrayList<>();
        topping.add(toppingHashMap1);
        topping.add(toppingHashMap2);

        HashMap<String, Object> mainJson = new HashMap<>();
        mainJson.put("id", "0001");
        mainJson.put("type", "donut");
        mainJson.put("name", "Cake");
        mainJson.put("ppu", 0.55);
        mainJson.put("batters", batters);
        mainJson.put("topping", topping);

        given().
                body(mainJson).
                when().
                post("/postcomplexjson").
                then().spec(responseSpecification)
                .assertThat().
                body("msg", is(equalTo("success")));
    }

//    @Test
//    public void validate_create_workspace_file() {
//        File file = new File("src/main/resources/ComplexJsonPayload.json");
//        given().
//                body(file).
//                when().
//                post("/postcomplexjson").
//                then().spec(responseSpecification)
//                .assertThat();
//    }

}