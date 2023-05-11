package dev.workout.application.workout.usecase;

import dev.workout.data.repository.WorkoutRepository;
import dev.workout.domain.dto.workout.request.CreateWorkoutRequest;
import dev.workout.domain.dto.workout.response.CreateWorkoutResponse;
import dev.workout.domain.models.WorkoutEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public CreateWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Uni<CreateWorkoutResponse> execute(CreateWorkoutRequest request) {
        WorkoutEntity workout = new WorkoutEntity();
        workout.setNome(request.getNome());

        return workoutRepository.persist(workout)
        .map(v -> {
            CreateWorkoutResponse response = new CreateWorkoutResponse();
            response.setHash(workout.getHash());
            response.setNome(workout.getNome());
            return response;
        });
    }


}
