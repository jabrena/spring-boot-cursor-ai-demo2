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

import jakarta.validation.Valid;
import java.util.List;

/**
 * REST controller for actor-related endpoints.
 */
@RestController
@RequestMapping("/api/v1")
public class ActorController {

    private final ActorService actorService;

    public ActorController(ActorService actorService) {
        this.actorService = actorService;
    }

    /**
     * Get first ten actors.
     *
     * @return list of actor DTOs
     */
    @GetMapping("/actors")
    public List<ActorDTO> getFirstTenActors() {
        // Get domain objects from the application service
        List<Actor> actors = actorService.getFirstTenActors();
        
        // Map domain objects to DTOs for the API response
        return mapToActorDTOList(actors);
    }
    
    /**
     * Create a new actor.
     *
     * @param request the actor creation request
     * @return the created actor
     */
    @PostMapping("/actors")
    public ResponseEntity<ActorDTO> createActor(@Valid @RequestBody CreateActorDTO request) {
        // Delegate to the application service
        Actor createdActor = actorService.createActor(request.firstName(), request.lastName());
        
        // Map to DTO and return with 201 Created status
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(mapToActorDTO(createdActor));
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