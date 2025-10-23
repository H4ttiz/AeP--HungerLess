package br.com.aep.hungerless.doacoes.dto;

import br.com.aep.hungerless.doacoes.entities.Doacao;
import br.com.aep.hungerless.doacoes.enums.StatusDoacao;

import java.time.LocalDateTime;

public record DoacaoDTO(
        Long id,
        Long alimentoId,
        Long doadorId,
        Long receptorId,
        StatusDoacao status,
        LocalDateTime dataSolicitacao,
        LocalDateTime dataAprovacao,
        LocalDateTime dataEntrega
) {
    public DoacaoDTO(Doacao doacao) {
        this(
                doacao.getId(),
                doacao.getAlimento().getId(),
                doacao.getDoador().getId(),
                doacao.getReceptor().getId(),
                doacao.getStatus(),
                doacao.getDataSolicitacao(),
                doacao.getDataAprovacao(),
                doacao.getDataEntrega()
        );
    }
}
