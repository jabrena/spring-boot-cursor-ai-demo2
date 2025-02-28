package info.jab.ms.adapter.persistence;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.model.ActorRepository;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementation of the ActorRepository port from the domain layer.
 * This adapter uses Spring Data repositories to access the database.
 */
@Component
public class ActorRepositoryAdapter implements ActorRepository {

    private final SpringActorRepository actorRepository;

    public ActorRepositoryAdapter(SpringActorRepository actorRepository) {
        this.actorRepository = actorRepository;
    }

    @Override
    public List<Actor> findActors(int limit) {
        return actorRepository.findActorsWithLimit(limit).stream()
                .map(this::toActor)
                .toList();
    }
    
    @Override
    public Actor save(Actor actor) {
        ActorEntity savedEntity = actorRepository.save(toActorEntity(actor));
        return toActor(savedEntity);
    }
    
    /**
     * Maps a domain model to an entity.
     */
    private ActorEntity toActorEntity(Actor actor) {
        return new ActorEntity(
                actor.id(),
                actor.firstName(),
                actor.lastName(),
                actor.lastUpdate()
        );
    }
    
    /**
     * Maps an entity to a domain model.
     */
    private Actor toActor(ActorEntity entity) {
        return new Actor(
                entity.id(),
                entity.firstName(),
                entity.lastName(),
                entity.lastUpdate()
        );
    }
} 