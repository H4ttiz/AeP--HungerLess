package br.com.aep.hungerless.usuarios.dto;

import br.com.aep.hungerless.usuarios.enums.TipoUsuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UsuarioRequestDTO(
        @NotBlank String nome,
        @NotBlank @Email String email,
        @NotBlank String senha,
        @NotNull TipoUsuario tipo,
        @NotBlank String documento,

        @NotBlank String logradouro,
        @NotBlank String numero,
        @NotBlank String bairro,
        @NotBlank String cidade,
        @NotBlank String estado,
        @NotBlank String cep,
        Double latitude,
        Double longitude,

        String telefone,
        @NotBlank String celular,
        String emailAlternativo
) {
}
