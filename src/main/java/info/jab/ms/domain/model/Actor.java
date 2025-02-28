package info.jab.ms.domain.model;

import java.time.LocalDateTime;

/**
 * Actor domain entity representing a film actor.
 * Implemented as a record for immutability and automatic generation of equals, hashCode, and toString.
 */
public record Actor(Long id, String firstName, String lastName, LocalDateTime lastUpdate) { } 