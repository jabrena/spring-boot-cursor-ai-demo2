package info.jab.ms.domain.service;

import info.jab.ms.domain.model.Actor;

import java.util.List;

/**
 * Domain service for actor-related business logic.
 */
public interface ActorDomainService {
    
    /**
     * Gets a list of actors.
     * 
     * @param limit the maximum number of actors to retrieve
     * @return a list of actors
     */
    List<Actor> getActors(int limit);
    
    /**
     * Creates a new actor.
     * 
     * @param actor the actor to create
     * @return the created actor with any generated IDs or timestamps
     */
    Actor createActor(Actor actor);
} 