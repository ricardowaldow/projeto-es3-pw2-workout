package dev.workout.application.workout.usecase;

import dev.workout.data.repository.WorkoutRepository;
import dev.workout.domain.dto.workout.response.GetWorkoutResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class GetWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public GetWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Uni<GetWorkoutResponse> execute(final String hash) {
        return workoutRepository.findByHash(hash)
        .map(v -> {
            GetWorkoutResponse response = new GetWorkoutResponse();
            response.setHash(v.getHash());
            response.setNome(v.getNome());
            response.setAgendas(v.getAgendas());
            return response;
        });
    }


}
