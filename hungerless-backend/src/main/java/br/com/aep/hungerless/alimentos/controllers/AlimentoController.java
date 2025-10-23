package br.com.aep.hungerless.alimentos.controllers;

import br.com.aep.hungerless.alimentos.dto.AlimentoDTO;
import br.com.aep.hungerless.alimentos.services.AlimentoService;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @PostMapping
    public ResponseEntity<AlimentoDTO> cadastrar(@RequestBody @Valid AlimentoDTO dto, @AuthenticationPrincipal Usuario doador) {
        try {
            AlimentoDTO alimento = alimentoService.cadastrar(dto, doador);
            return ResponseEntity.status(HttpStatus.CREATED).body(alimento);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AlimentoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid AlimentoDTO dto, @AuthenticationPrincipal Usuario doador) {
        try {
            AlimentoDTO alimento = alimentoService.atualizar(id, dto, doador);
            return ResponseEntity.ok(alimento);
        } catch (IllegalArgumentException | SecurityException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id, @AuthenticationPrincipal Usuario doador) {
        try {
            alimentoService.deletar(id, doador);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException | SecurityException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping
    public ResponseEntity<Page<AlimentoDTO>> listar(@PageableDefault(size = 10, sort = {"dataCadastro"}) Pageable pageable) {
        Page<AlimentoDTO> alimentos = alimentoService.listarDisponiveis(pageable);
        return ResponseEntity.ok(alimentos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AlimentoDTO> buscarPorId(@PathVariable Long id) {
        return alimentoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
