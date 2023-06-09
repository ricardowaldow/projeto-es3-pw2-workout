package dev.workout.application.workout.usecase;

import dev.workout.data.repository.WorkoutRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteWorkoutUseCase {
    private final WorkoutRepository workoutRepository;

    public DeleteWorkoutUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Uni<Object> execute(final String hash, final String userHash) {
        return workoutRepository.findByHash(hash)
        .onItem().ifNotNull()
        .transformToUni(workout -> {
            if (!userHash.equals(workout.getUserHash())) {
                throw new IllegalArgumentException("Proibido");
            }
            return workoutRepository.deleteWorkout(workout);
        });
    }


}
