package br.com.aep.hungerless.categorias.controllers;

import br.com.aep.hungerless.categorias.entities.Categoria;
import br.com.aep.hungerless.categorias.repositories.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping
    public ResponseEntity<List<Categoria>> listar() {
        return ResponseEntity.ok(categoriaRepository.findAll());
    }


    @PostMapping("/admin")
    public ResponseEntity<Categoria> cadastrar(@RequestBody Categoria categoria) {

        Categoria novaCategoria = categoriaRepository.save(categoria);
        return ResponseEntity.status(HttpStatus.CREATED).body(novaCategoria);
    }
}
