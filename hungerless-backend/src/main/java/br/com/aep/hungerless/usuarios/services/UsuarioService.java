package br.com.aep.hungerless.usuarios.services;

import br.com.aep.hungerless.contatos.entities.Contato;
import br.com.aep.hungerless.enderecos.entities.Endereco;
import br.com.aep.hungerless.usuarios.dto.UsuarioRequestDTO;
import br.com.aep.hungerless.usuarios.dto.UsuarioResponseDTO;
import br.com.aep.hungerless.usuarios.entities.Usuario;
import br.com.aep.hungerless.usuarios.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional
    public UsuarioResponseDTO cadastrar(UsuarioRequestDTO dto) {
        if (usuarioRepository.findByEmail(dto.email()) != null) {
            throw new RuntimeException("Usuário com este email já existe.");
        }


        Endereco endereco = new Endereco();
        endereco.setLogradouro(dto.logradouro());
        endereco.setNumero(dto.numero());
        endereco.setBairro(dto.bairro());
        endereco.setCidade(dto.cidade());
        endereco.setEstado(dto.estado());
        endereco.setCep(dto.cep());
        endereco.setLatitude(dto.latitude());
        endereco.setLongitude(dto.longitude());


        Contato contato = new Contato();
        contato.setTelefone(dto.telefone());
        contato.setCelular(dto.celular());
        contato.setEmailAlternativo(dto.emailAlternativo());


        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setEmail(dto.email());
        usuario.setSenha(passwordEncoder.encode(dto.senha()));
        usuario.setTipo(dto.tipo());
        usuario.setDocumento(dto.documento());
        usuario.setEndereco(endereco);
        usuario.setContato(contato);

        endereco.setUsuario(usuario);
        contato.setUsuario(usuario);

        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario);
    }
}
