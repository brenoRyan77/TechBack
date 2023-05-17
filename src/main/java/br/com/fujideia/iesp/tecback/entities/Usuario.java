package br.com.fujideia.iesp.tecback.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "tb_usuario")
@Data
public class Usuario {
	
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nomeCompleto;
    @Column(name = "dtNascimento")
    private String dataNasc;
    private String email;
    @Column(name = "senha")
    private String senha;
    
    @OneToOne
    @JoinColumn(name = "id_cartao")
    private Cartao dadosCartao;
}
