package info.jab.ms.domain.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActorTest {

    @Test
    void shouldCreateActorWithConstructor() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime lastUpdate = LocalDateTime.now();

        // When
        Actor actor = new Actor(id, firstName, lastName, lastUpdate);

        // Then
        assertThat(actor.id()).isEqualTo(id);
        assertThat(actor.firstName()).isEqualTo(firstName);
        assertThat(actor.lastName()).isEqualTo(lastName);
        assertThat(actor.lastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldImplementEqualsAndHashCodeAutomatically() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor1 = new Actor(1L, "John", "Doe", now);
        Actor actor2 = new Actor(1L, "John", "Doe", now);
        Actor actor3 = new Actor(2L, "Jane", "Doe", now);
        
        // Then
        // Equal objects
        assertThat(actor1).isEqualTo(actor2);
        assertThat(actor1.hashCode()).isEqualTo(actor2.hashCode());
        
        // Different objects
        assertThat(actor1).isNotEqualTo(actor3);
        assertThat(actor1.hashCode()).isNotEqualTo(actor3.hashCode());
    }

    @Test
    void shouldImplementToStringAutomatically() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);

        // When
        String result = actor.toString();

        // Then
        assertThat(result).contains("id=1");
        assertThat(result).contains("firstName=John");
        assertThat(result).contains("lastName=Doe");
        assertThat(result).contains("lastUpdate=" + now);
    }
} 