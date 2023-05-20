package br.com.fujideia.iesp.tecback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.entities.Genero;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.repository.GeneroRepository;

@Service
public class GeneroService {

	@Autowired
	GeneroRepository repository;
	
	public List<Genero> listarGenero(){
		return repository.findAll();
	}
	
	public void incluirGenero(GeneroDTO dto) throws ApplicationServiceException {
		
		try {
			
			Genero genero = new Genero();
			genero.setDescricao(dto.getDescricao());
			repository.save(genero);
			
		}catch (Exception e) {
			throw new ApplicationServiceException("message.erro.salva.genero");
		}
	}
	
	public void alterarGenero(Long id, GeneroDTO dto) throws ApplicationServiceException {

		Genero genero = repository.findById(id).get();

		if (genero != null) {
			genero.setDescricao(dto.getDescricao());
			repository.save(genero);
		} else {
			throw new ApplicationServiceException("message.erro.genero.inex");
		}

	}
	
	public void deletarGenero(Long id) throws ApplicationServiceException {
		
		Genero genero = repository.findById(id).get();
		
		if(genero != null) {
			repository.deleteById(genero.getId());
		}else {
			throw new ApplicationServiceException("message.erro.genero.inex");
		}
	}
}
