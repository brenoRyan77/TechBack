package br.com.fujideia.iesp.tecback.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import br.com.fujideia.iesp.tecback.entities.Email;
import br.com.fujideia.iesp.tecback.enums.StatusEmail;
import br.com.fujideia.iesp.tecback.repository.EmailRepository;
import org.springframework.mail.SimpleMailMessage;

@Service
public class EmailService {

	
	@Autowired
    EmailRepository repository;

    @Autowired
    private JavaMailSender emailSender;

	public Email sendEmail(Email emailModel) {

        emailModel.setSendDateEmail(LocalDateTime.now());

        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(emailModel.getEmailFrom());
            message.setTo(emailModel.getEmailTo());
            message.setSubject(emailModel.getSubject());
            message.setText(emailModel.getText());
            emailSender.send(message);

            emailModel.setStatusEmail(StatusEmail.SENT);
        }catch (MailException e){
            emailModel.setStatusEmail(StatusEmail.ERROR);
        }finally {
            return repository.save(emailModel);
        }
    }
}
