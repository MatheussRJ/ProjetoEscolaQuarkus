package study.mapper;

import study.dto.ProfessorRequest;
import study.dto.ProfessorResponse;
import study.model.Professor;


import java.util.ArrayList;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
@ApplicationScoped
public class ProfessorMapper {

    public List<ProfessorResponse> toResponse(List<Professor> listaDeProfessores) {
        if (Objects.isNull(listaDeProfessores))
            return new ArrayList<>();

        return listaDeProfessores.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public Professor toEntity(ProfessorRequest request) {
        if (Objects.isNull(request)) {
            return null;
        } else {
            return Professor.builder()
                    .name(request.getName())
                    .build();
        }
    }

    public ProfessorResponse toResponse(Professor entity) {

        //novo tipo de validação
        Objects.requireNonNull(entity, "Entidade não podde ser nula");

        return ProfessorResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }

}
