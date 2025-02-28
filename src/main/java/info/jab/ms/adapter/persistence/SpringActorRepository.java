package info.jab.ms.adapter.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository interface for actor entities.
 */
@Repository
public interface SpringActorRepository extends PagingAndSortingRepository<ActorEntity, Long> {
    
    /**
     * Find all actors with pagination.
     *
     * @param pageable pagination information
     * @return list of actor entities
     */
    List<ActorEntity> findAllBy(Pageable pageable);
} 