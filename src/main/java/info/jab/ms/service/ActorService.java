package info.jab.ms.service;

import info.jab.ms.dto.ActorDTO;
import info.jab.ms.repository.Actor;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ActorService {

    @Transactional
    public List<ActorDTO> getFirstTenActors() {
        List<Actor> actors = Actor.findFirstTen();
        
        // Convert entities to DTOs to maintain layer separation
        return actors.stream()
                .map(actor -> new ActorDTO(
                        actor.id,
                        actor.firstName,
                        actor.lastName,
                        actor.lastUpdate))
                .collect(Collectors.toList());
    }
} 