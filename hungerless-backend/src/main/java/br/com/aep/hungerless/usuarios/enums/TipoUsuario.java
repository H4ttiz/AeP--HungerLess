package br.com.aep.hungerless.usuarios.enums;

public enum TipoUsuario {
    ADMIN,
    DOADOR,
    RECEPTOR;

    public static TipoUsuario fromAuthority(String authority) {
        return TipoUsuario.valueOf(authority.replace("ROLE_", ""));
    }
}

