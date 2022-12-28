package study.mapper;

import study.dto.DisciplinaRequest;
import study.dto.DisciplinaResponse;
import study.dto.TitularResponse;
import study.model.Disciplina;
import study.model.Professor;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@ApplicationScoped
public class DisciplinaMapper {

    public List<DisciplinaResponse> toResponse(List<Disciplina> listaDeDisciplinas) {

        if (Objects.isNull(listaDeDisciplinas)) 
        return new ArrayList<>();

        return listaDeDisciplinas.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

    }


    public Disciplina toEntity(DisciplinaRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Disciplina.builder()
                    .name(request.getName())
                    .build();
        }
    }

    public DisciplinaResponse toResponse(Disciplina entity) {

        Objects.requireNonNull(entity, "Entidade não podde ser nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        var response =
                DisciplinaResponse.builder()
                    .id(entity.getId())
                    .name(entity.getName())
                    .dateTime(formatter.format(entity.getDateTime()))
                    .build();

        if (Objects.nonNull(entity.getTitular())) {
            response.setTitular(entity.getTitular().getName());
        }

        return response;
    }

    public TitularResponse toResponse(Professor entity) {

        Objects.requireNonNull(entity, "Entidade não podde ser nula");

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm:ss");

        return TitularResponse.builder()
                .titular(entity.getName())
                .atualizacao(formatter.format(LocalDateTime.now()))
                .build();

    }
}
