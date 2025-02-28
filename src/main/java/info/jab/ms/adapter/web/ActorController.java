package info.jab.ms.adapter.web;

import info.jab.ms.application.ActorService;
import info.jab.ms.domain.model.Actor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * REST controller for actor-related endpoints.
 */
@RestController
@RequestMapping("/api/v1")
public class ActorController {

    private final ActorService actorService;

    @Autowired
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
        return actors.stream()
                .map(actor -> new ActorDTO(
                        actor.getId(),
                        actor.getFirstName(),
                        actor.getLastName(),
                        actor.getLastUpdate()))
                .collect(Collectors.toList());
    }
} 