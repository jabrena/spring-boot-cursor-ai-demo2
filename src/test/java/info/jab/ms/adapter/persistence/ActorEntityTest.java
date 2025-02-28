package info.jab.ms.adapter.persistence;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActorEntityTest {

    @Test
    void shouldCreateEntityWithConstructor() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime lastUpdate = LocalDateTime.now();

        // When
        ActorEntity entity = new ActorEntity(id, firstName, lastName, lastUpdate);

        // Then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getFirstName()).isEqualTo(firstName);
        assertThat(entity.getLastName()).isEqualTo(lastName);
        assertThat(entity.getLastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldCreateEntityWithDefaultConstructorAndSetters() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime lastUpdate = LocalDateTime.now();

        // When
        ActorEntity entity = new ActorEntity();
        entity.setId(id);
        entity.setFirstName(firstName);
        entity.setLastName(lastName);
        entity.setLastUpdate(lastUpdate);

        // Then
        assertThat(entity.getId()).isEqualTo(id);
        assertThat(entity.getFirstName()).isEqualTo(firstName);
        assertThat(entity.getLastName()).isEqualTo(lastName);
        assertThat(entity.getLastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldImplementEqualsAndHashCodeComprehensively() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ActorEntity entity1 = new ActorEntity(1L, "John", "Doe", now);
        ActorEntity entity2 = new ActorEntity(1L, "John", "Doe", now);
        ActorEntity entity3 = new ActorEntity(2L, "Jane", "Doe", now);
        ActorEntity entityWithDifferentFirstName = new ActorEntity(1L, "Jane", "Doe", now);
        ActorEntity entityWithDifferentLastName = new ActorEntity(1L, "John", "Smith", now);
        ActorEntity entityWithDifferentLastUpdate = new ActorEntity(1L, "John", "Doe", now.plusDays(1));
        ActorEntity entityWithNullId = new ActorEntity(null, "John", "Doe", now);
        ActorEntity entityWithNullFirstName = new ActorEntity(1L, null, "Doe", now);
        ActorEntity entityWithNullLastName = new ActorEntity(1L, "John", null, now);
        ActorEntity entityWithNullLastUpdate = new ActorEntity(1L, "John", "Doe", null);
        Object differentObject = new Object();

        // Then
        // Same identity
        assertThat(entity1.equals(entity1)).isTrue();
        
        // Null check
        assertThat(entity1.equals(null)).isFalse();
        
        // Different class check
        assertThat(entity1.equals(differentObject)).isFalse();
        
        // Equal objects
        assertThat(entity1.equals(entity2)).isTrue();
        
        // Different ID
        assertThat(entity1.equals(entity3)).isFalse();
        
        // Different firstName
        assertThat(entity1.equals(entityWithDifferentFirstName)).isFalse();
        
        // Different lastName
        assertThat(entity1.equals(entityWithDifferentLastName)).isFalse();
        
        // Different lastUpdate
        assertThat(entity1.equals(entityWithDifferentLastUpdate)).isFalse();
        
        // Test with nulls
        assertThat(entityWithNullId.equals(entity1)).isFalse();
        assertThat(entity1.equals(entityWithNullId)).isFalse();
        
        assertThat(entityWithNullFirstName.equals(entity1)).isFalse();
        assertThat(entity1.equals(entityWithNullFirstName)).isFalse();
        
        assertThat(entityWithNullLastName.equals(entity1)).isFalse();
        assertThat(entity1.equals(entityWithNullLastName)).isFalse();
        
        assertThat(entityWithNullLastUpdate.equals(entity1)).isFalse();
        assertThat(entity1.equals(entityWithNullLastUpdate)).isFalse();
        
        // Same with nulls
        ActorEntity bothWithNullId = new ActorEntity(null, "John", "Doe", now);
        assertThat(entityWithNullId.equals(bothWithNullId)).isTrue();
        
        // Consistent hashCode
        assertThat(entity1.hashCode()).isEqualTo(entity2.hashCode());
        assertThat(entity1.hashCode()).isNotEqualTo(entity3.hashCode());
    }

    @Test
    void shouldImplementToString() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ActorEntity entity = new ActorEntity(1L, "John", "Doe", now);

        // When
        String result = entity.toString();

        // Then
        assertThat(result).contains("id=1");
        assertThat(result).contains("firstName='John'");
        assertThat(result).contains("lastName='Doe'");
        assertThat(result).contains("lastUpdate=" + now);
    }
} 