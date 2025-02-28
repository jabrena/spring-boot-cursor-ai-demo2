package info.jab.ms.adapter.persistence;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.model.ActorRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the ActorRepository port from the domain layer.
 * This adapter uses Spring Data repositories to access the database.
 */
@Component
public class ActorRepositoryAdapter implements ActorRepository {

    private final SpringActorRepository springActorRepository;

    @Autowired
    public ActorRepositoryAdapter(SpringActorRepository springActorRepository) {
        this.springActorRepository = springActorRepository;
    }

    @Override
    public List<Actor> findActors(int limit) {
        // Use the Spring Data repository to fetch data
        List<ActorEntity> entities = springActorRepository.findAllBy(PageRequest.of(0, limit));
        
        // Map the persistence entities to domain models
        return entities.stream()
                .map(entity -> new Actor(
                        entity.getId(),
                        entity.getFirstName(),
                        entity.getLastName(),
                        entity.getLastUpdate()))
                .collect(Collectors.toList());
    }
} 