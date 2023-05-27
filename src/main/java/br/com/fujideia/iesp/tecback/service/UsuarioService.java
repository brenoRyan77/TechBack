package br.com.fujideia.iesp.tecback.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.CartaoDTO;
import br.com.fujideia.iesp.tecback.dto.UsuarioDTO;
import br.com.fujideia.iesp.tecback.entities.Cartao;
import br.com.fujideia.iesp.tecback.entities.Usuario;
import br.com.fujideia.iesp.tecback.repository.CartaoRepository;
import br.com.fujideia.iesp.tecback.repository.UsuarioRepository;
import br.com.fujideia.iesp.tecback.util.CpfRgUtil;
import br.com.fujideia.iesp.tecback.util.UtilidadesDesenvolvimento;
import jakarta.el.MethodNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

	private final UsuarioRepository usuarioRepository;
    
    @Autowired
    private CartaoRepository cartaoRepository;
    
    @Autowired
    public UsuarioService(@Qualifier("usuarioRepository") UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public UsuarioDTO salvar(UsuarioDTO user) throws Exception {

        Usuario usuario = new Usuario();
        String senha = criptografarSenha(user.getSenha());

        if (StringUtils.isNotBlank(senha)) {
            usuario.setSenha(senha);
        }
        usuario.setNomeCompleto(user.getNomeCompleto());
        usuario.setEmail(user.getEmail());
        usuario.setDataNasc(user.getDataNasc());
        usuario.setLogin(user.getLogin());

        CartaoDTO cartaoDTO = user.getCartao();

        Cartao cartao = new Cartao();
        cartao.setCodSeguranca(cartaoDTO.getCodSeguranca());
        
        String cpfSemMascara = UtilidadesDesenvolvimento.retiraCpf(cartaoDTO.getCpf());
        boolean cpfValido = CpfRgUtil.validaCPF(cpfSemMascara);
        
        if(cpfValido) {
        	cartao.setCpf(cpfSemMascara);
        }else {
        	throw new IllegalArgumentException("Digite um CPF válido");
        }
        cartao.setNumCartao(cartaoDTO.getNumCartao());
        cartao.setTitularNome(cartaoDTO.getTitularNome());
        cartao.setValidadeCartao(cartaoDTO.getValidadeCartao());

        usuario.setDadosCartao(cartao);

        cartaoRepository.save(cartao);
        usuarioRepository.save(usuario);

        return user;
    }
    public void alterar(UsuarioDTO user, Long id) throws Exception {

        Optional<Usuario> op = usuarioRepository.findById(id);
        String senha = criptografarSenha(user.getSenha());

        if(!op.isEmpty()){

            Usuario usuario = op.get();
            usuario.setNomeCompleto(user.getNomeCompleto());
            usuario.setEmail(user.getEmail());
            usuario.setSenha(senha);
            usuarioRepository.save(usuario);

        }else{
            throw new MethodNotFoundException();
        }

    }
    public List<Usuario> listar() {
        return usuarioRepository.findAll();
    }
    public Boolean excluir(Long id){

        try {
            Optional<Usuario> op = usuarioRepository.findById(id);
            if(!op.isEmpty()){
            	usuarioRepository.deleteById(id);
            }
        }catch (Exception e){
            log.info("Erro ao realizar exclusao", e.getMessage());
            return false;
        }
        return true;
    }
    public Usuario consultarPorId(Long id) throws ChangeSetPersister.NotFoundException {
        return usuarioRepository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public String criptografarSenha(String senha){

        return BCrypt.hashpw(senha, BCrypt.gensalt());

    }
}
