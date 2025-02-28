package info.jab.ms.domain.service;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.model.ActorRepository;

import java.util.List;

/**
 * Implementation of the actor domain service.
 */
public class ActorDomainServiceImpl implements ActorDomainService {
    
    private final ActorRepository actorRepository;
    
    public ActorDomainServiceImpl(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }
    
    @Override
    public List<Actor> getActors(int limit) {
        return actorRepository.findActors(limit);
    }
} 