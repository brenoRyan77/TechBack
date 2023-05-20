package br.com.fujideia.iesp.tecback.service;

import java.util.List;
import java.util.Optional;

import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import org.apache.commons.lang3.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.CartaoDTO;
import br.com.fujideia.iesp.tecback.dto.EmailDTO;
import br.com.fujideia.iesp.tecback.dto.UsuarioDTO;
import br.com.fujideia.iesp.tecback.entities.Cartao;
import br.com.fujideia.iesp.tecback.entities.Email;
import br.com.fujideia.iesp.tecback.entities.Usuario;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.repository.CartaoRepository;
import br.com.fujideia.iesp.tecback.repository.UsuarioRepository;
import br.com.fujideia.iesp.tecback.util.CpfRgUtil;
import br.com.fujideia.iesp.tecback.util.UtilidadesDesenvolvimento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;
    
    @Autowired
    private CartaoRepository cartaoRepository;
    
    @Autowired
    EmailService emailService;
    
    @Value("${spring.mail.username}")
    private String emailDefault;

	public UsuarioDTO salvar(UsuarioDTO user) throws Exception {

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
				throw new ApplicationServiceException("Digite um CPF válido");
			}

			verificarCPF(cpfSemMascara);

			cartao.setNumCartao(cartaoDTO.getNumCartao());
			cartao.setTitularNome(cartaoDTO.getTitularNome());
			cartao.setValidadeCartao(cartaoDTO.getValidadeCartao());

			cartaoRepository.save(cartao);
			repository.save(usuario);

			enviarEmail(usuario);
			return user;

	}
    public void verificarCPF(String cpf) throws ApplicationServiceException {
		Cartao cartao = cartaoRepository.verificarCpf(cpf);
		if (cartao != null) {
			throw new ApplicationServiceException("message.erro.cpf.existente");
		}
	}
    public void alterar(UsuarioDTO user, Long id) throws ApplicationServiceException {

        Optional<Usuario> op = repository.findById(id);
        String senha = criptografarSenha(user.getSenha());

        if(!op.isEmpty()){

            Usuario usuario = op.get();
            usuario.setNomeCompleto(user.getNomeCompleto());
            usuario.setEmail(user.getEmail());
            usuario.setSenha(senha);
            repository.save(usuario);

        }else{
            throw new ApplicationServiceException("message.erro.user.not.found");
        }

    }
    
    public List<Usuario> listar() {
        return repository.findAll();
    }
    
    public void excluir(Long id) throws ApplicationServiceException{

    	Optional<Usuario> usuario = repository.findById(id);
    	
    	if(!usuario.isEmpty()) {
    		repository.deleteById(usuario.get().getId());
    	}else {
    		throw new ApplicationServiceException("message.erro.user.not.found");
    	}
    }
    
    public Usuario consultarPorId(Long id) throws ApplicationServiceException  {
        
    	Usuario user = repository.findById(id).get();
    	
    	if(user != null) {
    		return user;
    		
    	}else {
    		throw new ApplicationServiceException("message.erro.user.not.found");
    	}
    }

    public String criptografarSenha(String senha){

        return BCrypt.hashpw(senha, BCrypt.gensalt());

    }
    
	public void enviarEmail(Usuario user) throws ApplicationServiceException {

		try {

			EmailDTO emailDTO = new EmailDTO();
			StringBuilder corpoEmail = new StringBuilder();

			Email emailModel = new Email();
			BeanUtils.copyProperties(emailDTO, emailModel);

			corpoEmail.append("Prezado(a) ").append(user.getNomeCompleto()).append(",\n\n");
			corpoEmail.append("Gostaríamos de informar que seu cadastro foi"
					+ " realizado com sucesso. É um prazer tê-lo(a) como parte de nossa comunidade.\n\n");
			corpoEmail.append("Em caso de dúvidas ou necessidade de suporte,"
					+ " não hesite em entrar em contato conosco. Estamos sempre prontos para ajudar.\n\n");
			corpoEmail.append("Agradecemos por escolher nossos serviços e"
					+ " esperamos proporcionar uma experiência satisfatória" + " em todas as interações.\n\n");
			corpoEmail.append("Atenciosamente,\n");
			corpoEmail.append("Equipe TeckBack.");

			emailModel.setSubject("Cadastro TeckBack - UNIESP");
			emailModel.setEmailTo(user.getEmail());
			emailModel.setEmailFrom(emailDefault);
			emailModel.setOwnerRef(user.getId().toString() + " - " + user.getNomeCompleto());
			emailModel.setText(corpoEmail.toString());

			emailService.sendEmail(emailModel);

		} catch (Exception e) {
			throw new ApplicationServiceException("message.erro.envio.email");
		}
	}
}
