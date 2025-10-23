package br.com.aep.hungerless.doacoes.services;

import br.com.aep.hungerless.alimentos.entities.Alimento;
import br.com.aep.hungerless.alimentos.enums.StatusAlimento;
import br.com.aep.hungerless.alimentos.repositories.AlimentoRepository;
import br.com.aep.hungerless.doacoes.dto.DoacaoDTO;
import br.com.aep.hungerless.doacoes.entities.Doacao;
import br.com.aep.hungerless.doacoes.enums.StatusDoacao;
import br.com.aep.hungerless.doacoes.repositories.DoacaoRepository;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import br.com.aep.hungerless.usuarios.enums.TipoUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DoacaoService {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private AlimentoRepository alimentoRepository;

    @Transactional
    public DoacaoDTO solicitarDoacao(Long alimentoId, Usuario receptor) {
        if (receptor.getTipo() != TipoUsuario.RECEPTOR) {
            throw new SecurityException("Apenas RECEPTOR pode solicitar doações.");
        }

        Alimento alimento = alimentoRepository.findById(alimentoId)
                .orElseThrow(() -> new IllegalArgumentException("Alimento não encontrado."));

        if (alimento.getStatus() != StatusAlimento.DISPONIVEL) {
            throw new IllegalStateException("Alimento não está disponível para doação.");
        }

        Doacao doacao = new Doacao();
        doacao.setAlimento(alimento);
        doacao.setDoador(alimento.getDoador());
        doacao.setReceptor(receptor);
        doacao.setStatus(StatusDoacao.AGUARDANDO);

        alimento.setStatus(StatusAlimento.RESERVADO);
        alimentoRepository.save(alimento);

        doacao = doacaoRepository.save(doacao);
        return new DoacaoDTO(doacao);
    }

    @Transactional
    public DoacaoDTO aprovarDoacao(Long doacaoId, Usuario doador) {
        Doacao doacao = doacaoRepository.findById(doacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Doação não encontrada."));

        if (!doacao.getDoador().getId().equals(doador.getId())) {
            throw new SecurityException("Apenas o doador pode aprovar esta doação.");
        }

        if (doacao.getStatus() != StatusDoacao.AGUARDANDO) {
            throw new IllegalStateException("Doação não está no status AGUARDANDO.");
        }

        doacao.setStatus(StatusDoacao.APROVADA);
        return new DoacaoDTO(doacaoRepository.save(doacao));
    }

    @Transactional
    public DoacaoDTO marcarComoEntregue(Long doacaoId, Usuario doador) {
        Doacao doacao = doacaoRepository.findById(doacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Doação não encontrada."));

        if (!doacao.getDoador().getId().equals(doador.getId())) {
            throw new SecurityException("Apenas o doador pode marcar como entregue.");
        }

        if (doacao.getStatus() != StatusDoacao.APROVADA) {
            throw new IllegalStateException("Doação precisa estar no status APROVADA para ser entregue.");
        }

        doacao.setStatus(StatusDoacao.ENTREGUE);
        doacao.getAlimento().setStatus(StatusAlimento.DOADO);
        alimentoRepository.save(doacao.getAlimento());

        return new DoacaoDTO(doacaoRepository.save(doacao));
    }

    @Transactional
    public DoacaoDTO recusarDoacao(Long doacaoId, Usuario doador) {
        Doacao doacao = doacaoRepository.findById(doacaoId)
                .orElseThrow(() -> new IllegalArgumentException("Doação não encontrada."));

        if (!doacao.getDoador().getId().equals(doador.getId())) {
            throw new SecurityException("Apenas o doador pode recusar esta doação.");
        }

        if (doacao.getStatus() != StatusDoacao.AGUARDANDO) {
            throw new IllegalStateException("Doação não está no status AGUARDANDO.");
        }

        doacao.setStatus(StatusDoacao.RECUSADA);
        doacao.getAlimento().setStatus(StatusAlimento.DISPONIVEL); // Volta a ficar disponível
        alimentoRepository.save(doacao.getAlimento());

        return new DoacaoDTO(doacaoRepository.save(doacao));
    }
}
