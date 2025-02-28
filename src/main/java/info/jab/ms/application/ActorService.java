package info.jab.ms.application;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.service.ActorDomainService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Application service for actor operations.
 * Acts as a facade between the web adapter and the domain layer.
 */
@Service
public class ActorService {

    private final ActorDomainService actorDomainService;

    @Autowired
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
} 