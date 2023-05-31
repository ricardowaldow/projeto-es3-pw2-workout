package dev.workout.domain.dto.exercise.request;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateExerciseRequest {
    private String hash;
    private String nome;
    private int series;
    private int repeticoes;

}
