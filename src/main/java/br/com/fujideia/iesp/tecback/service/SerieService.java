package br.com.fujideia.iesp.tecback.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.dto.SerieDTO;
import br.com.fujideia.iesp.tecback.entities.Genero;
import br.com.fujideia.iesp.tecback.entities.Serie;
import br.com.fujideia.iesp.tecback.exception.ApplicationServiceException;
import br.com.fujideia.iesp.tecback.repository.GeneroRepository;
import br.com.fujideia.iesp.tecback.repository.SerieRepository;

@Service
public class SerieService {

	@Autowired
	private SerieRepository repository;

	@Autowired
	private GeneroRepository generoRepository;

	public void salvar(SerieDTO serieDTO) throws ApplicationServiceException {

		try {
			Serie serie = new Serie();

			Genero genero = new Genero();
			genero.setId(serieDTO.getGenero());

			serie.setGenero(genero);
			serie.setSinopse(serieDTO.getSinopse());
			serie.setTitulo(serieDTO.getTitulo());
			serie.setTemporada(serieDTO.getTemporada());
			serie.setEpisodio(serieDTO.getEpisodio());
			serie.setClassificacaoIndicativa(serieDTO.getClassificacaoIndicativa());

			repository.save(serie);

		} catch (Exception e) {
			throw new ApplicationServiceException("message.erro.salvar");
		}
	}

	public void alterar(Integer id, SerieDTO serieDTO) throws ApplicationServiceException {

		Optional<Serie> optionalSerie = repository.findById(id);

		if (optionalSerie.isPresent()) {

			Serie serie = optionalSerie.get();
			Genero genero = generoRepository.findById(serie.getGenero().getId())
					.orElseThrow(() -> new ApplicationServiceException("message.erro.genero.inex"));

			serie.setSinopse(serieDTO.getSinopse());
			serie.setTitulo(serieDTO.getTitulo());
			serie.setEpisodio(serieDTO.getEpisodio());
			serie.setClassificacaoIndicativa(serieDTO.getClassificacaoIndicativa());
			serie.setTemporada(serieDTO.getTemporada());

			genero.setId(serieDTO.getGenero());

			serie.setGenero(genero);

			generoRepository.save(genero);
			repository.save(serie);

		} else {
			throw new ApplicationServiceException("message.erro.filme.inex");
		}
	}

	public List<Serie> listar() {
		return repository.findAll();
	}

	public void excluir(Integer id) throws ApplicationServiceException {

		Serie serie = repository.findById(id).get();

		if (serie != null) {
			repository.deleteById(serie.getId());

		} else {
			throw new ApplicationServiceException("message.erro.filme.inex");
		}

	}

	public Serie consultarPorId(Integer id) throws ApplicationServiceException {

		Serie serie = repository.findById(id).get();

		if (serie != null) {
			return serie;
		} else {

			throw new ApplicationServiceException("message.erro.filme.inex");
		}

	}
}
