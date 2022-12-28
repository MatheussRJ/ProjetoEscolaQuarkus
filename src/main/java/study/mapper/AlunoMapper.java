package study.mapper;

import study.dto.AlunoRequest;
import study.dto.AlunoResponse;
import study.dto.TutorResponse;
import study.model.Aluno;
import study.model.Professor;

import javax.enterprise.context.ApplicationScoped;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class AlunoMapper {
    
    public List<AlunoResponse> toResponse(List<Aluno> listaDeAlunos) {
        if (Objects.isNull(listaDeAlunos))
            return new ArrayList<>();

        return listaDeAlunos.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    public Aluno toEntity(AlunoRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Aluno.builder()
                    .name(request.getName())
                    .build();
        }
    }

    public AlunoResponse toResponse(Aluno entity) {

        //novo tipo de validação
        Objects.requireNonNull(entity, "Entidade não podde ser nula");

        var response = AlunoResponse.builder()
        .id(entity.getId())
        .name(entity.getName())
        .build();

        if (Objects.nonNull(entity.getTutor())) {
            response.setTutor(entity.getTutor().getName());
        }
        
        return response;
    }

    public TutorResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "entity must not be null");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TutorResponse.builder()
                .tutor(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();

    }

}
