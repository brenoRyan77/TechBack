package br.com.fujideia.iesp.tecback.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.FilmeDTO;
import br.com.fujideia.iesp.tecback.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.entities.Filme;
import br.com.fujideia.iesp.tecback.entities.Genero;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.repository.FilmeRepository;
import br.com.fujideia.iesp.tecback.repository.GeneroRepository;

@Service
public class FilmeService {
	
    @Autowired
    private FilmeRepository repository;
    
    @Autowired
    private GeneroRepository generoRepository;

    public void salvar(FilmeDTO filmeDTO) throws ApplicationServiceException{
    	
    	try {
            Filme filme = new Filme();

            Genero genero = new Genero();
            genero.setId(filmeDTO.getIdGenero());

            filme.setGenero(genero);
            filme.setSinopse(filmeDTO.getSinopse());
            filme.setTitulo(filmeDTO.getTitulo());

            repository.save(filme);

        } catch (Exception e) {
            throw new ApplicationServiceException("message.erro.salvar");
        }
    }

    public void alterar(Integer id, FilmeDTO filmeDTO) throws ApplicationServiceException{
    	
		Optional<Filme> optionalFilme = repository.findById(id);

		if (optionalFilme.isPresent()) {
			
			Filme filme = optionalFilme.get();
			Genero genero = generoRepository.findById(filme.getGenero().getId())
					.orElseThrow(() -> new 
							ApplicationServiceException("message.erro.genero.inex"));

			filme.setSinopse(filmeDTO.getSinopse());
			filme.setTitulo(filmeDTO.getTitulo());

			genero.setId(filmeDTO.getIdGenero());

			filme.setGenero(genero);

			generoRepository.save(genero);
			repository.save(filme);
        	
    	}else {
    		throw new ApplicationServiceException("message.erro.filme.inex");
    	}
    }

    public List<Filme> listar(){
        return repository.findAll();
    }

    public void excluir(Integer id) throws ApplicationServiceException{
        
		Filme filme = repository.findById(id).get();

		if (filme != null) {
			repository.deleteById(filme.getId());

		} else {
			throw new ApplicationServiceException("message.erro.filme.inex");
		}
   
    }

    public Filme consultarPorId(Integer id) throws ApplicationServiceException{
        
    	Filme filme = repository.findById(id).get();
    	
    	if(filme != null) {
    		return filme;
    	}else {
    		
    		throw new ApplicationServiceException("message.erro.filme.inex");
    	}
    	
    }

}
