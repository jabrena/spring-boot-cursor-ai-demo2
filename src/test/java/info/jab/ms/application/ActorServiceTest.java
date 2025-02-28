package info.jab.ms.application;

import info.jab.ms.domain.model.Actor;
import info.jab.ms.domain.service.ActorDomainService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorDomainService actorDomainService;

    @InjectMocks
    private ActorService actorService;

    /**
     * Test that verifies the ActorService correctly delegates to the domain service
     * and returns the results.
     */
    @Test
    void shouldReturnFirstTenActors() {
        // Given
        Actor actor = new Actor(1L, "First", "Last", LocalDateTime.now());
        when(actorDomainService.getActors(10)).thenReturn(List.of(actor));

        // When
        List<Actor> result = actorService.getFirstTenActors();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getFirstName()).isEqualTo("First");
        assertThat(result.get(0).getLastName()).isEqualTo("Last");
    }
} 