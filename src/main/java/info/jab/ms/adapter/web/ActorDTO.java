package info.jab.ms.adapter.web;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Actor information to be exposed through the API.
 * Implemented as a record to provide an immutable data carrier with built-in
 * equals, hashCode, and toString methods.
 */
public record ActorDTO(Long id, String firstName, String lastName, LocalDateTime lastUpdate) { } 