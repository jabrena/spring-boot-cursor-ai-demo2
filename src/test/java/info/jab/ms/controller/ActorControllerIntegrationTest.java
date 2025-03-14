package info.jab.ms.controller;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class ActorControllerIntegrationTest {

    @Test
    public void testGetActorsEndpoint() {
        given()
            .when().get("/api/v1/actors")
            .then()
                .statusCode(200);
    }
} 