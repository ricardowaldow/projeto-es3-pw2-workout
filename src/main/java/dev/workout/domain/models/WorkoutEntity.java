package dev.workout.domain.models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/**Workout Entity. */
@Entity
@Getter@Setter
public class WorkoutEntity extends PanacheEntityBase {

    /** Primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /** Entity hash id. */
    private String hash;

    /** Workout name. */
    private String nome;

    /** Workout agendas. */
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "workout")
    private List<AgendaEntity> agendas;

    /** Hash from workout owner. */
    private String userHash;

    /** Workout Constructor */
    public WorkoutEntity() {
        super();
        this.hash = UUID.randomUUID().toString();
        this.agendas = new ArrayList<>();
    }


}
