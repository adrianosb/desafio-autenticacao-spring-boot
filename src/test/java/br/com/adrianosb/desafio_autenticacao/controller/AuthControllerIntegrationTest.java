package br.com.adrianosb.desafio_autenticacao.controller;

import br.com.adrianosb.desafio_autenticacao.dto.LoginRequest;
import br.com.adrianosb.desafio_autenticacao.security.JwtUtil;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AuthControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private JwtUtil jwtUtil;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Test
    public void testInvalidToken() {
        given()
                .when()
                .get("/foo-bar")
                .then()
                .statusCode(401);
    }

    @Test
    public void testValidToken() {
        String token = given()
                .contentType("application/json")
                .body(new LoginRequest("user", "user"))
                .when()
                .post("/signin")
                .then()
                .statusCode(200)
                .extract()
                .asString();

        String usernameFromToken = jwtUtil.getUsernameFromToken(token);
        assertEquals("user", usernameFromToken);
    }

    @Test
    public void testAuthentication() {
        String token = jwtUtil.generateToken("user");

        given()
                .header(new Header("Authorization", "Bearer " + token))
                .when()
                .get("/foo-bar")
                .then()
                .statusCode(204);
    }
}
