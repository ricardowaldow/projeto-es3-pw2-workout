package dev.workout.application.agenda.usecase;

import dev.workout.data.repository.AgendaRepository;
import dev.workout.data.repository.WorkoutRepository;
import dev.workout.domain.dto.agenda.request.CreateAgendaRequest;
import dev.workout.domain.dto.agenda.response.CreateAgendaResponse;
import dev.workout.domain.models.AgendaEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateAgendaUseCase {
    private final AgendaRepository agendaRepository;
    private final WorkoutRepository workoutRepository;

    public CreateAgendaUseCase(
        AgendaRepository agendaRepository,
        WorkoutRepository workoutRepository
    ) {
        this.agendaRepository = agendaRepository;
        this.workoutRepository = workoutRepository;
    }

    public Uni<CreateAgendaResponse> execute(CreateAgendaRequest request, final String userHash) {
        return workoutRepository.findByHash(request.getWorkoutHash())
        .onItem().ifNotNull()
        .transformToUni(wk -> {
            if (!userHash.equals(wk.getUserHash())) {
                throw new IllegalArgumentException("Proibido");
            }
            AgendaEntity agenda = new AgendaEntity();
            agenda.setOrdem(request.getOrdem());
            agenda.setWorkout(wk);
            return agendaRepository.persist(agenda)
            .map(v -> {
                CreateAgendaResponse response = new CreateAgendaResponse();
                response.setHash(v.getHash());
                response.setOrdem(v.getOrdem());
                return response;
            });
        });
    }
}
