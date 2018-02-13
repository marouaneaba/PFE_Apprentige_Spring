package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryPersonne;

import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
public class ControllerLogin {



	@Autowired
	private RepositoryPersonne mRepositoryPersonne;

	/**
	 * recupérer l'interface de l'authentification
	 * @param principal un objet nos permet de stocker des données vas etre accéder par tout les types de l'utilisateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @param logout nos permet de savoir si l'utilisateur a quitter sa session ou non
	 * @param error nos permet de savoir si utilisateur à pas reussie l'authntification
	 * @return fournie un interface de l'authentification
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String GetLogin(Principal principal, ModelMap pModel, @PathParam(value = "logout") String logout,
			@PathParam("error") String error) {

		pModel.addAttribute("logout", logout);
		pModel.addAttribute("error", error);
		String result = logout + error;
		System.out.println("hello world GET : " + result);
		if (principal != null)
			System.out.println("Principal : " + principal.getName());
		return "login";

	}

	/**
	 * verifier les information envoyer par l'utilisateur pour l'authentification
	 * @param request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pseudo pseudo saisie par l'utilisateur pour l'authentification
	 * @param password mot de pass saisie par l'utilisateur pour l'authentification
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public RedirectView VerifyLogin(HttpServletRequest request, @RequestParam("username") String pseudo,
			@RequestParam("password") String password, ModelMap pModel) {

		HttpSession session = request.getSession();

		Personne personne = mRepositoryPersonne.findByNomAndPassword(pseudo, password);

		if (personne != null && personne.getNom().equals(pseudo) && personne.getPassword().equals(password)) {
			System.out.println(personne);
			session.setAttribute("user", personne);

			if (personne.getRole().equals("etudiant")) {
				return new RedirectView("/aceuil");

			} else if (personne.getRole().equals("enseignant")) {
				return new RedirectView("/enseignant");

			} else if (personne.getRole().equals("admin")) {
				return new RedirectView("/admin");
			}

		} else {
			pModel.addAttribute("connected", true);
		}

		System.out.println("hello world 22");
		return new RedirectView("/login");

	}

}
