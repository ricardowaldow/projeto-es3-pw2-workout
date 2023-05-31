package dev.workout.domain.dto.exercise.response;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateExerciseResponse {
    private String hash;
    private String nome;
    private int series;
    private int repeticoes;    
}
