package br.com.aep.hungerless.alimentos.repositories;

import br.com.aep.hungerless.alimentos.entities.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
