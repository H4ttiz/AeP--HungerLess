package br.com.aep.hungerless.enderecos.entities;

import br.com.aep.hungerless.usuarios.entities.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "enderecos")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String logradouro;

    private String numero;

    private String bairro;

    private String cidade;

    private String estado;

    private String cep;

    private Double latitude;

    private Double longitude;

    @OneToOne(mappedBy = "endereco")
    private Usuario usuario;
}
