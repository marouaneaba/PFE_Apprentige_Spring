package com.lille1.PFE.ControllerEnseignant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.lille1.PFE.Service.EnseignantService;
import com.lille1.PFE.Service.EtudiantService;

@Controller
@RequestMapping("/deconnexion")
public class ControllerDeconexion {
	
	
	
	
	@RequestMapping()
	public String deconnexion(ModelMap pModel){
		pModel.addAttribute("login", "");
		
		return "login";
	}

}
