package tests;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class PostsApiTest {

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "https://dummyjson.com";
    }

    @Test(priority = 1 , description = "Get post 1")
    public void getPost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("His mother had always taught him"))
                .body("tags", equalTo(java.util.Arrays.asList(
                        "history", "american", "crime")))
                .body("reactions.likes", equalTo(192))
                .body("reactions.dislikes", equalTo(25));
    }

    @Test(priority = 2 , description = "Create a post")
    public void createPost() {
        String requestBody = """
                {
                  "title": "My REST Assured test post",
                  "body": "Testing a POST request with REST Assured",
                  "userId": 5
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("/posts/add")
                .then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("My REST Assured test post"))
                .body("body", equalTo("Testing a POST request with REST Assured"))
                .body("userId", equalTo(5));
    }

    @Test(priority = 3 , description = "Replace a post")
    public void replacePost() {
        String requestBody = """
                {
                  "title": "Updated REST Assured post",
                  "body": "Testing a PUT request with REST Assured",
                  "userId": 5
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Updated REST Assured post"))
                .body("body", equalTo("Testing a PUT request with REST Assured"))
                .body("userId", equalTo(5));
    }

    @Test(priority = 4 , description = "Partially update a post")
    public void patchPost() {
        String requestBody = """
                {
                  "title": "Patched REST Assured post"
                }
                """;

        given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .patch("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("title", equalTo("Patched REST Assured post"));
    }

    @Test(priority = 5 , description = "Delete a post")
    public void deletePost() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("isDeleted", equalTo(true));
    }
}
