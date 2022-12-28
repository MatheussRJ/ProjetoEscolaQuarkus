package study.service;

import study.dto.DisciplinaRequest;
import study.dto.DisciplinaResponse;
import study.dto.TitularResponse;
import study.exception.InvalidStateException;
import study.mapper.DisciplinaMapper;
import study.model.Disciplina;
import study.repository.DisciplinaRepository;
import study.repository.ProfessorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Objects;

@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class DisciplinaService {
    
    private final DisciplinaMapper mapper;
    private final DisciplinaRepository repository;
    private final ProfessorRepository professorRepository;

    private static final Logger log = LoggerFactory.getLogger(DisciplinaService.class);

    public List<DisciplinaResponse> retornaTodos() {
        log.info("Listando disciplinas...");
        final var listaDeDisciplinas = repository.listAll();
        return  mapper.toResponse(listaDeDisciplinas);
    }

    @Transactional
    public DisciplinaResponse salvar(@Valid DisciplinaRequest disciplinaRequest) {

        Objects.requireNonNull(disciplinaRequest, "Requisição não pode ser nula");

        log.info("Salvando disciplina - {}", disciplinaRequest);

        Disciplina entity =
                Disciplina.builder()
                        .name(disciplinaRequest.getName())
                        .build();

        repository.persist(entity);

        return mapper.toResponse(entity);
    }

    public DisciplinaResponse buscaPorId(int id) {
        log.info("Buscando aluno id-{}", id);

        Disciplina disciplina = repository.findById(id);
        return mapper.toResponse(disciplina);
    }

    @Transactional
    public TitularResponse atualizaTitular(int idDisciplina, int idProfessor) {

        log.info("Atualizando titular disciplina-id: {}, professor-id: {}", idDisciplina, idProfessor);

        //buscando entidades
        var disciplina = repository.findById(idDisciplina);
        var professor = professorRepository.findById(idProfessor);

        //validando se não é nulo
        if (Objects.isNull(disciplina)) throw new EntityNotFoundException("Disciplina não encontrada");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        //verificando se Professor não tem Disciplina
        if (repository.contarTitularidadePorProfessor (professor) > 0) {
            throw new InvalidStateException("Professor deve ser titular de pelo menos uma disciplina");
        }

        //Atualiza
        disciplina.setTitular(professor);
        repository.persist(disciplina);

        return mapper.toResponse(professor);
    }

    public DisciplinaResponse buscaDisciplinaPorProfessorId(int idProfessor) {

        log.info("Busca disciplina por professor-id: {}", idProfessor);

        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        var query = repository.find("titular", professor);
        if (query.count() == 0) throw new EntityNotFoundException("Disciplina não encontrada");
        if (query.count() > 1) throw new InvalidStateException("Professor deve ter pelo menos uma disciplina como titular");

        var disciplina = query.singleResult();

        return mapper.toResponse(disciplina);

    }
}