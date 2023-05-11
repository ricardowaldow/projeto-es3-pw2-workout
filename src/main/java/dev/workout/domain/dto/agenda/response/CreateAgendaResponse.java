package dev.workout.domain.dto.agenda.response;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class CreateAgendaResponse {
    private String hash;
    private int ordem;
}
