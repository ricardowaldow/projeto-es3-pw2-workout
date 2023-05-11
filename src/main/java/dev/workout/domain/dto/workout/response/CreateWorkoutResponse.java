package dev.workout.domain.dto.workout.response;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CreateWorkoutResponse {
    private String hash;
    private String nome;
}
