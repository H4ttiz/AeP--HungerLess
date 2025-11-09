package br.com.aep.hungerless.config.security.dto;

import br.com.aep.hungerless.usuarios.enums.TipoUsuario;

public record LoginResponseDTO(String token, TipoUsuario tipo) {
}
