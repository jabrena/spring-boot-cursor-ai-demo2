package info.jab.ms.adapter.persistence;

import info.jab.ms.domain.model.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorRepositoryAdapterTest {

    @Mock
    private SpringActorRepository springActorRepository;

    @InjectMocks
    private ActorRepositoryAdapter actorRepositoryAdapter;

    @Captor
    private ArgumentCaptor<Pageable> pageableCaptor;

    @Test
    void shouldConvertEntitiesToDomainModels() {
        // Given
        int limit = 10;
        LocalDateTime now = LocalDateTime.now();
        
        ActorEntity entity = new ActorEntity();
        entity.setId(1L);
        entity.setFirstName("John");
        entity.setLastName("Doe");
        entity.setLastUpdate(now);
        
        when(springActorRepository.findAllBy(any(Pageable.class))).thenReturn(List.of(entity));

        // When
        List<Actor> result = actorRepositoryAdapter.findActors(limit);

        // Then
        verify(springActorRepository).findAllBy(pageableCaptor.capture());
        assertThat(pageableCaptor.getValue()).isEqualTo(PageRequest.of(0, limit));
        
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        Actor actor = result.get(0);
        assertThat(actor.getId()).isEqualTo(entity.getId());
        assertThat(actor.getFirstName()).isEqualTo(entity.getFirstName());
        assertThat(actor.getLastName()).isEqualTo(entity.getLastName());
        assertThat(actor.getLastUpdate()).isEqualTo(entity.getLastUpdate());
    }
} 