package info.jab.ms.dto;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Data Transfer Object for Actor entity.
 * Used to transfer actor data between layers without exposing the entity directly.
 */
public class ActorDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDateTime lastUpdate;

    public ActorDTO() {
    }

    public ActorDTO(Long id, String firstName, String lastName, LocalDateTime lastUpdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDateTime getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDateTime lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ActorDTO actorDTO = (ActorDTO) o;
        return Objects.equals(id, actorDTO.id) &&
                Objects.equals(firstName, actorDTO.firstName) &&
                Objects.equals(lastName, actorDTO.lastName) &&
                Objects.equals(lastUpdate, actorDTO.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, lastUpdate);
    }

    @Override
    public String toString() {
        return "ActorDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
} 