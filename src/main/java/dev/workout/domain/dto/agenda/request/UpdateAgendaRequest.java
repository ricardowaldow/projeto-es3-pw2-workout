package dev.workout.domain.dto.agenda.request;

import lombok.Getter;
import lombok.Setter;

@Getter@Setter
public class UpdateAgendaRequest {
    private String hash;
    private int ordem;
}
