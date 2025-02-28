package info.jab.ms.service;

import info.jab.ms.dto.ActorDTO;
import info.jab.ms.repository.Actor;
import info.jab.ms.repository.ActorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActorService {

    private final ActorRepository actorRepository;

    @Autowired
    public ActorService(ActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    public List<ActorDTO> getFirstTenActors() {
        List<Actor> actors = actorRepository.findAllBy(PageRequest.of(0, 10));
        
        // Convert entities to DTOs to maintain layer separation
        return actors.stream()
                .map(actor -> new ActorDTO(
                        actor.getId(),
                        actor.getFirstName(),
                        actor.getLastName(),
                        actor.getLastUpdate()))
                .collect(Collectors.toList());
    }
} 