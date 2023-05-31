package dev.workout.domain.models;

import java.util.List;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

/** Agenda Entity. */
@Entity
@Getter@Setter
public class AgendaEntity extends PanacheEntityBase {

    /** Primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /** Entity hash id. */
    private String hash;

    /** Order of agenda. */
    private int ordem;

    /** Workout object. */
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "workout_id")
    @JsonIgnore
    private WorkoutEntity workout;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agenda", fetch = FetchType.EAGER)
    private List<ExerciseEntity> exercises;

    /** Agenda Constructor. */
    public AgendaEntity() {
        super();
        this.hash = UUID.randomUUID().toString();
    }


}
