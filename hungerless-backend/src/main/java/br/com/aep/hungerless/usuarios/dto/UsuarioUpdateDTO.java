package br.com.aep.hungerless.usuarios.dto;

import br.com.aep.hungerless.usuarios.enums.TipoUsuario;

public record UsuarioUpdateDTO(
        String nome,
        String email,
        String senha,
        TipoUsuario tipo,
        String documento
) {}