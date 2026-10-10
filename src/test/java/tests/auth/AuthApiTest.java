package tests.auth;

import io.qameta.allure.Allure;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

public class AuthApiTest {

    private static final String BASE_URL = "https://dummyjson.com";
    private static final String USERNAME = "emilys";
    private static final String PASSWORD = "emilyspass";

    @Test
    public void loginReturnsAccessAndRefreshTokens() {
        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "username", USERNAME,
                        "password", PASSWORD,
                        "expiresInMins", 30
                ))
                .when()
                .post("/auth/login");

        response.then()
                .statusCode(200)
                .body("username", equalTo(USERNAME))
                .body("accessToken", not(emptyOrNullString()))
                .body("refreshToken", not(emptyOrNullString()));

        String accessToken = response.path("accessToken");
        showAccessToken(accessToken);
    }

    @Test
    public void bearerTokenAuthenticatesCurrentUser() {
        String accessToken = getAccessToken();

        given()
                .baseUri(BASE_URL)
                .auth().oauth2(accessToken)
                .when()
                .get("/auth/me")
                .then()
                .statusCode(200)
                .body("username", equalTo(USERNAME));
    }

    private String getAccessToken() {
        Response response = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "username", USERNAME,
                        "password", PASSWORD
                ))
                .when()
                .post("/auth/login");

        response.then().statusCode(200);

        String accessToken = response.path("accessToken");
        showAccessToken(accessToken);

        return accessToken;
    }

    private void showAccessToken(String accessToken) {
        System.out.println("Access token: " + accessToken);
        Allure.attachment("Access token", "text/plain", accessToken);
    }
}