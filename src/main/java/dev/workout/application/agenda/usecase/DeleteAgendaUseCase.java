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

    public Uni<Void> execute(final String hash, final String userHash) {
        return agendaRepository.findByHash(hash)
        .onItem().ifNotNull()
        .transformToUni(agenda -> {
            if (agenda.getWorkout().getUserHash() != userHash) {
                throw new IllegalArgumentException("Proibido");
            }
            return agenda.delete();
        });
    }
}
