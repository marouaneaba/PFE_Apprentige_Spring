package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;

import javax.websocket.server.PathParam;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryPersonne;
import com.lille1.PFE.Service.LoginService;

@Controller
@RequestMapping("/forgetPassword")
public class ControllerPassword {

	private String messageErreurEmailOrPseudo ="";
	private boolean PostMethod =false; // pour savoir si on a passé par la méthode Post;
	@Autowired
	private LoginService mLoginService;
	
	@Autowired
	private RepositoryPersonne mRepositoryPersonne;
	
	// la différence entre SimpleMailMessage et MimeMessage : MimeMessage on 
	// peux envoyer des piéce joint et des contenus complexe que 
	// SimpleMailMessage
	
	@Autowired
	private JavaMailSender sender;

	@RequestMapping(method = RequestMethod.GET)
	public String GetLogin(ModelMap pModel) {

		if(!PostMethod) messageErreurEmailOrPseudo="";
		pModel.addAttribute("mesageError",messageErreurEmailOrPseudo );
		
		PostMethod = false;
		return "ForgetPassword";
		
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public RedirectView POSTLogin(ModelMap pModel,@RequestParam("email") String email,@RequestParam("name") String name) {

		Personne p = mRepositoryPersonne.findByNomAndEmail(name, email);
		System.out.println("forget : "+p);
		PostMethod = true;
		if(p == null){
			messageErreurEmailOrPseudo = " le Pseudo ou Email n'existe pas";
			return new RedirectView("/forgetPassword");
		}else{
			messageErreurEmailOrPseudo = "";
			mLoginService.EnvoyerMessage(email,"["+name+"] mot de passe Oublier",
					"Bonjour Mr."+name+", \n votre Mot de passe c'est : ");
		}
		
		return new RedirectView("/login");

	}
}
