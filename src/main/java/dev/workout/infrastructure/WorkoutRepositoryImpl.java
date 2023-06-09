package dev.workout.infrastructure;

import java.util.List;

import dev.workout.data.repository.WorkoutRepository;
import dev.workout.domain.models.WorkoutEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class WorkoutRepositoryImpl implements WorkoutRepository, PanacheRepository<WorkoutEntity> {

    @Override
    public Uni<WorkoutEntity> persist(WorkoutEntity workout) {
        return persistAndFlush(workout);
    }

    @Override
    public Uni<WorkoutEntity> findByHash(String hash) {
        return find("hash", hash).firstResult()
        .onItem().ifNull().failWith(new IllegalArgumentException("Workout não encontrado"));
    }

    @Override
    public Uni<List<WorkoutEntity>> listUserWorkouts(String hash) {
       return find("userHash", hash).list();
    }

    @Override
    public Uni<Long> deleteWorkout(WorkoutEntity workout) {
        return delete("hash", workout.getHash());
    }

}
