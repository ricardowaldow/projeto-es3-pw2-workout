package dev.workout.domain.dto.workout.response;

import java.util.List;

import dev.workout.domain.models.AgendaEntity;
import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class GetWorkoutResponse {
    private String hash;
    private String nome;
    private List<AgendaEntity> agendas;
}
