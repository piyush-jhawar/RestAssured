package com.rest.gmail;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import io.restassured.config.LogConfig;

import javax.crypto.MacSpi;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

public class GmailApi {
    String accessToken = "ya29.a0ARrdaM8-s1YHSq7xzQ47aG4I1Dhbp6lvcLElzYLPsIx3mp7zg1-fA8ZeGiEDkW_JjhNMPm41J3jp1ts6wwwb48rIgfMFSgcvIF7B0ljZGvr7EtUCgU0ggftyeee8TAgmkLoIczjCNlSVIsf-GhztWPgdomX9Ew";

    @BeforeClass
    public void beforeClass() {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder().
                setBaseUri("https://gmail.googleapis.com").
                setConfig(config.logConfig(LogConfig.logConfig().blacklistHeader("Authorization"))).
                addHeader("Authorization", "Bearer " + accessToken).
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
    public void getUserProfile() {
        String email = "restomjain@gmail.com";

        given().
                basePath("gmail/v1").
                pathParam("email", email).
        when().
                get("users/{email}/profile").
        then().spec(responseSpecification).
                log().all();
    }

    @Test
    public void sendMessage() {
        String email = "restomjain@gmail.com";
        String message = "From: restomjain@gmail.com\n" +
                "To: piyushjhawar26@gmail.com\n" +
                "Subject: Test Rest Assured Email\n" +
                "\n" +
                "Sending from Gmail API using Rest Assured";
        String base64EncodedMessage = Base64.getUrlEncoder().encodeToString(message.getBytes());
        Map<String, String> msg = new HashMap<>();
        msg.put("raw", base64EncodedMessage);

        given().
                basePath("gmail/v1").
                pathParam("email", email).
                body(msg).
        when().
                post("users/{email}/messages/send").
        then().spec(responseSpecification).
                log().all();
    }


}
