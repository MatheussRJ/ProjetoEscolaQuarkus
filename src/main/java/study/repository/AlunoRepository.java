package study.repository;

import javax.enterprise.context.ApplicationScoped;
import study.model.Aluno;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

@ApplicationScoped
public class AlunoRepository implements PanacheRepositoryBase<Aluno, Integer>{

    
}
