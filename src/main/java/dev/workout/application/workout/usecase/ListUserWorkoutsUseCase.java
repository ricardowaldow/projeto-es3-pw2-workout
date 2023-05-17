package dev.workout.application.workout.usecase;

import java.util.ArrayList;
import java.util.List;

import dev.workout.data.repository.WorkoutRepository;
import dev.workout.domain.dto.workout.response.ListUserWorkoutsResponse;
import dev.workout.domain.models.WorkoutEntity;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class ListUserWorkoutsUseCase {
    private final WorkoutRepository workoutRepository;

    public ListUserWorkoutsUseCase(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }

    public Uni<List<ListUserWorkoutsResponse>> execute(final String userHash) {
        return workoutRepository.listUserWorkouts(userHash)
        .map(v -> {
            List<ListUserWorkoutsResponse> response = new ArrayList<>();
            for (WorkoutEntity workout:v) {
                ListUserWorkoutsResponse obj = new ListUserWorkoutsResponse();
                obj.setHash(workout.getHash());
                obj.setNome(workout.getNome());
                response.add(obj);
            }
            return response;
        });
    }


}
