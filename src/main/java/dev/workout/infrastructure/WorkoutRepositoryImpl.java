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
        return persist(workout);
    }

    @Override
    public Uni<WorkoutEntity> findByHash(String hash) {
        return find("hash", hash).firstResult();
    }

    @Override
    public Uni<List<WorkoutEntity>> listUserWorkouts(String hash) {
       return find("userHash", hash).list();
    }
    
}
