package br.com.aep.hungerless.categorias.repositories;

import br.com.aep.hungerless.categorias.entities.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
}
