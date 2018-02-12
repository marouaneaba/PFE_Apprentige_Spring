package com.lille1.PFE.ControllerEnseignant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Service.AdminService;
import com.lille1.PFE.security.MainController;

@Controller
public class ControllerAdmin extends MainController {

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Autowired
	private AdminService mAdminService;

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String GetAdmin(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();
		pModel.addAttribute("user", ((Personne) session.getAttribute("user")).getNom());
		pModel.addAttribute("nombreConnaissanceFalseUser",
				mRepositoryConnaissance.findConnaissanceByValider(false).size());
		return "admin";

	}

	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public String listerAdmin(HttpServletRequest request, ModelMap pModel) {

		pModel.addAttribute("consulter", true);

		pModel.addAttribute("admin", mAdminService.getAllAdmin());
		return "listerAdmin";
	}

	@RequestMapping(value = "/listAdmin/update/{id}", method = RequestMethod.GET)
	public String updateAdmin(@PathVariable("id") Long id, Model pModel) {

		pModel.addAttribute("enseignants", mAdminService.getAdminById(id));
		return "modifierEnseignant";
	}

	@RequestMapping(value = "/listAdmin/update/{id}", method = RequestMethod.POST)
	public RedirectView POSTupdateEtudiant(@PathVariable("id") Long id, @RequestParam("name") String pseudo,
			@RequestParam("password") String password, @RequestParam("email_address") String email) {

		mAdminService.updateAdmin(id, pseudo, password, email);

		return new RedirectView("/listAdmin");
	}

	@RequestMapping(value = "/listAdmin/delete/{id}", method = RequestMethod.GET)
	public RedirectView deleteEtudiant(@PathVariable("id") Long id) {

		mAdminService.deleteAdmin(id);
		return new RedirectView("/listAdmin");
	}

}
