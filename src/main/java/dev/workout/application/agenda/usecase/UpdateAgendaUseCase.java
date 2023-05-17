package dev.workout.application.agenda.usecase;

import dev.workout.data.repository.AgendaRepository;
import dev.workout.domain.dto.agenda.request.UpdateAgendaRequest;
import dev.workout.domain.dto.agenda.response.UpdateAgendaResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateAgendaUseCase {
    private final AgendaRepository agendaRepository;
    
    public UpdateAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Uni<UpdateAgendaResponse> execute(final UpdateAgendaRequest request, final String userHash) {
        return agendaRepository.findByHash(request.getHash())
        .onItem().ifNotNull()
        .transformToUni(agenda -> {
            if (agenda.getWorkout().getUserHash() != userHash) {
                throw new IllegalArgumentException("Proibido");
            }
            agenda.setOrdem(request.getOrdem());
            return agendaRepository.persist(agenda)
            .map(v -> {
                UpdateAgendaResponse response = new UpdateAgendaResponse();
                response.setHash(v.getHash());
                response.setOrdem(v.getOrdem());
                return response;
            });
        });
    }
}
