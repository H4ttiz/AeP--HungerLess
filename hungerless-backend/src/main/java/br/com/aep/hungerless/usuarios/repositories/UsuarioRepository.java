package br.com.aep.hungerless.usuarios.repositories;

import br.com.aep.hungerless.usuarios.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
