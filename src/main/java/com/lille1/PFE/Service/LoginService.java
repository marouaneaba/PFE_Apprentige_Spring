package com.lille1.PFE.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

	// la différence entre SimpleMailMessage et MimeMessage : MimeMessage on 
		// peux envoyer des piéce joint et des contenus complexe que 
		// SimpleMailMessage
		
		@Autowired
		private JavaMailSender sender;
	
	// quand un utilisateur oublier son mot de passe , peux le récuperer dans sa boite d'email
	public void EnvoyerMessage(String destinateur,String objet,String msg){
		
		MimeMessage message = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message);

		try {
			helper.setTo(destinateur);
			helper.setText(msg);
			helper.setSubject(objet);
		} catch (MessagingException e) {
			System.out.println("Error : "+e);
			e.printStackTrace();
		}
		
		sender.send(message);
	}
	
}
