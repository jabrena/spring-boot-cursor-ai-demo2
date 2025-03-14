package info.jab.ms.service;

import info.jab.ms.dto.ActorDTO;
import info.jab.ms.repository.Actor;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ActorServiceTest {

    @Inject
    ActorService actorService;

    @Test
    public void testServiceNotNull() {
        assertNotNull(actorService);
    }
} 