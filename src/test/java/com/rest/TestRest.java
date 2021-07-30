package com.rest;
import static io.restassured.RestAssured.*;

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
