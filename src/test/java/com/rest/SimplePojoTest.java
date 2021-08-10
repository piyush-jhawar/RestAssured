package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.simple.SimplePojo;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class SimplePojoTest {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://d2188390-af98-4793-b12e-be30414adcc5.mock.pstmn.io").
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
    public void simple_withoutpojo_example() {
        String payload = "{\n" +
                "    \"key1\": \"value1\",\n" +
                "    \"key2\": \"value2\"\n" +
                "}";

        given().
                body(payload).
        when().
                post("/postsimplepojo").
        then().
                spec(responseSpecification).
                assertThat();
    }

    @Test
    public void simple_withpojo_example() {
//        SimplePojo simplePojo = new SimplePojo("value1", "value2");
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");

        given().
                body(simplePojo).
                when().
                post("/postsimplepojo").
                then().
                spec(responseSpecification).
                assertThat().
                body("key1", is(equalTo(simplePojo.getKey1())),
                        "key2",is(equalTo(simplePojo.getKey2())));
    }

    @Test
    public void simple_req_res_pojo_match() throws JsonProcessingException {
//        SimplePojo simplePojo = new SimplePojo("value1", "value2");
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.setKey1("value1");
        simplePojo.setKey2("value2");

        SimplePojo deserializedPojo = given().
                body(simplePojo).
                when().
                post("/postsimplepojo").
                then().
                spec(responseSpecification).
                assertThat().
                extract().
                response().as(SimplePojo.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String deserializedPojoStr = objectMapper.writeValueAsString(deserializedPojo);
        String simplePojoStr = objectMapper.writeValueAsString(simplePojo);
        assertThat(objectMapper.readTree(deserializedPojoStr), is(equalTo(objectMapper.readTree(simplePojoStr))));
    }

}
