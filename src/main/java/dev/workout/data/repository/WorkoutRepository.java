package dev.workout.data.repository;

import dev.workout.domain.models.WorkoutEntity;
import io.smallrye.mutiny.Uni;

public interface WorkoutRepository {
    Uni<WorkoutEntity> persist(WorkoutEntity workout);
    Uni<WorkoutEntity> findByHash(String hash);
}
