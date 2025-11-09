package br.com.aep.hungerless.usuarios.controllers;

import br.com.aep.hungerless.usuarios.dto.UsuarioRequestDTO;
import br.com.aep.hungerless.usuarios.dto.UsuarioResponseDTO;
import br.com.aep.hungerless.usuarios.dto.UsuarioUpdateDTO;
import br.com.aep.hungerless.usuarios.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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


    @GetMapping
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        List<UsuarioResponseDTO> usuarios = usuarioService.findAll();
        // Retorna Status 200 OK. O Spring lida com a lista vazia.
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {

        UsuarioResponseDTO usuario = usuarioService.findById(id);
        return ResponseEntity.ok(usuario);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioResponseDTO> updateById(
            @PathVariable Long id,
            @RequestBody @Valid UsuarioUpdateDTO dto) {

        UsuarioResponseDTO usuarioAtualizado = usuarioService.updateById(id, dto);
        return ResponseEntity.ok(usuarioAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        usuarioService.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/doadores")
    public List<UsuarioResponseDTO> listarDoadores() {
        return usuarioService.buscarDoadores();
    }


}
