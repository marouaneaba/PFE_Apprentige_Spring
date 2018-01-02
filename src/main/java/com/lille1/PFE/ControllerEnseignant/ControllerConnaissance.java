package com.lille1.PFE.ControllerEnseignant;

import java.util.ArrayList;
import java.util.List;

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

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Service.ConnaissanceService;

@Controller
public class ControllerConnaissance {

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Autowired
	private ConnaissanceService mConnaissanceService;

	@Autowired
	private RepositoryExercice mRepositoryExercice;

	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	@RequestMapping(value = "/Ajoutconnaissance", method = RequestMethod.GET)
	public String getInterfaceAjoutConnaissance(ModelMap pModel) {

		return "connaissance";
	}

	@RequestMapping(value = "/Ajoutconnaissance", method = RequestMethod.POST)
	public RedirectView ajoutConnaissance(HttpServletRequest request, @RequestParam("nom") String nom,
			@RequestParam("ordre") int ordre, ModelMap pModel) {

		HttpSession session = request.getSession();

		List<Connaissance> connaissanceNonValide = new ArrayList<>();
		List<Connaissance> connaissances = mConnaissanceService
				.convertIterableToList(mRepositoryConnaissance.findAll());
		boolean trouver = true;
		for (int i = 0; i < connaissances.size(); i++) {
			if (nom.replaceAll(" ", "").equals(connaissances.get(i).getNom().replaceAll(" ", ""))) {
				trouver = false;
			}
		}

		if (trouver && ((Personne) session.getAttribute("user")).getRole().equals("enseignant")) {

			Connaissance co = new Connaissance(nom, ordre, false);
			mRepositoryConnaissance.save(co);
			co.setEnseignant(mRepositoryEnseignant.findOne(((Personne) session.getAttribute("user")).getIdEns()));
			mRepositoryEnseignant
					.save(mRepositoryEnseignant.findOne(((Personne) session.getAttribute("user")).getIdEns()));
			System.out.println("con : " + co);
			sessionGlobal.addConnaissance(co);
			return new RedirectView("/enseignant");
		} else if (trouver && ((Personne) session.getAttribute("user")).getRole().equals("admin")) {

			mRepositoryConnaissance.save(new Connaissance(nom, ordre, true));
			return new RedirectView("/admin");
		} else {
			pModel.addAttribute("addConnaissance", trouver);
			return new RedirectView("/Ajoutconnaissance");
		}

	}

	@RequestMapping(value = "/consultConnaissance", method = RequestMethod.GET) // les
																				// connaissance
																				// non
																				// valider
																				// par
																				// admin
	public String consulterConnaissances(HttpServletRequest request, Model pModel) {

		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		if (personne.getRole().equals("admin")) {
			pModel.addAttribute("consulter", false);
		} else if (personne.getRole().equals("enseignant")) {
			pModel.addAttribute("consulter", true);
		}
		// Enseignant enseignant =
		// mEnseignantService.getEnseignantById(personne.getIdEns());
		// pModel.addAttribute("connaissances",mEnseignantService.getAllConnaissaneEnseignantById(enseignant.getIdEns()));
		List<Connaissance> Allconnaissances = mRepositoryConnaissance.findConnaissanceByValider(false); // sessionGlobal.getConnaissance();
		List<Connaissance> connaissances = new ArrayList<>();
		for (int i = 0; i < Allconnaissances.size(); i++) {
			if (Allconnaissances.get(i).getEnseignant() != null)
				System.out.println(Allconnaissances.get(i).getEnseignant().getIdEns() + " , " + personne.getIdEns());
			if (Allconnaissances.get(i).getEnseignant() != null
					&& Allconnaissances.get(i).getEnseignant().getIdEns() == personne.getIdEns()) {
				connaissances.add(Allconnaissances.get(i));
			}

		}
		System.out.println("teste : " + connaissances);
		pModel.addAttribute("connaissances", connaissances);

		return "ConsulterConnaissances";
	}

	@RequestMapping(value = "/consultAllConnaissance", method = RequestMethod.GET)
	public String consulterAllConnaissances(HttpServletRequest request, Model pModel) {

		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		if (personne.getRole().equals("admin")) {
			pModel.addAttribute("Affvalider", false);
			pModel.addAttribute("consulter", true);
		} else if (personne.getRole().equals("enseignant")) {
			pModel.addAttribute("Affvalider", false);
			pModel.addAttribute("consulter", false);
		}

		// Enseignant enseignant =
		// mEnseignantService.getEnseignantById(personne.getIdEns());
		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(true));

		return "ConsulterConnaissances";
	}

	@RequestMapping(value = "/validerConnaissance", method = RequestMethod.GET)
	public String consulterConnaissancesNonValider(HttpServletRequest request, Model pModel) {

		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		if (personne.getRole().equals("admin")) {
			pModel.addAttribute("Affvalider", true);
			pModel.addAttribute("consulter", true);
		} else if (personne.getRole().equals("enseignant")) {
			pModel.addAttribute("Affvalider", false);
			pModel.addAttribute("consulter", false);
		}

		// Enseignant enseignant =
		// mEnseignantService.getEnseignantById(personne.getIdEns());
		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(false));

		return "ConsulterConnaissances";
	}

	@RequestMapping(value = "/consultConnaissance/delete/{id_ExEtu}", method = RequestMethod.GET)
	public RedirectView supprimerConnaissances(Model pModel, @PathVariable("id_ExEtu") Long idCon) {

		mConnaissanceService.supprimerConnaissances(idCon);
		sessionGlobal.deleteConnaissance(idCon);
		pModel.addAttribute("connaissances", mConnaissanceService.getAllConnaissance());
		// return new RedirectView("/consultConnaissance");
		return new RedirectView("/dashbord");
	}

	@RequestMapping(value = "/consultConnaissance/update/{id_ExEtu}", method = RequestMethod.GET)
	public String modifierConnaissances(Model pModel, @PathVariable("id_ExEtu") Long idCon) {

		pModel.addAttribute("connaissances", mConnaissanceService.getConnaissances(idCon));

		return "modifierConnaissance";
	}

	@RequestMapping(value = "/consultConnaissance/update/{id_ExEtu}", method = RequestMethod.POST)
	public RedirectView PostmodifierConnaissances(@PathVariable("id_ExEtu") Long idCon, @RequestParam("nom") String nom,
			@RequestParam("ordre") int ordre, ModelMap pModel) {

		mConnaissanceService.updateConnaissance(idCon, nom, ordre);
		sessionGlobal.updateConnaissance(idCon, nom, ordre);
		pModel.addAttribute("connaissances", mConnaissanceService.getAllConnaissance());

		// return new RedirectView("/consultConnaissance");
		return new RedirectView("/dashbord");
	}

	@RequestMapping(value = "/consultConnaissance/valider/{id_ExEtu}", method = RequestMethod.GET)
	public RedirectView ValiderConnaissanceAdmin(@PathVariable("id_ExEtu") Long idCon, ModelMap pModel) {

		mConnaissanceService.updateConnaissanceToTrue(idCon);
		sessionGlobal.deleteConnaissance(idCon);
		return new RedirectView("/validerConnaissance");
	}

	@RequestMapping(value = "/consultConnaissance/deleteOfExercice/{id_ExEtu}/{idExercice}", method = RequestMethod.GET)
	public RedirectView SuuprimerConnaissanceOfExercice(@PathVariable("id_ExEtu") Long idCon,
			@PathVariable("idExercice") Long idExercice, ModelMap pModel) {

		System.out.println("idconnaissance : " + idCon + " ,idExercice : " + idExercice);
		Exercice exercice = mRepositoryExercice.findOne(idExercice);
		List<Connaissance> connaissances = exercice.getConnaissance();

		for (int i = 0; i < connaissances.size(); i++) {
			if (connaissances.get(i).getId_ExEtu() == idCon) {
				connaissances.remove(i);
				break;
			}
		}
		// exercice.getConnaissance().clear();
		// exercice.setConnaissance(null);
		exercice.setConnaissance(connaissances);
		mRepositoryExercice.save(exercice);

		System.out.println("marouane abakarim");
		return new RedirectView("/consulterExercice");
	}
}
