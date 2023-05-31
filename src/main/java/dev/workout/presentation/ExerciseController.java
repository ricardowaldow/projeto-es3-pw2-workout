package dev.workout.presentation;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.workout.application.exercise.usecase.CreateExerciseUseCase;
import dev.workout.application.exercise.usecase.DeleteExerciseUseCase;
import dev.workout.application.exercise.usecase.UpdateExerciseUseCase;
import dev.workout.domain.dto.exercise.request.CreateExerciseRequest;
import dev.workout.domain.dto.exercise.request.UpdateExerciseRequest;
import dev.workout.exceptions.ServiceException;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;
import jakarta.inject.Inject;

@Path("/api/exercise")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ExerciseController {

    private final CreateExerciseUseCase createExerciseUseCase;
    private final UpdateExerciseUseCase updateExerciseUseCase;
    private final DeleteExerciseUseCase deleteExerciseUseCase;

    @Inject
    JsonWebToken jwt;

    @Inject
    public ExerciseController(
        CreateExerciseUseCase createExerciseUseCase,
        UpdateExerciseUseCase updateExerciseUseCase,
        DeleteExerciseUseCase deleteExerciseUseCase
    ) {
        this.createExerciseUseCase = createExerciseUseCase;
        this.updateExerciseUseCase = updateExerciseUseCase;
        this.deleteExerciseUseCase = deleteExerciseUseCase;
    }

    @POST
    @Path("/create")
    @WithSession
    public Uni<Response> createExercise(CreateExerciseRequest request) {
        try {
            String userHash = jwt.getClaim("c_hash");
            return createExerciseUseCase.execute(request, userHash)
                    .map(response -> Response.status(Status.OK).entity(response).build())
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

    @POST
    @Path("/update")
    @WithSession
    public Uni<Response> updateExercise(UpdateExerciseRequest request) {
        try {
            String userHash = jwt.getClaim("c_hash");
            return updateExerciseUseCase.execute(request, userHash)
                    .map(response -> Response.status(Status.OK).entity(response).build())
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

    @DELETE
    @Path("/delete/{hash}")
    @WithSession
    public Uni<Response> deleteExercise(@PathParam("hash") final String hash) {
        try {
            String userHash = jwt.getClaim("c_hash");
            return deleteExerciseUseCase.execute(hash, userHash)
                    .map(response -> Response.status(Status.OK).entity(response).build())
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
