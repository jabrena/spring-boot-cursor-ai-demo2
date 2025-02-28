package info.jab.ms.adapter.persistence;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Test repository for integration tests extending ListCrudRepository 
 * to access save method directly.
 */
@Repository
public interface TestSpringActorRepository extends ListCrudRepository<ActorEntity, Long> {
    /**
     * Find actors with a specified limit.
     * 
     * @param limit maximum number of actors to retrieve
     * @return list of actor entities limited by the specified count
     */
    @Query("SELECT * FROM actor LIMIT :limit")
    List<ActorEntity> findActorsWithLimit(@Param("limit") int limit);
} 