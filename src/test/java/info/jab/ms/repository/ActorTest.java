package info.jab.ms.repository;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class ActorTest {

    @Test
    public void testActorConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);
        
        assertEquals(1L, actor.id);
        assertEquals("John", actor.firstName);
        assertEquals("Doe", actor.lastName);
        assertEquals(now, actor.lastUpdate);
    }

    @Test
    public void testActorDefaultConstructor() {
        Actor actor = new Actor();
        
        assertNull(actor.id);
        assertNull(actor.firstName);
        assertNull(actor.lastName);
        assertNull(actor.lastUpdate);
    }

    @Test
    public void testActorThreeParamConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor("Jane", "Smith", now);
        
        assertNull(actor.id);
        assertEquals("Jane", actor.firstName);
        assertEquals("Smith", actor.lastName);
        assertEquals(now, actor.lastUpdate);
    }

    @Test
    public void testEqualsAndHashCode() {
        LocalDateTime now = LocalDateTime.now();
        Actor actor1 = new Actor(1L, "John", "Doe", now);
        Actor actor2 = new Actor(1L, "John", "Doe", now);
        Actor actor3 = new Actor(2L, "Jane", "Smith", now);
        
        assertEquals(actor1, actor2);
        assertNotEquals(actor1, actor3);
        assertEquals(actor1.hashCode(), actor2.hashCode());
        assertNotEquals(actor1.hashCode(), actor3.hashCode());
    }

    @Test
    public void testToString() {
        LocalDateTime now = LocalDateTime.now();
        Actor actor = new Actor(1L, "John", "Doe", now);
        String toString = actor.toString();
        
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("firstName='John'"));
        assertTrue(toString.contains("lastName='Doe'"));
        assertTrue(toString.contains("lastUpdate=" + now));
    }
} 