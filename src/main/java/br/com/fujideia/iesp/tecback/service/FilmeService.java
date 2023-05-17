package br.com.fujideia.iesp.tecback.service;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.FilmeDTO;
import br.com.fujideia.iesp.tecback.dto.GeneroDTO;
import br.com.fujideia.iesp.tecback.entities.Filme;
import br.com.fujideia.iesp.tecback.entities.Genero;
import br.com.fujideia.iesp.tecback.repository.FilmeRepository;
import br.com.fujideia.iesp.tecback.repository.GeneroRepository;
import jakarta.ws.rs.NotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FilmeService {
	
    @Autowired
    private FilmeRepository repository;
    
    @Autowired
    private GeneroRepository generoRepository;

    public FilmeDTO salvar(FilmeDTO filmeDTO){
    	
    	Filme filme = new Filme();
    	
    	Genero genero = new Genero();
    	
    	GeneroDTO generoDTO = filmeDTO.getGenero();
    	genero.setDescircao(generoDTO.getDescricao());
    	
    	filme.setGenero(genero);
    	filme.setSinopse(filmeDTO.getSinopse());
    	filme.setTitulo(filmeDTO.getTitulo());
    	
    	generoRepository.save(genero);
    	
    	repository.save(filme);
       
    	return filmeDTO;
    }

    public Filme alterar(Integer id, FilmeDTO filmeDTO){
    	
		Optional<Filme> optionalFilme = repository.findById(id);

		if (optionalFilme.isPresent()) {
			
			Filme filme = optionalFilme.get();
			Genero genero = generoRepository.findById(filme.getGenero().getId())
					.orElseThrow(() -> new IllegalArgumentException("Gênero não encontrado"));

			filme.setSinopse(filmeDTO.getSinopse());
			filme.setTitulo(filmeDTO.getTitulo());

			GeneroDTO generoDTO = filmeDTO.getGenero();
			genero.setDescircao(generoDTO.getDescricao());

			filme.setGenero(genero);

			generoRepository.save(genero);
			repository.save(filme);
			return filme;
        	
    	}else {
    		throw new IllegalArgumentException("Filme não encontrado");
    	}
    }

    public List<Filme> listar(){
        return repository.findAll();
    }

    public Boolean excluir(Integer id){
        try {
            repository.deleteById(id);
        }catch (Exception e ){
            log.info("Erro ao realizar Exclusão : {}", e);
            return false;

        }
        return true;
    }

    public Filme consultarPorId(Integer id){
        return repository
                .findById(id)
                .orElseThrow(NotFoundException::new);
    }

}
