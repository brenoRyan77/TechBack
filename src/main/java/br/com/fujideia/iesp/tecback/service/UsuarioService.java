package br.com.fujideia.iesp.tecback.service;

import java.util.List;
import java.util.Optional;

import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private CartaoRepository cartaoRepository;

    public UsuarioDTO salvar(UsuarioDTO user) throws Exception {

        try {
            Usuario usuario = new Usuario();
            String senha = criptografarSenha(user.getSenha());

            if (StringUtils.isNotBlank(senha)) {
                usuario.setSenha(senha);
            }
            usuario.setNomeCompleto(user.getNomeCompleto());
            usuario.setEmail(user.getEmail());
            usuario.setDataNasc(user.getDataNasc());

            CartaoDTO cartaoDTO = user.getCartao();

            Cartao cartao = new Cartao();
            cartao.setCodSeguranca(cartaoDTO.getCodSeguranca());

            String cpfSemMascara = UtilidadesDesenvolvimento.retiraCpf(cartaoDTO.getCpf());
            boolean cpfValido = CpfRgUtil.validaCPF(cpfSemMascara);

            if (cpfValido) {
                cartao.setCpf(cpfSemMascara);
            } else {
                throw new ApplicationServiceException("message.erro.usuario.cpf");
            }
            cartao.setNumCartao(cartaoDTO.getNumCartao());
            cartao.setTitularNome(cartaoDTO.getTitularNome());
            cartao.setValidadeCartao(cartaoDTO.getValidadeCartao());

            usuario.setDadosCartao(cartao);

            cartaoRepository.save(cartao);
            repository.save(usuario);

            return user;
        } catch (Exception e) {
            throw new ApplicationServiceException("message.erro.usuario.salvar");
        }
    }
    public void alterar(UsuarioDTO user, Long id) throws Exception {

        Optional<Usuario> op = repository.findById(id);
        String senha = criptografarSenha(user.getSenha());

        if(!op.isEmpty()){

            Usuario usuario = op.get();
            usuario.setNomeCompleto(user.getNomeCompleto());
            usuario.setEmail(user.getEmail());
            usuario.setSenha(senha);
            repository.save(usuario);

        }else{
            throw new ApplicationServiceException("message.erro.usuario.alterar");
        }

    }
    public List<Usuario> listar() {
        return repository.findAll();
    }
    public void excluir(Long id) throws ApplicationServiceException{

            Optional<Usuario> op = repository.findById(id);

            if(!op.isEmpty()){
                repository.deleteById(id);
            }else {
                throw new ApplicationServiceException("message.erro.usuario.excluir");
            }


    }
    public Usuario consultarPorId(Long id) throws ChangeSetPersister.NotFoundException {
        return repository.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
    }

    public String criptografarSenha(String senha){

        return BCrypt.hashpw(senha, BCrypt.gensalt());

    }
}
