package br.com.aep.hungerless.categorias.entities;

import br.com.aep.hungerless.alimentos.entities.Alimento;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Entity
@Table(name = "categorias")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Categoria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;

    private String descricao;

    @OneToMany(mappedBy = "categoria")
    private List<Alimento> alimentos;
}
