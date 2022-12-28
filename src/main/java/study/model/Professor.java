package study.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="PROFESSORES")
public class Professor{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "professor_id")
    private Integer id;

    @Size(min=2, message="Tamanho do nome não pode ser menor que 2")
    @NotBlank (message = "Nome não pode ser nulo")
    @Column(name = "professor_name", nullable = false)
    private String name;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "titular")
    private Disciplina disciplina;
}
