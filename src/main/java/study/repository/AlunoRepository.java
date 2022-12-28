package study.repository;

import java.util.List;
import java.util.Objects;

import javax.enterprise.context.ApplicationScoped;
import study.model.Aluno;
import study.model.Professor;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer>{
    public List<Aluno> buscaTutoradosPorProfessor(Professor professor) {

        Objects.requireNonNull(professor, "Professor não pode ser nulo");

        var query = find("tutor", Sort.ascending("name"), professor);

        return query.list();
    }
    
}
