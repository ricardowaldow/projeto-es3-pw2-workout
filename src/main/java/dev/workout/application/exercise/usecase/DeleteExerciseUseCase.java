package dev.workout.application.exercise.usecase;

import dev.workout.data.repository.ExerciseRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DeleteExerciseUseCase {
    private final ExerciseRepository exerciseRepository;

    public DeleteExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Uni<Object> execute(final String hash, final String userHash) {
        return exerciseRepository.findByHash(hash)
        .onItem().ifNotNull()
        .transformToUni(exercise -> {
            if (!userHash.equals(exercise.getAgenda().getWorkout().getUserHash())) {
                throw new IllegalArgumentException("Proibido");
            }
            return exerciseRepository.deleteExercise(exercise);
        });
    }
}
