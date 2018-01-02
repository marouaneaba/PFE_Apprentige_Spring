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

import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.EtudiantService;

@Controller
public class ControllerEtudiant {

	@Autowired
	private EtudiantService mEtudiantService;
	@Autowired
	private ConnaissanceService mConnaissanceService;

	@RequestMapping(value = "/ajoutEtudiant", method = RequestMethod.GET)
	public String GetFormAjoutEtudiant(ModelMap pModel) {

		return "ajoutEtudiant";
	}

	@RequestMapping(value = "/ajoutEtudiant", method = RequestMethod.POST)
	public RedirectView setFormAjoutEtudiant(@RequestParam("pseudo") String name,
			@RequestParam("password") String password, @RequestParam("email_address") String email, ModelMap pModel) {

		Etudiant etudiant = mEtudiantService.addEtudiant(name, password, email, "etudiant");

		return new RedirectView("/admin");
	}

	@RequestMapping(value = "/listEtudiant", method = RequestMethod.GET)
	public String listerEtudiant(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession();

		if (((Personne) session.getAttribute("user")).getRole().equals("admin")) {
			model.addAttribute("consulter", true);
		} else if (((Personne) session.getAttribute("user")).getRole().equals("enseignant")) {
			model.addAttribute("consulter", false);
		}

		model.addAttribute("etudiants", mEtudiantService.getAllEtudiant());
		/*
		 * List<Etudiant> etudiants =
		 * mEtudiantService.convertIterableToList(mEtudiantService.
		 * getAllEtudiant()); for(Etudiant etudiant : etudiants){
		 * System.out.println("etu : "+etudiant+
		 * ", size connaissance :"+etudiant.getConnaissances().size()); }
		 */
		return "listerEtudiants";
	}

	@RequestMapping(value = "/listEtudiant/update/{id}", method = RequestMethod.GET)
	public String updateEtudiant(@PathVariable("id") Long id, Model pModel) {

		pModel.addAttribute("enseignants", mEtudiantService.getEtudiantById(id));
		return "modifierEnseignant";
	}

	@RequestMapping(value = "/listEtudiant/update/{id}", method = RequestMethod.POST)
	public RedirectView POSTupdateEtudiant(@PathVariable("id") Long id, @RequestParam("name") String pseudo,
			@RequestParam("password") String password, @RequestParam("email_address") String email) {

		mEtudiantService.updateEtudiant(id, pseudo, password, email);

		return new RedirectView("/listEtudiant");
	}

	@RequestMapping(value = "/listEtudiant/delete/{id}", method = RequestMethod.GET)
	public RedirectView deleteEtudiant(@PathVariable("id") Long id) {

		mEtudiantService.deleteEtudiantById(id);
		return new RedirectView("/listEtudiant");
	}

	@RequestMapping(value = "/viewConnaissance/view/{id}/{idExercice}", method = RequestMethod.GET)
	public String ViewConnaissance(@PathVariable("id") Long idCon, @PathVariable("idExercice") Long idExercice,
			ModelMap pModel) {

		pModel.addAttribute("connaissances", mConnaissanceService.getConnaissances(idCon));
		pModel.addAttribute("idExercice", idExercice);
		return "/viewConnaissance";
	}

}
