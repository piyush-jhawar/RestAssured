package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.rest.pojo.users.Address;
import com.rest.pojo.users.Company;
import com.rest.pojo.users.Geo;
import com.rest.pojo.users.UserRoot;
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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class UserPojoTest {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://jsonplaceholder.typicode.com/").
                setContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.requestSpecification = requestSpecBuilder.build();

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder().
                expectStatusCode(201).
                expectContentType(ContentType.JSON).
                log(LogDetail.ALL);
        RestAssured.responseSpecification = responseSpecBuilder.build();

    }

    @Test
    public void workspace_serialize_deserialize() throws JsonProcessingException {
        Geo geo = new Geo("-37.3159", "81.1496");
        Address address = new Address("Kulas Light", "Apt. 556", "Gwenborough", "92998-3874", geo);
        Company company = new Company("Romaguera-Crona", "Multi-layered client-server neural-net", "harness real-time e-markets");

        UserRoot userRoot = new UserRoot("Leanne Graham", "Bret", "Sincere@april.biz", address, "1-770-736-8031 x56442", "hildegard.org", company);

        UserRoot deseruserroot = given().
                body(userRoot).
                when().
                post("/users").
                then().spec(responseSpecification).
                extract().response().
                as(UserRoot.class);

        assertThat(deseruserroot.getName(), is(equalTo("Leanne Graham")));
        assertThat(deseruserroot.getId(), is(equalTo("11")));
        assertThat(deseruserroot.getId(), is(notNullValue()));

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
