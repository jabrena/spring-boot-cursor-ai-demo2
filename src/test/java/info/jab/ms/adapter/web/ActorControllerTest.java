package info.jab.ms.adapter.web;

import info.jab.ms.application.ActorService;
import info.jab.ms.domain.model.Actor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker;
import org.springframework.cloud.client.circuitbreaker.CircuitBreakerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorService actorService;
    
    @Mock
    private CircuitBreakerFactory circuitBreakerFactory;
    
    @Mock
    private CircuitBreaker circuitBreaker;

    @InjectMocks
    private ActorController actorController;

    @Test
    void shouldReturnDTOsFromDomainObjects() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);
        
        when(actorService.getFirstTenActors()).thenReturn(List.of(actor));
        when(circuitBreakerFactory.create(anyString())).thenReturn(circuitBreaker);
        when(circuitBreaker.run(any(Supplier.class), any(Function.class))).thenAnswer(invocation -> {
            Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        // When
        List<ActorDTO> result = actorController.getFirstTenActors();

        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        
        ActorDTO dto = result.get(0);
        assertThat(dto.id()).isEqualTo(actor.id());
        assertThat(dto.firstName()).isEqualTo(actor.firstName());
        assertThat(dto.lastName()).isEqualTo(actor.lastName());
        assertThat(dto.lastUpdate()).isEqualTo(actor.lastUpdate());
    }
    
    @Test
    void shouldCreateActorAndReturnCreatedStatus() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor createdActor = new Actor(1L, "Jane", "Smith", now);
        CreateActorDTO request = new CreateActorDTO("Jane", "Smith");
        
        when(actorService.createActor(anyString(), anyString())).thenReturn(createdActor);
        when(circuitBreakerFactory.create(anyString())).thenReturn(circuitBreaker);
        when(circuitBreaker.run(any(Supplier.class), any(Function.class))).thenAnswer(invocation -> {
            Supplier<?> supplier = invocation.getArgument(0);
            return supplier.get();
        });

        // When
        ResponseEntity<ActorDTO> response = actorController.createActor(request);

        // Then
        assertThat(response).isNotNull();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        
        ActorDTO dto = response.getBody();
        assertThat(dto).isNotNull();
        assertThat(dto.id()).isEqualTo(createdActor.id());
        assertThat(dto.firstName()).isEqualTo(createdActor.firstName());
        assertThat(dto.lastName()).isEqualTo(createdActor.lastName());
        assertThat(dto.lastUpdate()).isEqualTo(createdActor.lastUpdate());
    }
} 