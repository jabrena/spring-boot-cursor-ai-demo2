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
        assertThat(actor.getId()).isEqualTo(id);
        assertThat(actor.getFirstName()).isEqualTo(firstName);
        assertThat(actor.getLastName()).isEqualTo(lastName);
        assertThat(actor.getLastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldCreateActorWithDefaultConstructorAndSetters() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime lastUpdate = LocalDateTime.now();

        // When
        Actor actor = new Actor();
        actor.setId(id);
        actor.setFirstName(firstName);
        actor.setLastName(lastName);
        actor.setLastUpdate(lastUpdate);

        // Then
        assertThat(actor.getId()).isEqualTo(id);
        assertThat(actor.getFirstName()).isEqualTo(firstName);
        assertThat(actor.getLastName()).isEqualTo(lastName);
        assertThat(actor.getLastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldImplementEqualsAndHashCodeComprehensively() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor1 = new Actor(1L, "John", "Doe", now);
        Actor actor2 = new Actor(1L, "John", "Doe", now);
        Actor actor3 = new Actor(2L, "Jane", "Doe", now);
        Actor actorWithDifferentFirstName = new Actor(1L, "Jane", "Doe", now);
        Actor actorWithDifferentLastName = new Actor(1L, "John", "Smith", now);
        Actor actorWithDifferentLastUpdate = new Actor(1L, "John", "Doe", now.plusDays(1));
        Actor actorWithNullId = new Actor(null, "John", "Doe", now);
        Actor actorWithNullFirstName = new Actor(1L, null, "Doe", now);
        Actor actorWithNullLastName = new Actor(1L, "John", null, now);
        Actor actorWithNullLastUpdate = new Actor(1L, "John", "Doe", null);
        Object differentObject = new Object();

        // Then
        // Same identity
        assertThat(actor1.equals(actor1)).isTrue();
        
        // Null check
        assertThat(actor1.equals(null)).isFalse();
        
        // Different class check
        assertThat(actor1.equals(differentObject)).isFalse();
        
        // Equal objects
        assertThat(actor1.equals(actor2)).isTrue();
        
        // Different ID
        assertThat(actor1.equals(actor3)).isFalse();
        
        // Different firstName
        assertThat(actor1.equals(actorWithDifferentFirstName)).isFalse();
        
        // Different lastName
        assertThat(actor1.equals(actorWithDifferentLastName)).isFalse();
        
        // Different lastUpdate
        assertThat(actor1.equals(actorWithDifferentLastUpdate)).isFalse();
        
        // Test with nulls
        assertThat(actorWithNullId.equals(actor1)).isFalse();
        assertThat(actor1.equals(actorWithNullId)).isFalse();
        
        assertThat(actorWithNullFirstName.equals(actor1)).isFalse();
        assertThat(actor1.equals(actorWithNullFirstName)).isFalse();
        
        assertThat(actorWithNullLastName.equals(actor1)).isFalse();
        assertThat(actor1.equals(actorWithNullLastName)).isFalse();
        
        assertThat(actorWithNullLastUpdate.equals(actor1)).isFalse();
        assertThat(actor1.equals(actorWithNullLastUpdate)).isFalse();
        
        // Same with nulls
        Actor bothWithNullId = new Actor(null, "John", "Doe", now);
        assertThat(actorWithNullId.equals(bothWithNullId)).isTrue();
        
        // Consistent hashCode
        assertThat(actor1.hashCode()).isEqualTo(actor2.hashCode());
        assertThat(actor1.hashCode()).isNotEqualTo(actor3.hashCode());
    }

    @Test
    void shouldImplementToString() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);

        // When
        String result = actor.toString();

        // Then
        assertThat(result).contains("id=1");
        assertThat(result).contains("firstName='John'");
        assertThat(result).contains("lastName='Doe'");
        assertThat(result).contains("lastUpdate=" + now);
    }
} 