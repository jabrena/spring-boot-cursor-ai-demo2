package info.jab.ms.adapter.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import info.jab.ms.config.TestContainersJdbcConfig;

import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

@DataJdbcTest
class ActorRepositoryIntegrationTest extends TestContainersJdbcConfig {
    
    @Autowired
    private TestSpringActorRepository repository;
    
    @BeforeEach
    void clearDatabase() {
        // Delete all entities before each test
        repository.deleteAll();
    }
    
    @Test
    void shouldFindAllActorsWithLimit() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        ActorEntity actor1 = new ActorEntity(null, "John", "Doe", now);
        ActorEntity actor2 = new ActorEntity(null, "Jane", "Smith", now);
        
        // Save the entities 
        repository.save(actor1);
        repository.save(actor2);
        
        // When
        List<ActorEntity> result = repository.findActorsWithLimit(10);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        
        // Verify the content matches what we expect
        assertThat(result).extracting(ActorEntity::firstName)
                .containsExactlyInAnyOrder("John", "Jane");
        assertThat(result).extracting(ActorEntity::lastName)
                .containsExactlyInAnyOrder("Doe", "Smith");
    }
    
    @Test
    void shouldLimitResultsBasedOnLimitParameter() {
        // Given
        List<ActorEntity> actors = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < 10; i++) {
            ActorEntity actor = new ActorEntity(null, "FirstName" + i, "LastName" + i, now);
            actors.add(actor);
        }
        
        // Save all entities using saveAll method
        repository.saveAll(actors);
        
        // When - requesting just 5 results
        List<ActorEntity> result = repository.findActorsWithLimit(5);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(5);
    }
    
    @Test
    void shouldReturnTop10Actors() {
        // Given
        List<ActorEntity> actors = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        
        for (int i = 0; i < 15; i++) {
            ActorEntity actor = new ActorEntity(null, "FirstName" + i, "LastName" + i, now);
            actors.add(actor);
        }
        
        // Save all entities
        repository.saveAll(actors);
        
        // When - requesting top 10 results
        List<ActorEntity> result = repository.findActorsWithLimit(10);
        
        // Then
        assertThat(result).isNotNull();
        assertThat(result).hasSize(10);
    }
} 