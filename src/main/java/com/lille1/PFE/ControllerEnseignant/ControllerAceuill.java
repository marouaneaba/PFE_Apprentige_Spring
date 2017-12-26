package com.lille1.PFE.ControllerEnseignant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lille1.PFE.Entity.Personne;

@Controller
@RequestMapping("/aceuil")
public class ControllerAceuill {

	
	@RequestMapping(method = RequestMethod.GET)
    public String GetAceuill(HttpServletRequest request,ModelMap pModel) {
	
		HttpSession session = request.getSession();
		Personne personne = (Personne)session.getAttribute("user");
		pModel.addAttribute("user", personne.getNom());
		
		if(personne.getRole().equals("enseignant")){
			return "/enseignant";
		}else if(personne.getRole().equals("admin")){
			return "/admin";
		}
		
		return "aceuil";
        
    }
}
