package dev.workout.data.repository;

import dev.workout.domain.models.ExerciseEntity;
import io.smallrye.mutiny.Uni;

public interface ExerciseRepository {
    Uni<ExerciseEntity> persist(ExerciseEntity exercise);
    Uni<ExerciseEntity> findByHash(String hash);
    Uni<Long> deleteExercise(ExerciseEntity exercise);
}
