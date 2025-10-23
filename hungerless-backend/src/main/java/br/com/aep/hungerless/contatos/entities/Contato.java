package br.com.aep.hungerless.contatos.entities;

import br.com.aep.hungerless.usuarios.entities.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "contatos")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Contato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String telefone;

    private String celular;

    private String emailAlternativo;

    @OneToOne(mappedBy = "contato")
    private Usuario usuario;
}
