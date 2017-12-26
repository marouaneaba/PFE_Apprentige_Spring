package com.lille1.PFE.ControllerEnseignant;


import java.security.Principal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lille1.PFE.MainController;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;



@Controller
@RequestMapping("/admin")
public class ControllerAdmin extends MainController{

	@Resource(name="globalSessionMessage")
	ClassScope sessionGlobal;
	
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	
	
	@RequestMapping(method = RequestMethod.GET)
    public String GetAdmin(HttpServletRequest request,ModelMap pModel) {
	
		HttpSession session = request.getSession();
		pModel.addAttribute("user", ((Personne)session.getAttribute("user")).getNom());
		pModel.addAttribute("nombreConnaissanceFalseUser", mRepositoryConnaissance.findConnaissanceByValider(false));
		return "admin";
        
    }
}
