	package br.com.fujideia.iesp.tecback.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tb_serie")
public class Serie implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Integer id;

	private String titulo;
	
	private String temporada;
	
	private String episodio;
	
	@Column(name = "ds_sinopse", length = 500)
    private String sinopse;

	@ManyToOne
	@JoinColumn(name = "id_genero")
	private Genero genero;

	private String classificacaoIndicativa;
}
