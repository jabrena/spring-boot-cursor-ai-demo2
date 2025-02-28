package info.jab.ms.application;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.service.ActorDomainService;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Application service for actor operations.
 * Acts as a facade between the web adapter and the domain layer.
 */
@Service
public class ActorService {

    private final ActorDomainService actorDomainService;

    public ActorService(ActorDomainService actorDomainService) {
        this.actorDomainService = actorDomainService;
    }

    /**
     * Gets the first ten actors.
     *
     * @return a list of actors
     */
    public List<Actor> getFirstTenActors() {
        return actorDomainService.getActors(10);
    }
    
    /**
     * Creates a new actor.
     *
     * @param firstName the actor's first name
     * @param lastName the actor's last name
     * @return the created actor with generated ID and timestamp
     */
    public Actor createActor(String firstName, String lastName) {
        // Create a new Actor with null ID and current timestamp
        // The ID will be generated by the database
        Actor actor = new Actor(null, firstName, lastName, LocalDateTime.now());
        
        // Delegate to the domain service
        return actorDomainService.createActor(actor);
    }
} 