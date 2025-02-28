package info.jab.ms.adapter.web;

import static org.assertj.core.api.Assertions.assertThat;

import info.jab.ms.config.ToxiproxyTestContainersConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.awaitility.Awaitility;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActorControllerCircuitBreakerIT extends ToxiproxyTestContainersConfig {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() throws IOException {
        restoreDatabaseConnection();
    }

    @AfterEach
    void tearDown() throws IOException {
        restoreDatabaseConnection();
    }

    @Test
    void getFirstTenActors_shouldReturnEmptyList_whenDatabaseIsDown() throws IOException {
        // First ensure normal operation works
        ResponseEntity<List<ActorDTO>> initialResponse = restTemplate.exchange(
                "/api/v1/actors",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<ActorDTO>>() {});
        
        assertThat(initialResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(initialResponse.getBody()).isNotEmpty();
        
        // Cut the connection to simulate database down
        simulateDatabaseDown();
        
        // Make requests until circuit breaker opens
        for (int i = 0; i < 10; i++) {
            restTemplate.getForEntity("/api/v1/actors", List.class);
        }
        
        // Now circuit should be open, verify fallback response
        ResponseEntity<List> response = restTemplate.getForEntity("/api/v1/actors", List.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEmpty();
        
        // Remove the toxic and verify system recovers
        restoreDatabaseConnection();
        
        // Wait for circuit to go to half-open and then closed
        Awaitility.await()
            .atMost(Duration.ofSeconds(10))
            .pollInterval(1, TimeUnit.SECONDS)
            .until(() -> {
                ResponseEntity<List<ActorDTO>> recoveryResponse = restTemplate.exchange(
                        "/api/v1/actors",
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<List<ActorDTO>>() {});
                return recoveryResponse.getBody() != null && !recoveryResponse.getBody().isEmpty();
            });
    }
    
    @Test
    void createActor_shouldReturnServiceUnavailable_whenDatabaseIsDown() throws IOException {
        // First ensure normal operation works
        CreateActorDTO actorDTO = new CreateActorDTO("John", "Doe");
        ResponseEntity<ActorDTO> initialResponse = restTemplate.postForEntity(
                "/api/v1/actors", 
                actorDTO, 
                ActorDTO.class);
        
        assertThat(initialResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        // Cut the connection to simulate database down
        simulateDatabaseDown();
        
        // Make requests until circuit breaker opens
        for (int i = 0; i < 10; i++) {
            restTemplate.postForEntity("/api/v1/actors", actorDTO, ActorDTO.class);
        }
        
        // Now circuit should be open, verify fallback response
        ResponseEntity<ActorDTO> response = restTemplate.postForEntity(
                "/api/v1/actors", 
                actorDTO, 
                ActorDTO.class);
        
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.SERVICE_UNAVAILABLE);
        
        // Remove the toxic and verify system recovers
        restoreDatabaseConnection();
    }
} 