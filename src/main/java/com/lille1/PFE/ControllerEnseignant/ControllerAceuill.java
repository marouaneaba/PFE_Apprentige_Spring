package com.lille1.PFE.ControllerEnseignant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;

@Controller
@RequestMapping("/aceuil")
public class ControllerAceuill {

	
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;
	
	
	@RequestMapping(method = RequestMethod.GET)
    public String GetAceuill(HttpServletRequest request,ModelMap pModel) {
	
		HttpSession session = request.getSession();
		//Etudiant etudiant = (Etudiant)session.getAttribute("user");
		
		
		Connaissance connaissance = mRepositoryConnaissance.findOne(26L);
		Etudiant etudiant = mRepositoryEtudiant.findOne(((Etudiant)session.getAttribute("user")).getIdEns());
		pModel.addAttribute("user", etudiant.getNom());
		/*etudiant.setConnaissances(connaissance);
		mRepositoryEtudiant.save(etudiant);
		System.out.println("marouane : "+etudiant.getConnaissances());*/
		/*if(personne.getRole().equals("enseignant")){
			return new RedirectView("/enseignant");
		}else if(personne.getRole().equals("admin")){
			return new RedirectView("/admin");
		}else if(personne.getRole().equals("etudiant")){
			return new RedirectView("/apprentisage");
		}
		System.out.println("role : "+personne.getRole());
		return new RedirectView("/logout"); // deconnéxion et return au login*/
		
		return "/aceuil";
    }
}
