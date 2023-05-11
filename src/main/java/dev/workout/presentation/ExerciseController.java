package dev.workout.presentation;

import dev.workout.application.CreateExerciseRequest;
import dev.workout.application.CreateExerciseUseCase;
import dev.workout.exceptions.ServiceException;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.inject.Inject;

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseController {

    private final CreateExerciseUseCase createExerciseUseCase;

    @Inject
    public ExerciseController(CreateExerciseUseCase createExerciseUseCase) {
        this.createExerciseUseCase = createExerciseUseCase;
    }

    @POST
    @Path("exercise/create")
    @WithSession
    public Uni<Response> createExercise(CreateExerciseRequest request) {
        try {
            return createExerciseUseCase.execute(request)
                    .map(response -> Response.status(Status.CREATED).entity(response).build())
                    .log()
                    .onFailure().transform(e -> {
                        String message = e.getMessage();
                        throw new ServiceException(
                                message,
                                Response.Status.BAD_REQUEST);
                    });
        } catch (Exception e) {
            String message = e.getMessage();
            throw new ServiceException(
                    message,
                    Response.Status.BAD_REQUEST);
        }
    }

}
