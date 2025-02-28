package info.jab.ms.adapter.web;

import info.jab.ms.application.ActorService;
import info.jab.ms.domain.model.Actor;
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
class ActorControllerTest {

    @Mock
    private ActorService actorService;

    @InjectMocks
    private ActorController actorController;

    @Test
    void shouldReturnDTOsFromDomainObjects() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);
        
        when(actorService.getFirstTenActors()).thenReturn(List.of(actor));

        // When
        List<ActorDTO> result = actorController.getFirstTenActors();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        ActorDTO dto = result.get(0);
        assertThat(dto.getId()).isEqualTo(actor.getId());
        assertThat(dto.getFirstName()).isEqualTo(actor.getFirstName());
        assertThat(dto.getLastName()).isEqualTo(actor.getLastName());
        assertThat(dto.getLastUpdate()).isEqualTo(actor.getLastUpdate());
    }
} 