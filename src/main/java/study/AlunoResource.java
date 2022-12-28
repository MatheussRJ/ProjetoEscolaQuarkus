package study;

import lombok.RequiredArgsConstructor;
import study.dto.AlunoRequest;
import study.dto.ErrorResponse;
import study.service.AlunoService;

import javax.validation.ConstraintViolationException;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/alunos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RequiredArgsConstructor
public class AlunoResource {

    private final AlunoService service;

    @GET
    public Response listaAlunos() {
        final var response = service.retornaTodos();

        return Response.ok(response).build();
    }

    @POST
    public Response salvarAluno(final AlunoRequest alunoRequest) {
        try {
            final var response = service.salvar(alunoRequest);

            return Response
                    .status(Response.Status.CREATED)
                    .entity(response)
                    .build();

        } catch(ConstraintViolationException e) {
            return Response
                    .status(Response.Status.BAD_REQUEST)
                    .entity(ErrorResponse.createFromValidation(e))
                    .build();
        }
    }

    @GET
    @Path("/{id}")
    public Response buscaAlunoPorId(@PathParam("id") int id) {

        final var response = service.buscaAlunoPorId(id);

        return Response.ok(response).build();
    }

    @PATCH
    @Path("/{id}/tutor/{idProfessor}")
    public Response atualizaTutor(@PathParam("id") int idAluno, @PathParam("idProfessor") int idProfessor) {
        final var response = service.atualizaTutor(idAluno, idProfessor);

        return Response
                .status(Response.Status.CREATED)
                .entity(response)
                .build();
    }
}
