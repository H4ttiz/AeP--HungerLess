package br.com.aep.hungerless.doacoes.controllers;

import br.com.aep.hungerless.doacoes.dto.DoacaoDTO;
import br.com.aep.hungerless.doacoes.services.DoacaoService;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {

    @Autowired
    private DoacaoService doacaoService;

    @PostMapping("/solicitar/{alimentoId}")
    public ResponseEntity<DoacaoDTO> solicitar(@PathVariable Long alimentoId, @AuthenticationPrincipal Usuario receptor) {
        try {
            DoacaoDTO doacao = doacaoService.solicitarDoacao(alimentoId, receptor);
            return ResponseEntity.status(HttpStatus.CREATED).body(doacao);
        } catch (SecurityException | IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/aprovar/{doacaoId}")
    public ResponseEntity<DoacaoDTO> aprovar(@PathVariable Long doacaoId, @AuthenticationPrincipal Usuario doador) {
        try {
            DoacaoDTO doacao = doacaoService.aprovarDoacao(doacaoId, doador);
            return ResponseEntity.ok(doacao);
        } catch (SecurityException | IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/entregar/{doacaoId}")
    public ResponseEntity<DoacaoDTO> marcarComoEntregue(@PathVariable Long doacaoId, @AuthenticationPrincipal Usuario doador) {
        try {
            DoacaoDTO doacao = doacaoService.marcarComoEntregue(doacaoId, doador);
            return ResponseEntity.ok(doacao);
        } catch (SecurityException | IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/recusar/{doacaoId}")
    public ResponseEntity<DoacaoDTO> recusar(@PathVariable Long doacaoId, @AuthenticationPrincipal Usuario doador) {
        try {
            DoacaoDTO doacao = doacaoService.recusarDoacao(doacaoId, doador);
            return ResponseEntity.ok(doacao);
        } catch (SecurityException | IllegalStateException | IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
