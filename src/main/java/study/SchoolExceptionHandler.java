package study;

import study.dto.ErrorMessage;
import study.exception.NotAllowedNameException;
import study.exception.InvalidStateException;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.EntityNotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Slf4j
@Provider
public class SchoolExceptionHandler implements ExceptionMapper<Exception> {
    
    @Override
    public Response toResponse(Exception exception) {
        log.error("Exception {}", exception.getMessage());
        if (exception instanceof EntityNotFoundException) {
            return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(ErrorMessage.builder()
                            .message(exception.getMessage())
                            .build())
                    .build();
        }
        if (exception instanceof InvalidStateException) {
            return Response
                    .status(Response.Status.CONFLICT)
                    .entity(ErrorMessage.builder()
                            .message(exception.getMessage())
                            .build())
                    .build();
        }

        if (exception instanceof NotAllowedNameException) {
            return Response
                    .status(422)
                    .entity(ErrorMessage.builder()
                            .message(exception.getMessage())
                            .build())
                    .build();
        }


        return Response
                .status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(ErrorMessage.builder()
                        .message("Entre em contato com nosso suporte e informe a mensagem: " + exception.getMessage())
                        .build())
                .build();
    }
}

