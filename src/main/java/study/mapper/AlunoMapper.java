package study.mapper;

import study.dto.AlunoRequest;
import study.dto.AlunoResponse;
import study.model.Aluno;

import javax.enterprise.context.ApplicationScoped;
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

        return AlunoResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
