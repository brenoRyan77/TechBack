package br.com.fujideia.iesp.tecback.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fujideia.iesp.tecback.entities.Email;

public interface EmailRepository extends JpaRepository<Email, UUID>{

}
