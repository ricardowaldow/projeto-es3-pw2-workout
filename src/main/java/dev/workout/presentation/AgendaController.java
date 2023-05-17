package dev.workout.presentation;

import org.eclipse.microprofile.jwt.JsonWebToken;

import dev.workout.application.agenda.usecase.CreateAgendaUseCase;
import dev.workout.application.agenda.usecase.DeleteAgendaUseCase;
import dev.workout.application.agenda.usecase.UpdateAgendaUseCase;
import dev.workout.domain.dto.agenda.request.CreateAgendaRequest;
import dev.workout.domain.dto.agenda.request.UpdateAgendaRequest;
import dev.workout.exceptions.ServiceException;
import io.quarkus.hibernate.reactive.panache.common.WithSession;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/api/agenda")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendaController {
    private final CreateAgendaUseCase createAgendaUseCase;
    private final UpdateAgendaUseCase updateAgendaUseCase;
    private final DeleteAgendaUseCase deleteAgendaUseCase;

    @Inject
    JsonWebToken jwt;

    @Inject
    public AgendaController(
        CreateAgendaUseCase createAgendaUseCase,
        UpdateAgendaUseCase updateAgendaUseCase,
        DeleteAgendaUseCase deleteAgendaUseCase
    ) {
        this.createAgendaUseCase = createAgendaUseCase;
        this.updateAgendaUseCase = updateAgendaUseCase;
        this.deleteAgendaUseCase = deleteAgendaUseCase;
    }

    @POST
    @Path("/create")
    @WithSession
    public Uni<Response> createAgenda(CreateAgendaRequest request) {

        try {
            String userHash = jwt.getClaim("c_hash");
            return createAgendaUseCase.execute(request, userHash)
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

    @POST
    @Path("/update")
    @WithSession
    public Uni<Response> updateAgenda(UpdateAgendaRequest request) {

        try {
            String userHash = jwt.getClaim("c_hash");
            return updateAgendaUseCase.execute(request, userHash)
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

    @DELETE
    @Path("/delete/{hash}")
    @WithSession
    public Uni<Response> deleteAgenda(@PathParam("hash") final String hash) {

        try {
            String userHash = jwt.getClaim("c_hash");
            return deleteAgendaUseCase.execute(hash, userHash)
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
