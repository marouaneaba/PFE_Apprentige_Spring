package com.lille1.PFE.ControllerEnseignant;

import java.util.List;

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

import com.lille1.PFE.Entity.*;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryAdmin;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Service.EnseignantService;
import com.lille1.PFE.Service.ExerciceService;

@Controller

public class ControllerEnseignant {

	@Autowired
	private EnseignantService mEnseignantService;

	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissanec;

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Autowired
	private RepositoryAdmin mRepositoryAdmin;


	@Autowired
	private ExerciceService mExerciceService;

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	private boolean message = false;
	private boolean userExiste = false;

	/**
	 * permet à l'enseignant de sortir à la page d'acceuil
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface contient tous les fonctionnalité de l'admin
	 */
	@RequestMapping(value = "/enseignant", method = RequestMethod.GET)
	public String GetInterfaceEns(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();
		Personne personne = ((Personne) session.getAttribute("user"));
		pModel.addAttribute("user", personne.getNom());
		List<Connaissance> connaissances = mRepositoryConnaissanec.findConnaissanceByValider(false);
		int nbr = 0;
		for (int i = 0; i < connaissances.size(); i++) {
			if (connaissances.get(i).getEnseignant() != null
					&& connaissances.get(i).getEnseignant().getIdEns() == personne.getIdEns())
				nbr++;
		}

		pModel.addAttribute("nombreConnaissanceFalseUser", nbr); // nbr connaissance n'est pas valider par admin.

		List<Exercice> exercices = mExerciceService.getAllExercices();
		int nbrExercice = 0;
		
		for (int i = 0; i < exercices.size(); i++) {
			System.out.println("******");
			if (exercices.get(i).getConnaissance().size() == 0)
				nbrExercice++;
		}
		
		pModel.addAttribute("nombreExerciceZeroConnaissance", nbrExercice); // nbr des exercices n'a aucun connaissance.
		
		return "enseignant";
	}

	/**
	 * ajouter des personne soit etudiant ou Enseignant , admin
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface pour ajouter admin ou etudiant ou enseignant
	 */
	@RequestMapping(value = "/ajouterPersonne", method = RequestMethod.GET)
	public String GetAjoutEns(ModelMap pModel) {

		if (message == true) {
			pModel.addAttribute("message", true);
		} else {
			pModel.addAttribute("message", false);
		}
		if (userExiste == true) {
			pModel.addAttribute("userExiste", true);
		} else {
			pModel.addAttribute("userExiste", false);
		}

		message = false;
		userExiste = false;
		return "ajouterPersonne";
	}

	/**
	 * récuper les données saisie par administrateur pour l'ajout d'un admin, enseignant ou etudiant
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface d'acceuil fournie à l'enseignant d'utiliser tous ces fonctionnalité
	 */
	@RequestMapping(value = "/ajouterPersonne", method = RequestMethod.POST)
	public RedirectView POSTAjoutEns(HttpServletRequest request, ModelMap pModel) {

		String role = request.getParameter("role");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String email = request.getParameter("email_address");

		Enseignant enseignant = mRepositoryEnseignant.findByNome(name);
		Etudiant etudiant = mRepositoryEtudiant.findByNome(name);
		Admin admin = mRepositoryAdmin.findByNome(name);

		if (role == null || name.equals("") || password.equals("") || email.equals("")) {
			message = true;
			return new RedirectView("/ajouterPersonne");
		} else if (enseignant != null || etudiant != null || admin != null) {
			userExiste = true;
			return new RedirectView("/ajouterPersonne");
		}
		if (role.equals("admin")) {
			mRepositoryAdmin.save(new Admin(name, password, email, role));
		} else if (role.equals("enseignant")) {
			mRepositoryEnseignant.save(new Enseignant(name, password, email, role));
		} else if (role.equals("etudiant")) {
			mRepositoryEtudiant.save(new Etudiant(name, password, email, role));
		}

		return new RedirectView("/admin");
	}

	/**
	 * lister tous les enseignement enregistrer
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return return la liste de tous les enseignant enregistrer dans la base
	 */
	@RequestMapping(value = "/listerEns", method = RequestMethod.GET)
	public String ListerEns(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();

		if (((Personne) session.getAttribute("user")).getRole().equals("admin")) {
			pModel.addAttribute("consulter", true);
		} else if (((Personne) session.getAttribute("user")).getRole().equals("enseignant")) {
			pModel.addAttribute("consulter", false);
		}

		pModel.addAttribute("enseignants", mEnseignantService.getAllEnseignant());

		return "/consulterEns";
	}

	/**
	 * supprimer un enseignant à partir son ID
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface
	 * @param id ID d'enseignant à supprimer
	 * @return aprés la suppression affiche tous les enseignents enregister
	 */
	@RequestMapping(value = "/listerEns/delete/{idEns}", method = RequestMethod.GET)
	public RedirectView deleteEns(ModelMap pModel, @PathVariable("idEns") Long id) {

		mEnseignantService.deleteEns(id);
		pModel.addAttribute("enseignants", mEnseignantService.getAllEnseignant());
		return new RedirectView("/listerEns");
	}

	/**
	 * fournie un interface de modifier un enseignant
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface
	 * @param id ID de'enseignent à modifier
	 * @return interface de modifier un enseignant
	 */
	@RequestMapping(value = "/listerEns/update/{idEns}", method = RequestMethod.GET)
	public String updateEns(ModelMap pModel, @PathVariable("idEns") Long id) {

		pModel.addAttribute("enseignants", mEnseignantService.getEnseignantById(id));

		return "/modifierEnseignant";
	}

	/**
	 * interface pour la modification de l'enseignant selectionner
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface
	 * @param id ID de l'enseignant à modifier
	 * @param nom le nouveaux nom qui remplace l'ancien nom de l'enseignant à modifier
	 * @param password le nouveaux password qui remplace l'ancien nom de l'enseignant à modifier
	 * @param email le nouveaux email qui remplace l'ancien nom de l'enseignant à modifier
	 * @return list de tous les enseignants enregistrer
	 */
	@RequestMapping(value = "/listerEns/update/{idEns}", method = RequestMethod.POST)
	public RedirectView POSTupdateEns(ModelMap pModel, @PathVariable("idEns") Long id, @RequestParam("name") String nom,
			@RequestParam("password") String password, @RequestParam("email_address") String email) {

		mEnseignantService.updateEnseignant(id, nom, password, email);
		return new RedirectView("/listerEns");
	}

}
