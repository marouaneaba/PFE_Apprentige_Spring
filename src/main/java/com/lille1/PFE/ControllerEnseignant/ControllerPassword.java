package com.lille1.PFE.ControllerEnseignant;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	/**
	 * récuper l'interface de la saisie de l'email , quand l'utilisateur à oublier son mot de passe
	 * @param pModel utiliser pour afficher un message a l'utilisateur ,quand il saisait un email n'est pas correct
	 * @return interface de saisie Email sur le quel vas recevoir ces information de l'authentification
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String GetLogin(ModelMap pModel) {

		if(!PostMethod) messageErreurEmailOrPseudo="";
		pModel.addAttribute("mesageError",messageErreurEmailOrPseudo );
		
		PostMethod = false;
		return "ForgetPassword";
		
	}
	
	/**
	 * l'authentification de l'utilisateur
	 * @param pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @param email email de l'utilisateur à oublier son mot de pass
	 * @return interface de login , pour faire l'authentification
	 */
	@RequestMapping(method = RequestMethod.POST)
	public RedirectView POSTLogin(ModelMap pModel,@RequestParam("email") String email) {

		Personne p = mRepositoryPersonne.findByEmail( email);
		System.out.println("forget : "+p);
		PostMethod = true;
		if(p == null){
			messageErreurEmailOrPseudo = " le Pseudo ou Email n'existe pas";
			return new RedirectView("/forgetPassword");
		}else{
			messageErreurEmailOrPseudo = "";
			mLoginService.EnvoyerMessage(email,"["+email+"] mot de passe Oublier",
					"Bonjour Mr."+email+", \n votre Mot de passe c'est : ");
		}
		
		return new RedirectView("/login");

	}
}
