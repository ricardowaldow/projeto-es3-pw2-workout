package dev.workout.data.repository;

import java.util.List;

import dev.workout.domain.models.WorkoutEntity;
import io.smallrye.mutiny.Uni;

public interface WorkoutRepository {
    Uni<WorkoutEntity> persist(WorkoutEntity workout);
    Uni<WorkoutEntity> findByHash(String hash);
    Uni<List<WorkoutEntity>> listUserWorkouts(String hash);
}
