package dev.workout.presentation;

import dev.workout.application.agenda.usecase.CreateAgendaUseCase;
import dev.workout.domain.dto.agenda.request.CreateAgendaRequest;
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

@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AgendaController {
    private final CreateAgendaUseCase createAgendaUseCase;

    public AgendaController(CreateAgendaUseCase createAgendaUseCase) {
        this.createAgendaUseCase = createAgendaUseCase;
    }

    @POST
    @Path("/agenda/create")
    @WithSession
    public Uni<Response> createAgenda(CreateAgendaRequest request) {

        try {
            return createAgendaUseCase.execute(request)
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
