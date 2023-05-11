package dev.workout.infrastructure;

import dev.workout.domain.models.ExerciseEntity;
import dev.workout.domain.repository.ExerciseRepository;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExerciseRepositoryImpl implements ExerciseRepository, PanacheRepository<ExerciseEntity> {

    @Override
    public Uni<ExerciseEntity> persist(ExerciseEntity exercise) {
        return persist(exercise);
    }

    @Override
    public Uni<ExerciseEntity> findByHash(String hash) {
        return find("hash", hash).firstResult();
    }

}
