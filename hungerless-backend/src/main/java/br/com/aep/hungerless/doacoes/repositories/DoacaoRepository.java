package br.com.aep.hungerless.doacoes.repositories;

import br.com.aep.hungerless.doacoes.entities.Doacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DoacaoRepository extends JpaRepository<Doacao, Long> {
}
