package com.lille1.PFE.ControllerEnseignant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
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

	/**
	 * permet de retourner à l'interface d'acceuil
	 * @return le dashbord interface d'acceuil de l'utilisateur contient tous les fonctionnalité de l'application
	 */
	@GetMapping(value="/")
	public RedirectView forward(){
		return new RedirectView("/dashbord");
	}
	
	/**
	 * permet à l'admin de retourner à l'interface d'acceuil qui contient les fonctionnalité de l'application
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface d'acceuil contient les fonctionnalité de l'application
	 */
	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String GetAdmin(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();
		pModel.addAttribute("user", ((Personne) session.getAttribute("user")).getNom());
		pModel.addAttribute("nombreConnaissanceFalseUser",
				mRepositoryConnaissance.findConnaissanceByValider(false).size());
		return "admin";

	}

	/**
	 * lister tous les administrateur enregister
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return la liste tous les administrateur enregister
	 */
	@RequestMapping(value = "/listAdmin", method = RequestMethod.GET)
	public String listerAdmin(HttpServletRequest request, ModelMap pModel) {

		pModel.addAttribute("consulter", true);

		pModel.addAttribute("admin", mAdminService.getAllAdmin());
		return "listerAdmin";
	}

	/**
	 * interface pour la modification de l'administrateur
	 * @param id ID de Administrateur à modifier
	 * @param pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface pour la modification de l'administrateur
	 */
	@RequestMapping(value = "/listAdmin/update/{id}", method = RequestMethod.GET)
	public String updateAdmin(@PathVariable("id") Long id, Model pModel) {

		pModel.addAttribute("enseignants", mAdminService.getAdminById(id));
		return "modifierEnseignant";
	}

	/**
	 * fournie interface pour la modification d'administrateur
	 * @param id ID de Administrateur à modifier
	 * @param pseudo Pseudo de Administrateur à modifier
	 * @param password Password de Administrateur à modifier
	 * @param email Email de Administrateur à modifier
	 * @return afficher administrateur à modifier
	 */
	@RequestMapping(value = "/listAdmin/update/{id}", method = RequestMethod.POST)
	public RedirectView POSTupdateEtudiant(@PathVariable("id") Long id, @RequestParam("name") String pseudo,
			@RequestParam("password") String password, @RequestParam("email_address") String email) {

		mAdminService.updateAdmin(id, pseudo, password, email);

		return new RedirectView("/listAdmin");
	}

	/**
	 * supprimer un administrateur à partir son ID
	 * @param id id ID de Administrateur à modifier
	 * @return la liste des administrateurs
	 */
	@RequestMapping(value = "/listAdmin/delete/{id}", method = RequestMethod.GET)
	public RedirectView deleteEtudiant(@PathVariable("id") Long id) {

		mAdminService.deleteAdmin(id);
		return new RedirectView("/listAdmin");
	}

}
