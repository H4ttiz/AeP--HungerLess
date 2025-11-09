package br.com.aep.hungerless.config.security;

import br.com.aep.hungerless.config.security.dto.AuthenticationDTO;
import br.com.aep.hungerless.config.security.dto.LoginResponseDTO;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import br.com.aep.hungerless.usuarios.enums.TipoUsuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthenticationDTO data) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.senha());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((Usuario) auth.getPrincipal());
        var tipoUsuario = TipoUsuario.fromAuthority(auth.getAuthorities().iterator().next().getAuthority());
        return ResponseEntity.ok(new LoginResponseDTO(token,  tipoUsuario));
    }
}
