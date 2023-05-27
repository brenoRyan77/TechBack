package br.com.fujideia.iesp.tecback.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "tb_filme")
public class Filme implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @GeneratedValue
    private Integer id;
	
    private String titulo;
    
    @Column(name = "ds_sinopse", length = 500)
    private String sinopse;
    
    @ManyToOne
    @JoinColumn(name = "id_genero")
    private Genero genero;

}
