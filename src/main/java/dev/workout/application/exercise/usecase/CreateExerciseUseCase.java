package dev.workout.application.exercise.usecase;

import dev.workout.data.repository.ExerciseRepository;
import dev.workout.domain.dto.exercise.request.CreateExerciseRequest;
import dev.workout.domain.dto.exercise.response.CreateExerciseResponse;
import dev.workout.domain.models.ExerciseEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CreateExerciseUseCase {
    private final ExerciseRepository exerciseRepository;

    public CreateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Uni<CreateExerciseResponse> execute(CreateExerciseRequest request) {
        ExerciseEntity exercise = new ExerciseEntity();
        exercise.setNome(request.getNome());
        exercise.setRepeticoes(request.getRepeticoes());
        exercise.setSeries(request.getSeries());

        return exerciseRepository.persist(exercise)
        .map(v -> {
            CreateExerciseResponse response = new CreateExerciseResponse();
            response.setHash(exercise.getHash());
            response.setNome(exercise.getNome());
            response.setRepeticoes(exercise.getRepeticoes());
            response.setSeries(exercise.getSeries());
            return response;
        });
    }
}
