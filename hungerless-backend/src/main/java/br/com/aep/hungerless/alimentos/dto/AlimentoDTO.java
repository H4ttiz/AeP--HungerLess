package br.com.aep.hungerless.alimentos.dto;

import br.com.aep.hungerless.alimentos.entities.Alimento;
import br.com.aep.hungerless.alimentos.enums.StatusAlimento;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public record AlimentoDTO(
        Long id,
        @NotBlank String nome,
        String descricao,
        @NotNull @Future LocalDate validade,
        @NotNull @Positive Integer quantidade,
        Long categoriaId,
        StatusAlimento status
) {
    public AlimentoDTO(Alimento alimento) {
        this(
                alimento.getId(),
                alimento.getNome(),
                alimento.getDescricao(),
                alimento.getValidade(),
                alimento.getQuantidade(),
                alimento.getCategoria() != null ? alimento.getCategoria().getId() : null,
                alimento.getStatus()
        );
    }
}
