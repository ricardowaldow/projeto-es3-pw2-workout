package dev.workout.application.agenda.usecase;

import dev.workout.data.repository.AgendaRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteAgendaUseCase {
    private final AgendaRepository agendaRepository;

    public DeleteAgendaUseCase(AgendaRepository agendaRepository) {
        this.agendaRepository = agendaRepository;
    }

    public Uni<Object> execute(final String hash, final String userHash) {
        return agendaRepository.findByHash(hash)
        .onItem().ifNotNull()
        .transformToUni(agenda -> {
            if (!userHash.equals(agenda.getWorkout().getUserHash())) {
                throw new IllegalArgumentException("Proibido");
            }
            return agendaRepository.deleteAgenda(agenda);
        });
    }
}
