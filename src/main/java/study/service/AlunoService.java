package study.service;

import javax.enterprise.context.ApplicationScoped;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Objects;

import study.exception.NotAllowedNameException;
import javax.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import javax.transaction.Transactional;
import java.util.Optional;
import javax.validation.Valid;

import study.model.Aluno;
import study.repository.AlunoRepository;
import study.repository.ProfessorRepository;
import study.dto.AlunoRequest;
import study.dto.AlunoResponse;
import study.dto.TutorResponse;
import study.mapper.AlunoMapper;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
@ApplicationScoped
@Slf4j
@RequiredArgsConstructor
public class AlunoService {
    
    private final AlunoMapper mapper;
    private final AlunoRepository repository;
    private final ProfessorRepository professorRepository;

    private static final Logger log = LoggerFactory.getLogger(AlunoService.class);

    public List<AlunoResponse> retornaTodos(){
        log.info("Listando alunos...");
        final List<Aluno> listaDeAlunos = repository.listAll();
        return mapper.toResponse(listaDeAlunos);
    }

    public AlunoResponse buscaAlunoPorId(int id){
        log.info("Buscando aluno id--{}", id);

        Aluno aluno = repository.findById(id);
        if (Objects.isNull(aluno)) throw new EntityNotFoundException("Aluno não encontrado");
        return mapper.toResponse(aluno);
    }

    //colocar anotação @Valid nas classes q implementam as validações
    @Transactional
    public AlunoResponse salvar(@Valid AlunoRequest alunoRequest){

        Objects.requireNonNull(alunoRequest, "Requisição não pode ser nula");

        log.info("Salvando aluno - {}", alunoRequest);

        if (alunoRequest.getName().equals("TESTE")) {
            throw new NotAllowedNameException("O nome TESTE não é permitido");
        }

        Aluno entity = 
                Aluno.builder()
                .name(alunoRequest.getName())
                .build();

        repository.persist(entity);

        return mapper.toResponse(entity);

    }

    @Transactional
    public AlunoResponse atualiza(int id,@Valid AlunoRequest alunoRequest) {

        Objects.requireNonNull(alunoRequest, "Requisição não pode ser nula");

        log.info("Atualizando aluno id - {}, data - {}", id, alunoRequest);

        Optional<Aluno> aluno = repository.findByIdOptional(id);

        aluno.orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));

        if (aluno.isPresent()) {
            var entity = aluno.get();
            entity.setName(alunoRequest.getName());
            repository.persistAndFlush(entity);
            return mapper.toResponse(entity);
        }

        return new AlunoResponse();
    }

    @Transactional
    public void removeAluno(int id) {
        log.info("Deletando aluno id - {}", id);
        repository.findByIdOptional(id).ifPresent(repository::delete);
    }

    @Transactional
    public TutorResponse atualizaTutor(int idAluno, int idProfessor) {

        log.info("Atualizando tutor aluno-id: {}, professor-id: {}", idAluno, idProfessor);

        //encontrando entidades
        var aluno = repository.findById(idAluno);
        var professor = professorRepository.findById(idProfessor);

        //validando se não é nulo
        if (Objects.isNull(aluno)) throw new EntityNotFoundException("Aluno não encontrado");
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        //Atualiza
        aluno.setTutor(professor);
        repository.persist(aluno);

        return mapper.toResponse(professor);
    }

    public List<AlunoResponse> buscaTutoradosPorProfessorId(int idProfessor) {

        log.info("Busca tutorados por professor-id: {}", idProfessor);

        var professor = professorRepository.findById(idProfessor);
        if (Objects.isNull(professor)) throw new EntityNotFoundException("Professor não encontrado");

        List<Aluno> listaDeTutorados = repository.buscaTutoradosPorProfessor(professor);

        return mapper.toResponse(listaDeTutorados);
    }

}
