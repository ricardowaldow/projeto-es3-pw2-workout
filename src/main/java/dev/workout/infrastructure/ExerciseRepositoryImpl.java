package dev.workout.infrastructure;

import dev.workout.data.repository.ExerciseRepository;
import dev.workout.domain.models.ExerciseEntity;
import io.quarkus.hibernate.reactive.panache.PanacheRepository;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ExerciseRepositoryImpl implements ExerciseRepository, PanacheRepository<ExerciseEntity> {

    @Override
    public Uni<ExerciseEntity> persist(ExerciseEntity exercise) {
        return persistAndFlush(exercise);
    }

    @Override
    public Uni<ExerciseEntity> findByHash(String hash) {
        return find("hash", hash).firstResult();
    }

    @Override
    public Uni<Long> deleteExercise(ExerciseEntity exercise) {
        return delete("hash", exercise.getHash());
    }

}
