package com.banking;

import com.banking.dto.AccountDto;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AccountIntegrationTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    public void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;

        // Test කරන්න කලින් User කෙනෙක් හදලා Login වෙලා Token එක ගන්නවා
        registerUser();
        token = loginAndGetToken();
    }

    private void registerUser() {
        String userJson = "{\"username\": \"qatest\", \"password\": \"123\"}";
        given().contentType(ContentType.JSON).body(userJson)
                .when().post("/api/auth/register"); // Error awe nattam hari
    }

    private String loginAndGetToken() {
        String loginJson = "{\"username\": \"qatest\", \"password\": \"123\"}";
        return given()
                .contentType(ContentType.JSON)
                .body(loginJson)
                .when().post("/api/auth/login")
                .then().extract().path("token");
    }

    @Test
    public void testCreateAccountAPI() {
        String accountJson = "{\"accountHolderName\": \"QA Auto\", \"balance\": 5000.0, \"accountType\": \"SAVINGS\"}";

        given()
                .header("Authorization", "Bearer " + token) // Token eka yawanna oni
                .contentType(ContentType.JSON)
                .body(accountJson)
                .when()
                .post("/api/accounts")
                .then()
                .statusCode(200) // 200 OK enna oni
                .body("accountHolderName", equalTo("QA Auto")); // Namath hariyanna oni
    }
}