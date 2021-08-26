package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.rest.pojo.collection.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.json.JSONException;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.ValueMatcher;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class ComplexPojoTest {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "").
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
    public void complex_pojo_create_collection() throws JsonProcessingException, JSONException {
        Header header = new Header("Content-Type", "application/json");
        List<Header> headerList = new ArrayList<>();
        headerList.add(header);

        Body body = new Body("raw", "{\"data\": \"123\"}");

        Request request = new Request("https://postman-echo.com/post", "POST", headerList, body, "This is a sample POST Request");

        RequestRoot requestRoot = new RequestRoot("Sample POST Request", request);
        List<RequestRoot> requestRootList = new ArrayList<>();
        requestRootList.add(requestRoot);

        Folder folder = new Folder("This is a folder", requestRootList);

        Info info = new Info("Sample Collection", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        List<Folder> itemList = new ArrayList<>();
        itemList.add(folder);

        Collection collection = new Collection(info, itemList);

        CollectionRoot collectionRoot = new CollectionRoot(collection);

        String collectionuid  = given().
                body(collectionRoot).queryParams("workspace", "0c011f6c-4a8e-4fdb-baf3-6f1e6a2f8e2a").
                when().post("collections").
                then().spec(responseSpecification).
                extract().response().path("collection.uid");

        CollectionRoot collectionRootResponse = given().pathParam("collectionuid", collectionuid).
        when().
                get("collections/{collectionuid}").
        then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRoot.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String collectionResponseStr = objectMapper.writeValueAsString(collectionRootResponse);

        JSONAssert.assertEquals(collectionRootStr, collectionResponseStr,
                new CustomComparator(JSONCompareMode.STRICT_ORDER,
                new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
                    public boolean equal(Object o1, Object o2) {
                        return true;
                    }
                })));
    }
}
