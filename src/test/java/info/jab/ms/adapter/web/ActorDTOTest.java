package info.jab.ms.adapter.web;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class ActorDTOTest {

    @Test
    void shouldCreateDTOWithConstructor() {
        // Given
        Long id = 1L;
        String firstName = "John";
        String lastName = "Doe";
        LocalDateTime lastUpdate = LocalDateTime.now();

        // When
        ActorDTO dto = new ActorDTO(id, firstName, lastName, lastUpdate);

        // Then
        assertThat(dto.id()).isEqualTo(id);
        assertThat(dto.firstName()).isEqualTo(firstName);
        assertThat(dto.lastName()).isEqualTo(lastName);
        assertThat(dto.lastUpdate()).isEqualTo(lastUpdate);
    }

    @Test
    void shouldImplementEqualsAndHashCodeComprehensively() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ActorDTO dto1 = new ActorDTO(1L, "John", "Doe", now);
        ActorDTO dto2 = new ActorDTO(1L, "John", "Doe", now);
        ActorDTO dto3 = new ActorDTO(2L, "Jane", "Doe", now);
        ActorDTO dtoWithDifferentFirstName = new ActorDTO(1L, "Jane", "Doe", now);
        ActorDTO dtoWithDifferentLastName = new ActorDTO(1L, "John", "Smith", now);
        ActorDTO dtoWithDifferentLastUpdate = new ActorDTO(1L, "John", "Doe", now.plusDays(1));
        ActorDTO dtoWithNullId = new ActorDTO(null, "John", "Doe", now);
        ActorDTO dtoWithNullFirstName = new ActorDTO(1L, null, "Doe", now);
        ActorDTO dtoWithNullLastName = new ActorDTO(1L, "John", null, now);
        ActorDTO dtoWithNullLastUpdate = new ActorDTO(1L, "John", "Doe", null);
        Object differentObject = new Object();

        // Then
        // Same identity
        assertThat(dto1.equals(dto1)).isTrue();
        
        // Null check
        assertThat(dto1.equals(null)).isFalse();
        
        // Different class check
        assertThat(dto1.equals(differentObject)).isFalse();
        
        // Equal objects
        assertThat(dto1.equals(dto2)).isTrue();
        
        // Different ID
        assertThat(dto1.equals(dto3)).isFalse();
        
        // Different firstName
        assertThat(dto1.equals(dtoWithDifferentFirstName)).isFalse();
        
        // Different lastName
        assertThat(dto1.equals(dtoWithDifferentLastName)).isFalse();
        
        // Different lastUpdate
        assertThat(dto1.equals(dtoWithDifferentLastUpdate)).isFalse();
        
        // Test with nulls
        assertThat(dtoWithNullId.equals(dto1)).isFalse();
        assertThat(dto1.equals(dtoWithNullId)).isFalse();
        
        assertThat(dtoWithNullFirstName.equals(dto1)).isFalse();
        assertThat(dto1.equals(dtoWithNullFirstName)).isFalse();
        
        assertThat(dtoWithNullLastName.equals(dto1)).isFalse();
        assertThat(dto1.equals(dtoWithNullLastName)).isFalse();
        
        assertThat(dtoWithNullLastUpdate.equals(dto1)).isFalse();
        assertThat(dto1.equals(dtoWithNullLastUpdate)).isFalse();
        
        // Same with nulls
        ActorDTO bothWithNullId = new ActorDTO(null, "John", "Doe", now);
        assertThat(dtoWithNullId.equals(bothWithNullId)).isTrue();
        
        // Consistent hashCode
        assertThat(dto1.hashCode()).isEqualTo(dto2.hashCode());
        assertThat(dto1.hashCode()).isNotEqualTo(dto3.hashCode());
    }

} 