package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.Matchers.*;

public class JacksonAPI_JSONArray {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
                addHeader("x-mock-match-request-body", "true").
                setContentType("application/json").
        log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(200).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void validate_create_workspace_json_array_as_list() throws JsonProcessingException {
        HashMap<String,String> obj5001 = new HashMap();
        obj5001.put("id", "5001");
        obj5001.put("type", "None");
        HashMap<String,String> obj5002 = new HashMap();
        obj5002.put("id", "5002");
        obj5002.put("type", "Glazed");

        List<HashMap<String, String>> jsonList = new ArrayList();
        jsonList.add(obj5001);
        jsonList.add(obj5002);

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonListStr = objectMapper.writeValueAsString(jsonList);
        System.out.println(jsonListStr);

        given().
                body(jsonList).
        when().
                post("/post").
        then().spec(responseSpecification)
                .assertThat().
                body("msg", is(equalTo("success")));
    }

    @Test
    public void serialize_json_array_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayNode arrayNodeList = objectMapper.createArrayNode();

        ObjectMapper objectMapper1 = new ObjectMapper();
        ObjectNode objectNode5001 = objectMapper1.createObjectNode();
        objectNode5001.put("id", "5001");
        objectNode5001.put("type", "None");

        ObjectMapper objectMapper2 = new ObjectMapper();
        ObjectNode objectNode5002 = objectMapper2.createObjectNode();
        objectNode5002.put("id", "5002");
        objectNode5002.put("type", "Glazed");

        arrayNodeList.add(objectNode5001);
        arrayNodeList.add(objectNode5002);

        String jsonListStr = objectMapper.writeValueAsString(arrayNodeList);

        given().
                body(jsonListStr).
        when().
                post("/post").
        then().spec(responseSpecification)
                .assertThat().
                body("msg", is(equalTo("success")));
    }

}
