package info.jab.ms.domain.model;

import java.util.List;

/**
 * Repository port for actor persistence operations.
 * This interface is defined in the domain layer and implemented by the persistence adapter.
 */
public interface ActorRepository {
    
    /**
     * Finds a specified number of actors.
     * 
     * @param limit the maximum number of actors to retrieve
     * @return a list of actors
     */
    List<Actor> findActors(int limit);
} 