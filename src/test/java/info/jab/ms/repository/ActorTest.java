package info.jab.ms.repository;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActorTest {

    private final LocalDateTime timestamp = LocalDateTime.of(2024, 7, 28, 12, 0);
    
    @Test
    void testParameterizedConstructor() {
        Actor actor = new Actor(1L, "John", "Doe", timestamp);
        
        assertThat(actor.getId()).isEqualTo(1L);
        assertThat(actor.getFirstName()).isEqualTo("John");
        assertThat(actor.getLastName()).isEqualTo("Doe");
        assertThat(actor.getLastUpdate()).isEqualTo(timestamp);
    }
    
    @Test
    void testNoArgsConstructor() {
        Actor actor = new Actor();
        
        assertThat(actor.getId()).isNull();
        assertThat(actor.getFirstName()).isNull();
        assertThat(actor.getLastName()).isNull();
        assertThat(actor.getLastUpdate()).isNull();
    }
    
    @Test
    void testSetters() {
        Actor actor = new Actor();
        
        actor.setId(2L);
        actor.setFirstName("Jane");
        actor.setLastName("Smith");
        actor.setLastUpdate(timestamp);
        
        assertThat(actor.getId()).isEqualTo(2L);
        assertThat(actor.getFirstName()).isEqualTo("Jane");
        assertThat(actor.getLastName()).isEqualTo("Smith");
        assertThat(actor.getLastUpdate()).isEqualTo(timestamp);
    }
    
    @Test
    void testEquals() {
        Actor actor1 = new Actor(1L, "John", "Doe", timestamp);
        Actor actor2 = new Actor(1L, "John", "Doe", timestamp);
        Actor actor3 = new Actor(2L, "Jane", "Smith", timestamp);
        
        assertThat(actor1).isEqualTo(actor2);
        assertThat(actor1).isNotEqualTo(actor3);
        assertThat(actor1).isNotEqualTo(null);
        assertThat(actor1).isNotEqualTo(new Object());
        assertThat(actor1).isEqualTo(actor1); // reflexive
    }
    
    @Test
    void testHashCode() {
        Actor actor1 = new Actor(1L, "John", "Doe", timestamp);
        Actor actor2 = new Actor(1L, "John", "Doe", timestamp);
        
        assertThat(actor1.hashCode()).isEqualTo(actor2.hashCode());
    }
    
    @Test
    void testToString() {
        Actor actor = new Actor(1L, "John", "Doe", timestamp);
        String toString = actor.toString();
        
        assertThat(toString).contains("id=1");
        assertThat(toString).contains("firstName='John'");
        assertThat(toString).contains("lastName='Doe'");
        assertThat(toString).contains("lastUpdate=" + timestamp);
    }
} 