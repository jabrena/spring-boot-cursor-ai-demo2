package info.jab.ms.adapter.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * Data Transfer Object for creating a new Actor.
 * Contains validation constraints to ensure data integrity.
 */
public record CreateActorDTO(
        @NotBlank(message = "First name is required")
        @Size(min = 1, max = 50, message = "First name must be between 1 and 50 characters")
        String firstName,
        
        @NotBlank(message = "Last name is required")
        @Size(min = 1, max = 50, message = "Last name must be between 1 and 50 characters")
        String lastName
) { } 