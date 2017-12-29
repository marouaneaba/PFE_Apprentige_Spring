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
public class ControllerLogin{

	/*@Autowired
	private EtudiantService mEtudiantService;
	
	@Autowired
	private EnseignantService mEnseignantService;
	
	@Autowired
	private RepositoryAdmin mRepositoryAdmin;*/
	
	@Autowired
	private RepositoryPersonne mRepositoryPersonne;
	
	
	
	
	
	@RequestMapping(method = RequestMethod.GET)
    public String GetLogin(Principal principal,ModelMap pModel,@PathParam(value="logout") String logout, @PathParam("error") String error ) {
	
		pModel.addAttribute("logout", logout);
		pModel.addAttribute("error", error);
		String result = logout + error;
		System.out.println("hello world GET : "+result);
		if(principal != null)
			System.out.println("Principal : "+principal.getName());
		return "login";
        
    }
	
	@RequestMapping(method = RequestMethod.POST)
    public RedirectView VerifyLogin(HttpServletRequest request,@RequestParam("username") String pseudo,
    					@RequestParam("password") String password, ModelMap pModel) {
		
		
		HttpSession session = request.getSession();
		
		/*Etudiant etudiant = mEtudiantService.VerifyEtudiant(pseudo, password);
		Enseignant enseignant = mEnseignantService.VerifyEnseignant(pseudo, password);
		Admin admin = 	mRepositoryAdmin.findAdminByNomAndPassword(pseudo,password);*/
		Personne personne = mRepositoryPersonne.findByNomAndPassword(pseudo, password);
		
		
		if(personne != null && personne.getNom().equals(pseudo) && personne.getPassword().equals(password)){
			System.out.println(personne);
			session.setAttribute("user",personne);
			
			if(personne.getRole().equals("etudiant")){
				return new RedirectView("/aceuil");
			
			}else if(personne.getRole().equals("enseignant")){
				return new RedirectView("/enseignant");
			
			}else if(personne.getRole().equals("admin")){
				return new RedirectView("/admin");
			}
			
		}else{
			pModel.addAttribute("connected",true);
		}
		
		System.out.println("hello world 22");
		return new RedirectView("/login");
        
    }
	
}
