package com.lille1.PFE.ControllerEnseignant;

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
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.EtudiantService;

@Controller
public class ControllerEtudiant {

	@Autowired
	private EtudiantService mEtudiantService;
	@Autowired
	private ConnaissanceService mConnaissanceService;

	/**
	 * afficher un interface à administrateur permet d'ajouter un etudiant
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return retounner un interface permet à l'administrateur d'ajouter un etudiant
	 */
	@RequestMapping(value = "/ajoutEtudiant", method = RequestMethod.GET)
	public String GetFormAjoutEtudiant(ModelMap pModel) {

		return "ajoutEtudiant";
	}

	/**
	 * récupérer les données envoyé par administrateur et verifier si le nouveux etudiant existe
	 * et l'enregistrer dans la base de données
	 * @param name le nom de l'étudiant à ajouter saisie par administrateur
	 * @param password le password de l'etudiant choisie par administrateur
	 * @param email email de l'etudiant ajouter par l'administrateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface (si l'etudiant existe déja)
	 * @return aprés la réussite d'enregistrer l'etudiant dans la base de données , l'utisateur reçoit l'interface d'acceuil 
	 */
	@RequestMapping(value = "/ajoutEtudiant", method = RequestMethod.POST)
	public RedirectView setFormAjoutEtudiant(@RequestParam("pseudo") String name,
			@RequestParam("password") String password, @RequestParam("email_address") String email, ModelMap pModel) {


		return new RedirectView("/admin");
	}

	/**
	 * interface permet de lister tous les etudiants enregister.
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param model pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return retourner un interface contient tous les étudiants enregistré.
	 */
	@RequestMapping(value = "/listEtudiant", method = RequestMethod.GET)
	public String listerEtudiant(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		if (((Personne) session.getAttribute("user")).getRole().equals("admin")) {
			model.addAttribute("consulter", true);
		} else if (((Personne) session.getAttribute("user")).getRole().equals("enseignant")) {
			model.addAttribute("consulter", false);
		}

		model.addAttribute("etudiants", mEtudiantService.getAllEtudiant());
		return "listerEtudiants";
	}

	/**
	 * interface permet de modifier un enseignant
	 * @param id id de l'étudiant à modifier
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface contient tout les données pour la modification d'un etudiant
	 */
	@RequestMapping(value = "/listEtudiant/update/{id}", method = RequestMethod.GET)
	public String updateEtudiant(@PathVariable("id") Long id, Model pModel) {

		pModel.addAttribute("enseignants", mEtudiantService.getEtudiantById(id));
		return "modifierEnseignant";
	}

	/**
	 * récupérer les données envoyer par administrateur pour ajouter un nouveuax etudiant
	 * et verifier si l'etudiant déja existe avant chaque ajoute des nouveaux etudiant
	 * @param id id de l'étudiant à modifier
	 * @param pseudo le noveaux pseudo à modifier à l'étudiant 
	 * @param password le noveaux password à modifier à l'étudiant 
	 * @param email le noveaux email à modifier à l'étudiant 
	 * @return interface contient tout les données pour la modification d'un etudiant
	 */
	@RequestMapping(value = "/listEtudiant/update/{id}", method = RequestMethod.POST)
	public RedirectView POSTupdateEtudiant(@PathVariable("id") Long id, @RequestParam("name") String pseudo,
			@RequestParam("password") String password, @RequestParam("email_address") String email) {

		mEtudiantService.updateEtudiant(id, pseudo, password, email);

		return new RedirectView("/listEtudiant");
	}

	/**
	 * permet de supprimer un etudiant à partir son ID
	 * @param id id de l'étudiant a supprimer
	 * @return interface contient tout les données pour la modification d'un étudiant
	 */
	@RequestMapping(value = "/listEtudiant/delete/{id}", method = RequestMethod.GET)
	public RedirectView deleteEtudiant(@PathVariable("id") Long id) {

		mEtudiantService.deleteEtudiantById(id);
		return new RedirectView("/listEtudiant");
	}

	/**
	 * permet d'aficher tous les exercices avec ces connaissance
	 * @param idCon id de connaissance à afficher
	 * @param idExercice id exercice à afficher 
	 * @param pModel Model pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return retourner un interface contient tous les Connaissances avec leurs exerice.
	 */
	@RequestMapping(value = "/viewConnaissance/view/{id}/{idExercice}", method = RequestMethod.GET)
	public String ViewConnaissance(@PathVariable("id") Long idCon, @PathVariable("idExercice") Long idExercice,
			ModelMap pModel) {

		pModel.addAttribute("connaissances", mConnaissanceService.getConnaissances(idCon));
		pModel.addAttribute("idExercice", idExercice);
		return "/viewConnaissance";
	}

}
