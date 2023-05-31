package dev.workout.domain.models;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

/** Agenda Entity. */
@Entity
@Getter@Setter
public class ExerciseEntity extends PanacheEntityBase {

    /** Primary key. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;

    /** Entity hash id. */
    private String hash;

    /** Exercise name. */
    private String nome;

    /** Exercise series. */
    private int series;

    /** Exercise repetitions. */
    private int repeticoes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "agenda_id")
    @JsonIgnore
    private AgendaEntity agenda;

    /** Exercise constructor. */
    public ExerciseEntity() {
        super();
        this.hash = UUID.randomUUID().toString();
    }

}
