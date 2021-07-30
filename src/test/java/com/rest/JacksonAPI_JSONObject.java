package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class JacksonAPI_JSONObject {

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
    public void serialize_map_to_json_using_jackson() throws JsonProcessingException {
        HashMap<String, Object> mainobject = new HashMap();
        HashMap<String, String> nestedobject = new HashMap();
        nestedobject.put("name", "Jackson Workspace");
        nestedobject.put("type", "team");
        nestedobject.put("description", "Creating a Team Workspace using the Postman API");

        mainobject.put("workspace", nestedobject);

        ObjectMapper objectMapper = new ObjectMapper();
        String mainObjectStr = objectMapper.writeValueAsString(mainobject);
        System.out.println(mainObjectStr);

        given().
                body(mainobject).
        when().
                post("/workspaces").
        then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("Jackson Workspace")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

    @Test
    public void serialize_json_using_jackson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode nestedobjectNode = objectMapper.createObjectNode();
        nestedobjectNode.put("name", "Jackson Workspace");
        nestedobjectNode.put("type", "team");
        nestedobjectNode.put("description", "Creating a Team Workspace using the Postman API");

        ObjectNode mainobjectNode = objectMapper.createObjectNode();
        mainobjectNode.set("workspace", nestedobjectNode);
        String mainObjectStr = objectMapper.writeValueAsString(mainobjectNode);

        given().
                body(mainObjectStr).
        when().
                post("/workspaces").
        then().spec(responseSpecification)
                .assertThat().
                body("workspace.name", is(equalTo("Jackson Workspace")),
                        "workspace.id", matchesPattern("^[a-z0-9-]{36}$"));
    }

}
