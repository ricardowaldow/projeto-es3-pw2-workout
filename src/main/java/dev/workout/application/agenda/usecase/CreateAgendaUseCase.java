package dev.workout.application.agenda.usecase;

import dev.workout.data.repository.AgendaRepository;
import dev.workout.domain.dto.agenda.request.CreateAgendaRequest;
import dev.workout.domain.dto.agenda.response.CreateAgendaResponse;
import dev.workout.domain.models.AgendaEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateAgendaUseCase {
    private final AgendaRepository agendaRepository;

    public CreateAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Uni<CreateAgendaResponse> execute(CreateAgendaRequest request) {
        AgendaEntity agenda = new AgendaEntity();
        agenda.setOrdem(request.getOrdem());

        return agendaRepository.persist(agenda)
        .map(v -> {
            CreateAgendaResponse response = new CreateAgendaResponse();
            response.setHash(agenda.getHash());
            response.setOrdem(agenda.getOrdem());
            return response;
        });
    }
}
