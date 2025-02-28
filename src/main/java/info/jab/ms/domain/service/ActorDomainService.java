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
} 