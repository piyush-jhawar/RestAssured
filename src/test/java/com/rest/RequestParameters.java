package com.rest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.*;
import java.util.HashMap;

import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RequestParameters {

    @Test
    public void single_query_parameter() {
        given().
                baseUri("https://postman-echo.com").
//                param("foo1", "bar1").
                queryParam("foo1", "bar1").
                log().all().

        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_query_parameter() {
        given().
                baseUri("https://postman-echo.com").
//                param("foo1", "bar1").
                queryParam("foo1", "bar1").
                queryParam("foo2", "bar2").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_query_parameter_hashmap() {
        HashMap<String, String> queryParams = new HashMap<>();
        queryParams.put("foo1", "bar1");
        queryParams.put("foo2", "bar2");

        given().
                baseUri("https://postman-echo.com").
                queryParams(queryParams).
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multiple_value_parameter() {

        given().
                baseUri("https://postman-echo.com").
                queryParam("foo1", "bar1, bar2, bar3").
                log().all().
        when().
                get("/get").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void path_parameter() {

        given().
                baseUri("https://reqres.in").
                pathParam("userId", "2").
                log().all().
        when().
                get("/api/users/{userId}").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void multipart_form_data() {

        given().
                baseUri("https://postman-echo.com").
                multiPart("foo1", "bar1").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void upload_file_multipart_form_data() {
        String attributes = "{\n" +
                "    \"name\": \"temp.txt\",\n" +
                "    \"parent\": {\n" +
                "        \"id\": \"123456\"\n" +
                "    }\n" +
                "}";

        given().
                baseUri("https://postman-echo.com").
                multiPart("file", new File("temp.txt")).
                multiPart("attributes", attributes, "application/json").
                log().all().
        when().
                post("/post").
        then().
                log().all().
                assertThat().
                statusCode(200);
    }

    @Test
    public void download_file_multipart_form_data_bytes() throws IOException {

        byte[] bytes = given().
                               baseUri("https://raw.githubusercontent.com").
                               log().all().
                       when().
                               get("/appium/appium/master/sample-code/apps/ApiDemos-debug.apk").
                       then().
                               log().all().extract().response().asByteArray();

        OutputStream os = new FileOutputStream(new File("ApiDemos-debug.apk"));
        os.write(bytes);
        os.close();
    }

    @Test
    public void download_file_multipart_form_data_inputstream() throws IOException {

        InputStream inputStream = given().
                                          baseUri("https://raw.githubusercontent.com").
                                          log().all().
                                  when().
                                          get("/appium/appium/master/sample-code/apps/ApiDemos-debug.apk").
                                  then().
                                          log().all().extract().response().asInputStream();

        OutputStream os = new FileOutputStream(new File("ApiDemos-debug2.apk"));
        byte[] bytes = new byte[inputStream.available()];
        inputStream.read(bytes);
        os.write(bytes);
        os.close();
    }

    @Test
    public void form_url_encoded() {
        given().
                baseUri("https://postman-echo.com").
                config(config().encoderConfig(EncoderConfig.encoderConfig().
                        appendDefaultContentCharsetToContentTypeIfUndefined(false))).
                formParam("key1", "value1").
                formParam("key 2", "value 2").
                log().all().
        when().
                post("/post").
        then().
                log().all();
    }
}
