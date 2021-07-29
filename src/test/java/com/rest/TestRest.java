package com.rest;
import static io.restassured.RestAssured.*;
import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.Test;



public class TestRest {

    @Test
    public void test() {
        given().
        when().
        then();
        System.out.println("Hi from TestNg");
    }
}
