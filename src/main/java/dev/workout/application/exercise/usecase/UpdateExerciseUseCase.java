package dev.workout.application.exercise.usecase;

import dev.workout.data.repository.ExerciseRepository;
import dev.workout.domain.dto.exercise.request.UpdateExerciseRequest;
import dev.workout.domain.dto.exercise.response.UpdateExerciseResponse;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UpdateExerciseUseCase {
    private final ExerciseRepository exerciseRepository;

    public UpdateExerciseUseCase(ExerciseRepository exerciseRepository) {
        this.exerciseRepository = exerciseRepository;
    }

    public Uni<UpdateExerciseResponse> execute(final UpdateExerciseRequest request, final String userHash) {
        return exerciseRepository.findByHash(request.getHash())
        .onItem().ifNotNull()
        .transformToUni(exercise -> {
            if (!userHash.equals(exercise.getAgenda().getWorkout().getUserHash())) {
                throw new IllegalArgumentException("Proibido");
            }
            exercise.setNome(request.getNome());
            exercise.setSeries(request.getSeries());
            exercise.setRepeticoes(request.getRepeticoes());
            return exerciseRepository.persist(exercise)
            .map(v -> {
                UpdateExerciseResponse response = new UpdateExerciseResponse();
                response.setHash(v.getHash());
                response.setNome(v.getNome());
                response.setSeries(v.getSeries());
                response.setRepeticoes(v.getRepeticoes());
                return response;
            });
        });
    }
}
