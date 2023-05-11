package dev.workout.application.workout.usecase;

import dev.workout.application.workout.request.CreateWorkoutRequest;
import dev.workout.application.workout.response.CreateWorkoutResponse;
import dev.workout.domain.models.WorkoutEntity;
import dev.workout.domain.repository.WorkoutRepository;
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
