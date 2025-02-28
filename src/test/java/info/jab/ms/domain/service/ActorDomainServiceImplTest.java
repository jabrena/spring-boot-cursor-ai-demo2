package info.jab.ms.domain.service;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.model.ActorRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorDomainServiceImplTest {

    @Mock
    private ActorRepository actorRepository;

    @InjectMocks
    private ActorDomainServiceImpl actorDomainService;

    @Test
    void shouldReturnActorsFromRepository() {
        // Given
        int limit = 10;
        Actor actor = new Actor(1L, "John", "Doe", LocalDateTime.now());
        when(actorRepository.findActors(limit)).thenReturn(List.of(actor));

        // When
        List<Actor> result = actorDomainService.getActors(limit);

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0)).isEqualTo(actor);
        verify(actorRepository, times(1)).findActors(limit);
    }
} 