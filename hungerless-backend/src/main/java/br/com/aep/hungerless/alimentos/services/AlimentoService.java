package br.com.aep.hungerless.alimentos.services;

import br.com.aep.hungerless.alimentos.dto.AlimentoDTO;
import br.com.aep.hungerless.alimentos.entities.Alimento;
import br.com.aep.hungerless.alimentos.enums.StatusAlimento;
import br.com.aep.hungerless.alimentos.repositories.AlimentoRepository;
import br.com.aep.hungerless.categorias.entities.Categoria;
import br.com.aep.hungerless.categorias.repositories.CategoriaRepository;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Transactional
    public AlimentoDTO cadastrar(AlimentoDTO dto, Usuario doador) {
        if (dto.validade().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("A data de validade deve ser futura.");
        }

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        Alimento alimento = new Alimento();
        alimento.setNome(dto.nome());
        alimento.setDescricao(dto.descricao());
        alimento.setValidade(dto.validade());
        alimento.setQuantidade(dto.quantidade());
        alimento.setCategoria(categoria);
        alimento.setDoador(doador);
        alimento.setStatus(StatusAlimento.DISPONIVEL);

        alimento = alimentoRepository.save(alimento);
        return new AlimentoDTO(alimento);
    }

    @Transactional
    public AlimentoDTO atualizar(Long id, AlimentoDTO dto, Usuario doador) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado."));

        if (!alimento.getDoador().getId().equals(doador.getId())) {
            throw new SecurityException("Você não tem permissão para editar este alimento.");
        }

        Categoria categoria = categoriaRepository.findById(dto.categoriaId())
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada."));

        alimento.setNome(dto.nome());
        alimento.setDescricao(dto.descricao());
        alimento.setValidade(dto.validade());
        alimento.setQuantidade(dto.quantidade());
        alimento.setCategoria(categoria);
        alimento.setStatus(dto.status() != null ? dto.status() : alimento.getStatus());

        alimento = alimentoRepository.save(alimento);
        return new AlimentoDTO(alimento);
    }

    @Transactional
    public void deletar(Long id, Usuario doador) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado."));

        if (!alimento.getDoador().getId().equals(doador.getId())) {
            throw new SecurityException("Você não tem permissão para deletar este alimento.");
        }

        alimentoRepository.delete(alimento);
    }

    public Page<AlimentoDTO> listarDisponiveis(Pageable pageable) {
        return alimentoRepository.findAll(pageable)
                .map(AlimentoDTO::new);
    }

    public Optional<AlimentoDTO> buscarPorId(Long id) {
        return alimentoRepository.findById(id)
                .map(AlimentoDTO::new);
    }
}
