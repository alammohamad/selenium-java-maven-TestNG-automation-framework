package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import org.testng.Assert;
import org.testng.annotations.Test;

public class APITest {

    // API Base URL
    private String apiBaseUrl =
            "https://jsonplaceholder.typicode.com";

    @Test(description = "Verify GET Post API")
    public void testGetPost() {

        System.out.println(
                "API Base URL: " + apiBaseUrl
        );

        Response response = RestAssured
                .given()
                .baseUri(apiBaseUrl)
                .when()
                .get("/posts/1");

        Assert.assertEquals(
                response.getStatusCode(),
                200,
                "Expected HTTP status code 200"
        );

        Assert.assertTrue(
                response.getBody()
                        .asString()
                        .contains("userId"),
                "Response does not contain userId"
        );
    }
}


// formal lines CTL+Shift+f
//close all files CTL+Shift+w