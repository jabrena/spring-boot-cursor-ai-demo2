package info.jab.ms.adapter.web;

import info.jab.ms.application.ActorService;
import info.jab.ms.domain.model.Actor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.validation.Valid;
import java.util.Collections;
import java.util.List;

/**
 * REST controller for actor-related endpoints.
 */
@RestController
@RequestMapping("/api/v1")
public class ActorController {

    private static final Logger log = LoggerFactory.getLogger(ActorController.class);
    private static final String ACTOR_SERVICE = "actorService";

    private final ActorService actorService;
    private final CircuitBreakerFactory circuitBreakerFactory;

    public ActorController(ActorService actorService, CircuitBreakerFactory circuitBreakerFactory) {
        this.actorService = actorService;
        this.circuitBreakerFactory = circuitBreakerFactory;
    }

    /**
     * Get first ten actors.
     *
     * @return list of actor DTOs
     */
    @GetMapping("/actors")
    public List<ActorDTO> getFirstTenActors() {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create(ACTOR_SERVICE);
        return circuitBreaker.run(
            () -> {
                // Get domain objects from the application service
                List<Actor> actors = actorService.getFirstTenActors();
                
                // Map domain objects to DTOs for the API response
                return mapToActorDTOList(actors);
            },
            throwable -> getFirstTenActorsFallback(throwable)
        );
    }
    
    /**
     * Fallback method for getFirstTenActors when the circuit breaker is open.
     *
     * @param e the exception that triggered the fallback
     * @return empty list with appropriate log message
     */
    public List<ActorDTO> getFirstTenActorsFallback(Throwable e) {
        log.error("Circuit breaker triggered for getFirstTenActors: {}", e.getMessage());
        return Collections.emptyList();
    }
    
    /**
     * Create a new actor.
     *
     * @param request the actor creation request
     * @return the created actor
     */
    @PostMapping("/actors")
    public ResponseEntity<ActorDTO> createActor(@Valid @RequestBody CreateActorDTO request) {
        CircuitBreaker circuitBreaker = circuitBreakerFactory.create(ACTOR_SERVICE);
        return circuitBreaker.run(
            () -> {
                // Delegate to the application service
                Actor createdActor = actorService.createActor(request.firstName(), request.lastName());
                
                // Map to DTO and return with 201 Created status
                return ResponseEntity
                        .status(HttpStatus.CREATED)
                        .body(mapToActorDTO(createdActor));
            },
            throwable -> createActorFallback(request, throwable)
        );
    }
    
    /**
     * Fallback method for createActor when the circuit breaker is open.
     *
     * @param request the actor creation request
     * @param e the exception that triggered the fallback
     * @return service unavailable response
     */
    public ResponseEntity<ActorDTO> createActorFallback(CreateActorDTO request, Throwable e) {
        log.error("Circuit breaker triggered for createActor: {}", e.getMessage());
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
    }
    
    private List<ActorDTO> mapToActorDTOList(List<Actor> actors) {
        return actors.stream()
                .map(this::mapToActorDTO)
                .toList();
    }
    
    private ActorDTO mapToActorDTO(Actor actor) {
        return new ActorDTO(
                actor.id(),
                actor.firstName(),
                actor.lastName(),
                actor.lastUpdate());
    }
} 