package br.com.aep.hungerless.usuarios.controllers;

import br.com.aep.hungerless.usuarios.dto.UsuarioRequestDTO;
import br.com.aep.hungerless.usuarios.dto.UsuarioResponseDTO;
import br.com.aep.hungerless.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<UsuarioResponseDTO> cadastrar(@RequestBody @Valid UsuarioRequestDTO dto) {
        try {
            UsuarioResponseDTO usuario = usuarioService.cadastrar(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(usuario);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
