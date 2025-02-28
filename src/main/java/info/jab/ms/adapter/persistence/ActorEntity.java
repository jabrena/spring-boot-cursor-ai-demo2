package info.jab.ms.adapter.persistence;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

/**
 * ORM entity for the actor table.
 */
@Table("actor")
public record ActorEntity(
    @Id
    @Column("actor_id")
    Long id,

    @Column("first_name")
    String firstName,
    
    @Column("last_name")
    String lastName,
    
    @Column("last_update")
    LocalDateTime lastUpdate
) { } 