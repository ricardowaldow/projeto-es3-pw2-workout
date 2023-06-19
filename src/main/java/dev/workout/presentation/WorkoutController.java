package dev.workout.presentation;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.workout.application.workout.usecase.CreateWorkoutUseCase;
import dev.workout.application.workout.usecase.DeleteWorkoutUseCase;
import dev.workout.application.workout.usecase.GetWorkoutUseCase;
import dev.workout.application.workout.usecase.ListUserWorkoutsUseCase;
import dev.workout.domain.dto.workout.request.CreateWorkoutRequest;
import dev.workout.exceptions.ServiceException;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/api/workout")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@DeclareRoles("user")
@RolesAllowed("user")
public class WorkoutController {
    private final CreateWorkoutUseCase createWorkoutUseCase;
    private final ListUserWorkoutsUseCase listUserWorkoutsUseCase;
    private final GetWorkoutUseCase getWorkoutUseCase;
    private final DeleteWorkoutUseCase deleteWorkoutUseCase;

    @Inject
    JsonWebToken jwt;

    @Inject
    public WorkoutController(
        CreateWorkoutUseCase createWorkoutUseCase,
        ListUserWorkoutsUseCase listUserWorkoutsUseCase,
        GetWorkoutUseCase getWorkoutUseCase,
        DeleteWorkoutUseCase deleteWorkoutUseCase
    ) {
        this.createWorkoutUseCase = createWorkoutUseCase;
        this.listUserWorkoutsUseCase = listUserWorkoutsUseCase;
        this.getWorkoutUseCase = getWorkoutUseCase;
        this.deleteWorkoutUseCase = deleteWorkoutUseCase;
    }

    @GET
    @Path("/{hash}")
    @WithSession
    public Uni<Response> getWorkout(@PathParam("hash") final String hash) {
        try {
            return getWorkoutUseCase.execute(hash)
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

    @GET
    @Path("/user/list")
    @WithSession
    public Uni<Response> listUserWorkouts() {
        try {
            String userHash = jwt.getClaim("c_hash");
            return listUserWorkoutsUseCase.execute(userHash)
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
    @Path("/create")
    @WithSession
    public Uni<Response> createWorkout(CreateWorkoutRequest request) {
        try {
            String userHash = jwt.getClaim("c_hash");
            return createWorkoutUseCase.execute(request, userHash)
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
    public Uni<Response> deleteWorkout(@PathParam("hash") final String hash) {
        try {
            String userHash = jwt.getClaim("c_hash");
            return deleteWorkoutUseCase.execute(hash, userHash)
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
