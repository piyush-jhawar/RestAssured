package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.workspace.Workspace;
import com.rest.pojo.workspace.WorkspaceRoot;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class WorkspacePojoTest {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-6111fef2a8ce940036221e6e-4c1b0ab28b3a0f13480e06b3b5040b348d").
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
    public void workspace_serialize_deserialize() throws JsonProcessingException {
        Workspace workspace = new Workspace("Pojo workspace", "team", "This is team pojo workspace");
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot deserworkspaceroot = given().
                body(workspaceRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                extract().response().
                as(WorkspaceRoot.class);

        assertThat(deserworkspaceroot.getWorkspace().getName(), is(equalTo("Pojo workspace")));
        assertThat(deserworkspaceroot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));

    }

    @Test(dataProvider = "workspace")
    public void workspace_serialize_deserialize_withdataprovider(String name, String type, String description) throws JsonProcessingException {
        Workspace workspace = new Workspace(name, type, description);
        WorkspaceRoot workspaceRoot = new WorkspaceRoot(workspace);

        WorkspaceRoot deserworkspaceroot = given().
                body(workspaceRoot).
                when().
                post("/workspaces").
                then().spec(responseSpecification).
                extract().response().
                as(WorkspaceRoot.class);

//        assertThat(deserworkspaceroot.getWorkspace().getName(), is(equalTo("Pojo workspace")));
        assertThat(deserworkspaceroot.getWorkspace().getId(), matchesPattern("^[a-z0-9-]{36}$"));

    }

    @DataProvider(name = "workspace")
    public Object[][] getWorkspace() {
        return new Object[][]{
                {"Pojo workspace", "team", "This is team pojo workspace"},
                {"Pojo workspace per", "personal", "This is team pojo workspace"}
        };
    }
}
