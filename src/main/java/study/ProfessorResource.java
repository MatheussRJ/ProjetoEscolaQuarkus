package study;

import javax.annotation.processing.Generated;
import javax.ws.rs.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import lombok.extern.slf4j.Slf4j;

import study.service.ProfessorService;
import study.dto.ProfessorRequest;
import study.mapper.ProfessorMapper;


@Slf4j
@Path("/professor")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ProfessorResource {

    private final ProfessorService service;

    @Inject
    public ProfessorResource(ProfessorService service) {
        this.service = service;
    }

    // trecho para teste simples de endpoint
    /*
     * @GET
     * 
     * @Produces(MediaType.TEXT_PLAIN)
     * public String hello(){
     * log.info("Testando Endpoint...");
     * return "Hello RestEasy!";
     * }
     */

    @GET
    public Response listaProfessores() {

        final var response = service.retornaTodos();

        return Response.ok(response).build();
    }


    
    @GET
    @Path("/{id}")
    public Response buscaProfessor(@PathParam("id") int id) {

        final var response = service.buscaPorId(id);

        return Response.ok(response).build();
    }

    @POST
    public Response salvarProfessor(final ProfessorRequest professor) {

        final var response = service.salvar(professor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }

    @PUT
    @Path("/{id}")
    public Response atualizaProfessor(@PathParam("id") int id, ProfessorRequest professor) {

        var response = service.atualiza(id, professor);

        return Response
                .ok(response)
                .build();
    }

    @DELETE
    @Path("/{id}")
    public Response removeProfessor(@PathParam("id") int id) {

        service.removeProfessor(id);

        return Response
                .status(Response.Status.NO_CONTENT)
                .build();
    }
}