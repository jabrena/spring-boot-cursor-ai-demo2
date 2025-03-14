package info.jab.ms.repository;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.List;

/**
 * CREATE TABLE actor (
 *     actor_id integer DEFAULT nextval('actor_actor_id_seq'::regclass) NOT NULL,
 *     first_name character varying(45) NOT NULL,
 *     last_name character varying(45) NOT NULL,
 *     last_update timestamp without time zone DEFAULT now() NOT NULL
 * );
 */
@Entity
@Table(name = "actor")
public class Actor extends PanacheEntityBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    public Long id;

    @Column(name = "first_name")
    public String firstName;
    
    @Column(name = "last_name")
    public String lastName;
    
    @Column(name = "last_update")
    public LocalDateTime lastUpdate;

    // Constructors
    public Actor() {}

    public Actor(String firstName, String lastName, LocalDateTime lastUpdate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
    }

    public Actor(Long id, String firstName, String lastName, LocalDateTime lastUpdate) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.lastUpdate = lastUpdate;
    }

    // Custom finders
    public static List<Actor> findFirstTen() {
        return Actor.findAll().page(0, 10).list();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Actor actor = (Actor) o;
        return Objects.equals(id, actor.id) && 
               Objects.equals(firstName, actor.firstName) && 
               Objects.equals(lastName, actor.lastName) && 
               Objects.equals(lastUpdate, actor.lastUpdate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, lastUpdate);
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
