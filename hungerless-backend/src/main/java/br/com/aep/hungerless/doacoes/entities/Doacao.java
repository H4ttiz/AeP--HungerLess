package br.com.aep.hungerless.doacoes.entities;

import br.com.aep.hungerless.alimentos.entities.Alimento;
import br.com.aep.hungerless.doacoes.enums.StatusDoacao;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Entity
@Table(name = "doacoes")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Doacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "alimento_id")
    private Alimento alimento;

    @ManyToOne
    @JoinColumn(name = "doador_id")
    private Usuario doador;

    @ManyToOne
    @JoinColumn(name = "receptor_id")
    private Usuario receptor;

    @Enumerated(EnumType.STRING)
    private StatusDoacao status = StatusDoacao.AGUARDANDO;

    private LocalDateTime dataSolicitacao = LocalDateTime.now();

    private LocalDateTime dataAprovacao;

    private LocalDateTime dataEntrega;

    @PreUpdate
    public void preUpdate() {
        if (this.status == StatusDoacao.APROVADA && this.dataAprovacao == null) {
            this.dataAprovacao = LocalDateTime.now();
        }
        if (this.status == StatusDoacao.ENTREGUE && this.dataEntrega == null) {
            this.dataEntrega = LocalDateTime.now();
        }
    }
}
