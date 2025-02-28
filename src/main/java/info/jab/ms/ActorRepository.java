package info.jab.ms;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import java.util.List;

@Repository
public interface ActorRepository extends PagingAndSortingRepository<Actor, Long> {
    List<Actor> findAllBy(Pageable pageable);
}
