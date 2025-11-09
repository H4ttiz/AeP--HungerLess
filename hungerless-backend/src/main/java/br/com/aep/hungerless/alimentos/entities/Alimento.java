package br.com.aep.hungerless.alimentos.entities;

import br.com.aep.hungerless.categorias.entities.Categoria;
import br.com.aep.hungerless.doacoes.entities.Doacao;
import br.com.aep.hungerless.alimentos.enums.StatusAlimento;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "alimentos")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String nome;
    private String descricao;
    private LocalDate validade;
    private Integer quantidade;

    @Enumerated(EnumType.STRING)
    private StatusAlimento status = StatusAlimento.DISPONIVEL;

    @ManyToOne
    @JoinColumn(name = "categoria_id")
    private Categoria categoria;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Usuario doador;

    private LocalDateTime dataCadastro = LocalDateTime.now();
    private LocalDateTime dataAtualizacao = LocalDateTime.now();

    @OneToMany(mappedBy = "alimento")
    private List<Doacao> doacoes;

    @PreUpdate
    public void preUpdate() {
        dataAtualizacao = LocalDateTime.now();
    }
}
