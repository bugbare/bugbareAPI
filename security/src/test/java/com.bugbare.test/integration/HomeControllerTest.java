package com.bugbare.test.integration;

import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class HomeControllerTest {

//    @Test
//    public void getEmployeeList() {
//
//        given().get("http://localhost:8080/").then().statusCode(200).log().all();
//    }

    @BeforeEach
    public void setup() {
        baseURI = "http://localhost";
        basePath = "/api";
        port = 8080;
    }

    @Test
    public void Unauthorised() {
        given()
                .get("/employees")
                .then()
                .statusCode(401);
    }

    @Test
    public void Authorised() {
        given().auth().basic("manager1", "manager1").get("/employees").then().statusCode(200)
                .assertThat()
                .body(containsString("Frodo"))
                .body(containsString("Gandalf"))
                .body(containsString("Bilbo"))
                .body(containsString("Baggins"))
                .body(containsString("pipe smoker"))
                .body(containsString("pipe smoker"))
                .log().all();
    }

    @Test
    public void EmployeeDetails() {
        given().auth().basic("manager1", "manager1").get("/employees/3").then().statusCode(200)
                .assertThat()
                .body(containsString("Frodo"))
                .body(containsString("Baggins"))
                .body(containsString("ring bearer"))
                .log().all();
    }

}
