package br.com.aep.hungerless.usuarios.dto;

import br.com.aep.hungerless.usuarios.entities.Usuario;
import br.com.aep.hungerless.usuarios.enums.TipoUsuario;

public record UsuarioResponseDTO(
        Long id,
        String nome,
        String email,
        TipoUsuario tipo,
        String documento
) {
    public UsuarioResponseDTO(Usuario usuario) {
        this(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getTipo(), usuario.getDocumento());
    }
}
