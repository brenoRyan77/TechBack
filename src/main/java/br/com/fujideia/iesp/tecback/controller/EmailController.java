package br.com.fujideia.iesp.tecback.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fujideia.iesp.tecback.dto.EmailDTO;
import br.com.fujideia.iesp.tecback.entities.Email;
import br.com.fujideia.iesp.tecback.service.EmailService;
import jakarta.validation.Valid;

@RestController
public class EmailController {

    @Autowired
    EmailService service;
    
    @PostMapping("/sending-email")
    public ResponseEntity<Email> sendingEmail(@RequestBody @Valid EmailDTO emailDTO){

        Email emailModel = new Email();
        BeanUtils.copyProperties(emailDTO, emailModel);
        service.sendEmail(emailModel);
        return new ResponseEntity<>(emailModel, HttpStatus.CREATED);
    }
}
