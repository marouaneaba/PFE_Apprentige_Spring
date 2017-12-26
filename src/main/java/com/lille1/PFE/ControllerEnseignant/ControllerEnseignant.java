package com.lille1.PFE.ControllerEnseignant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Service.EnseignantService;

@Controller

public class ControllerEnseignant {

	
	@Autowired
	private EnseignantService mEnseignantService;
	
	@Resource(name="globalSessionMessage")
	ClassScope sessionGlobal;
	
	
	@RequestMapping(value="/enseignant",method = RequestMethod.GET)
    public String GetInterfaceEns(HttpServletRequest request,ModelMap pModel) {
		
		HttpSession session = request.getSession();
		pModel.addAttribute("user", ((Personne)session.getAttribute("user")).getNom());
		pModel.addAttribute("nombreConnaissanceFalseUser",sessionGlobal.getConnaissance().size() );
		return "enseignant";
    }
	
	@RequestMapping(value="/ajouterEns",method = RequestMethod.GET)
    public String GetAjoutEns(ModelMap pModel) {
		
		return "ajouterEns";
    }
	
	@RequestMapping(value="/ajouterEns",method = RequestMethod.POST)
    public RedirectView  POSTAjoutEns(ModelMap pModel,@RequestParam("name") String name,@RequestParam("password") String password
    		,@RequestParam("email_address") String email) {
	
		mEnseignantService.setEnseignant(name,password,email,"enseignant");
		
		 return new RedirectView("/admin");
    }
	
	@RequestMapping(value="/listerEns",method = RequestMethod.GET)
    public String ListerEns(HttpServletRequest request,ModelMap pModel) {
	
		HttpSession session = request.getSession();
		
		if(((Personne)session.getAttribute("user")).getRole().equals("admin")){
			pModel.addAttribute("consulter", true);
		}else if(((Personne)session.getAttribute("user")).getRole().equals("enseignant")){
			pModel.addAttribute("consulter", false);
		}
		
		pModel.addAttribute("enseignants", mEnseignantService.getAllEnseignant());
		
		 return "/consulterEns";
    }
	
	@RequestMapping(value="/listerEns/delete/{idEns}",method = RequestMethod.GET)
    public RedirectView deleteEns(ModelMap pModel,@PathVariable("idEns") Long id) {
	
		mEnseignantService.deleteEns(id);
		pModel.addAttribute("enseignants", mEnseignantService.getAllEnseignant());
		return new RedirectView("/listerEns");
    }
	/**/
	@RequestMapping(value="/listerEns/update/{idEns}",method = RequestMethod.GET)
    public String updateEns(ModelMap pModel,@PathVariable("idEns") Long id) {
	
		pModel.addAttribute("enseignants", mEnseignantService.getEnseignantById(id));
		
		 return "/modifierEnseignant";
    }
	
	@RequestMapping(value="/listerEns/update/{idEns}",method = RequestMethod.POST)
    public RedirectView POSTupdateEns(ModelMap pModel,@PathVariable("idEns") Long id,@RequestParam("name")String nom,
    		@RequestParam("password") String password,@RequestParam("email_address")String email) {
	
		mEnseignantService.updateEnseignant(id, nom, password, email);
		return new RedirectView("/listerEns");
    }
	
	
	
}
