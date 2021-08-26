package com.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.pojo.collectioninheritance.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
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

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.responseSpecification;
import static org.hamcrest.MatcherAssert.assertThat;

public class ComplexPojoInheritanceTest {

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://api.postman.com").
                addHeader("X-Api-Key", "PMAK-61272ff526eb2f004634abe2-8335dc92e9ab8e625dd2575e62bbc85db1").
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

        RequestRequest request = new RequestRequest("POST", headerList, body, "This is a sample POST Request", "https://postman-echo.com/post");

        RequestRootRequest requestRootRequest = new RequestRootRequest("Sample POST Request", request);
        List<RequestRootRequest> requestRootRequestList = new ArrayList<>();
        requestRootRequestList.add(requestRootRequest);

        FolderRequest folder = new FolderRequest("This is a folder", requestRootRequestList);

        Info info = new Info("Sample Collection", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        List<FolderRequest> itemList = new ArrayList<>();
        itemList.add(folder);

        CollectionRequest collectionRequest = new CollectionRequest(info, itemList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collectionRequest);

        String collectionuid  = given().
                body(collectionRoot).queryParams("workspace", "0c011f6c-4a8e-4fdb-baf3-6f1e6a2f8e2a").
                when().post("collections").
                then().spec(responseSpecification).
                extract().response().path("collection.uid");

        CollectionRootResponse collectionRootResponse = given().pathParam("collectionuid", collectionuid).
        when().
                get("collections/{collectionuid}").
        then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRootResponse.class);

        List<String> urlRequestList = new ArrayList<>();
        List<String> urlReponseList = new ArrayList<>();

        for (RequestRootRequest requestRootRequest1 : requestRootRequestList) {
            urlRequestList.add(requestRootRequest1.getRequest().getUrl());
        }

        List<FolderResponse> folderResponseList = collectionRootResponse.getCollection().getItem();

        for (FolderResponse folderResponse: folderResponseList) {
            List<RequestRootResponse> requestRootResponseList = folderResponse.getItem();
            for (RequestRootResponse requestRootResponse: requestRootResponseList) {
                URL url = requestRootResponse.getRequest().getUrl();
                urlReponseList.add(url.getRaw());

            }

        }
        assertThat(urlReponseList, Matchers.containsInAnyOrder(urlRequestList.toArray()));
    }

    @Test
    public void simple_pojo_create_collection() throws JsonProcessingException, JSONException {

        Info info = new Info("Sample Collection111", "This is just a sample collection.", "https://schema.getpostman.com/json/collection/v2.1.0/collection.json");

        List<FolderRequest> itemList = new ArrayList<>();

        CollectionRequest collectionRequest = new CollectionRequest(info, itemList);

        CollectionRootRequest collectionRoot = new CollectionRootRequest(collectionRequest);

        String collectionuid = given().
                body(collectionRoot).queryParams("workspace", "0c011f6c-4a8e-4fdb-baf3-6f1e6a2f8e2a").
                when().post("collections").
                then().spec(responseSpecification).
                extract().response().path("collection.uid");

        CollectionRootResponse collectionRootResponse = given().pathParam("collectionuid", collectionuid).
                when().
                get("collections/{collectionuid}").
                then().spec(responseSpecification).
                extract().
                response().
                as(CollectionRootResponse.class);

        ObjectMapper objectMapper = new ObjectMapper();
        String collectionRootStr = objectMapper.writeValueAsString(collectionRoot);
        String collectionResponseStr = objectMapper.writeValueAsString(collectionRootResponse);

//        JSONAssert.assertEquals(collectionRootStr, collectionResponseStr,
//                new CustomComparator(JSONCompareMode.STRICT_ORDER,
//                        new Customization("collection.item[*].item[*].request.url", new ValueMatcher<Object>() {
//                            public boolean equal(Object o1, Object o2) {
//                                return true;
//                            }
//                        })));

        assertThat(objectMapper.readTree(collectionRootStr), Matchers.equalTo(objectMapper.readTree(collectionResponseStr)));

    }
}
