package info.jab.ms.adapter.persistence;

import info.jab.ms.domain.model.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorRepositoryAdapterTest {

    @Mock
    private SpringActorRepository actorRepository;

    @InjectMocks
    private ActorRepositoryAdapter actorRepositoryAdapter;

    @Captor
    private ArgumentCaptor<Integer> limitCaptor;

    @Test
    void shouldConvertEntitiesToDomainModels() {
        // Given
        int limit = 5;
        LocalDateTime now = LocalDateTime.now();
        
        ActorEntity entity = new ActorEntity(1L, "John", "Doe", now);
        
        when(actorRepository.findActorsWithLimit(anyInt())).thenReturn(List.of(entity));

        // When
        List<Actor> result = actorRepositoryAdapter.findActors(limit);

        // Then
        verify(actorRepository).findActorsWithLimit(limitCaptor.capture());
        assertThat(limitCaptor.getValue()).isEqualTo(limit);
        
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        Actor actor = result.get(0);
        assertThat(actor.id()).isEqualTo(entity.id());
        assertThat(actor.firstName()).isEqualTo(entity.firstName());
        assertThat(actor.lastName()).isEqualTo(entity.lastName());
        assertThat(actor.lastUpdate()).isEqualTo(entity.lastUpdate());
    }
    
    @Test
    void shouldUseCorrectLimitWhenLimitIs10() {
        // Given
        int limit = 10;
        LocalDateTime now = LocalDateTime.now();
        
        ActorEntity entity = new ActorEntity(1L, "John", "Doe", now);
        
        when(actorRepository.findActorsWithLimit(anyInt())).thenReturn(List.of(entity));

        // When
        List<Actor> result = actorRepositoryAdapter.findActors(limit);

        // Then
        verify(actorRepository).findActorsWithLimit(limitCaptor.capture());
        assertThat(limitCaptor.getValue()).isEqualTo(limit);
        
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        Actor actor = result.get(0);
        assertThat(actor.id()).isEqualTo(entity.id());
        assertThat(actor.firstName()).isEqualTo(entity.firstName());
        assertThat(actor.lastName()).isEqualTo(entity.lastName());
        assertThat(actor.lastUpdate()).isEqualTo(entity.lastUpdate());
    }
} 