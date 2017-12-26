package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;


import com.lille1.PFE.Entity.Admin;
import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryAdmin;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryEtudiant;

@Controller
@RequestMapping("/dashbord")
public class ControllerDashbord {

	

	@Autowired
	private RepositoryAdmin mRepositoryAdmin;
	
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;
	
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	
	
	
	@RequestMapping(method = RequestMethod.GET)
    public RedirectView VerifyLogin(HttpServletRequest request,ModelMap pModel,Principal principal) {
		

		
		Etudiant etudiant = mRepositoryEtudiant.findByNom(principal.getName());
		Enseignant enseignant = mRepositoryEnseignant.findByNom(principal.getName());
		Admin admin = mRepositoryAdmin.findByNom(principal.getName());
		
		HttpSession session = request.getSession();
		Personne personne =null;
		if(etudiant != null){
			personne = (Personne)etudiant;
			session.setAttribute("user", (Personne)etudiant);
			System.out.println("personne : "+(Personne)etudiant);
		}else if(enseignant != null){
			personne = (Personne)enseignant;
			session.setAttribute("user", (Personne)enseignant);
			System.out.println("personne : "+(Personne)enseignant);
		}else if(admin != null){
			personne = (Personne)admin;
			session.setAttribute("user", (Personne)admin);
			System.out.println("personne : "+(Personne)admin);
		}
		
		
		if(personne.getRole().equals("enseignant")){
			return new RedirectView("/enseignant");
		
		}else if(personne.getRole().equals("admin")){
			return new RedirectView("/admin");
		}else{
			return new RedirectView("/aceuil");
		}
        
    }
}
