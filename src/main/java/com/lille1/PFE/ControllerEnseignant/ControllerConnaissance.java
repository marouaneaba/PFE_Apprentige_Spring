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
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryHistory;
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
	
	@Autowired
	private RepositoryHistory mRepositoryHistory;
	
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	private String message= "";
	boolean trouver = false;
	
	/**
	 * interface permet d'ajouter une nouveux connaissance
	 * @param pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return interface sur le quel on saisie la connaissance à ajouter
	 */
	@RequestMapping(value = "/Ajoutconnaissance", method = RequestMethod.GET)
	public String getInterfaceAjoutConnaissance(ModelMap pModel) {

			pModel.addAttribute("message", message);
			pModel.addAttribute("addConnaissance", trouver);
			trouver = false;
			message ="";
		
		return "connaissance";
	}

	/**
	 * 
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param nom nom de connaissance à ajouter
	 * @param ordreS ordre de la connaissance a ajouter
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return si la connaissance et bien ajoute retourne interface d'acceuil sinon interface d'ajout pour la resaisir de la connaissance
	 */
	@RequestMapping(value = "/Ajoutconnaissance", method = RequestMethod.POST)
	public RedirectView ajoutConnaissance(HttpServletRequest request, @RequestParam("nom") String nom,
			@RequestParam("ordre") String ordreS, ModelMap pModel) {

		HttpSession session = request.getSession();
		message = "";
		List<Connaissance> connaissances = mConnaissanceService
				.convertIterableToList(mRepositoryConnaissance.findAll());
		for (int i = 0; i < connaissances.size(); i++) {
			if (nom.replaceAll(" ", "").equals(connaissances.get(i).getNom().replaceAll(" ", ""))) {
				trouver = true;
			}
		}
		
		int ordre;
		try{
			ordre = Integer.parseInt(ordreS);
		}catch(NumberFormatException e){
			message = "Veuillez bien remplir les champs !! ";
			return new RedirectView("/Ajoutconnaissance");
		}
		
		if (!trouver && ((Personne) session.getAttribute("user")).getRole().equals("enseignant")) {

			Connaissance co = new Connaissance(nom, ordre, false);
			mRepositoryConnaissance.save(co);
			co.setEnseignant(mRepositoryEnseignant.findOne(((Personne) session.getAttribute("user")).getIdEns()));
			mRepositoryEnseignant
					.save(mRepositoryEnseignant.findOne(((Personne) session.getAttribute("user")).getIdEns()));
			System.out.println("con : " + co);
			sessionGlobal.addConnaissance(co);
			return new RedirectView("/enseignant");
		} else if (!trouver && ((Personne) session.getAttribute("user")).getRole().equals("admin")) {

			mRepositoryConnaissance.save(new Connaissance(nom, ordre, true));
			return new RedirectView("/admin");
		} else {
			pModel.addAttribute("addConnaissance", trouver);
			System.out.println("trouver : "+trouver);
			return new RedirectView("/Ajoutconnaissance");
		}

	}

	/**
	 * 
	 * @param request
	 * @param pModel
	 * @return
	 */
	@RequestMapping(value = "/consultConnaissance", method = RequestMethod.GET) // les connaissance non valider par admin
	public String consulterConnaissances(HttpServletRequest request, Model pModel) {

		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		if (personne.getRole().equals("admin")) {
			pModel.addAttribute("consulter", false);
		} else if (personne.getRole().equals("enseignant")) {
			pModel.addAttribute("consulter", true);
		}
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

	/**
	 * 
	 * @param request
	 * @param pModel
	 * @return
	 */
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

		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(true));

		return "ConsulterConnaissances";
	}

	/**
	 * 
	 * @param request
	 * @param pModel
	 * @return
	 */
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

		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(false));

		return "ConsulterConnaissances";
	}

	/**
	 * 
	 * @param pModel
	 * @param idCon
	 * @return
	 */
	@RequestMapping(value = "/consultConnaissance/delete/{id_ExEtu}", method = RequestMethod.GET)
	public RedirectView supprimerConnaissances(Model pModel, @PathVariable("id_ExEtu") Long idCon) {
		
		List<History> historys =  mRepositoryHistory.findByConnaissance(mRepositoryConnaissance.findOne(idCon));
		
		mRepositoryHistory.delete(historys);
		
		List<Exercice> exercices = mRepositoryExercice.findByConnaissance(mRepositoryConnaissance.findOne(idCon));
		for(int i=0;i<exercices.size();i++){
			exercices.get(i).getConnaissance().clear();
		}
		mRepositoryExercice.save(exercices);
		
		mConnaissanceService.supprimerConnaissances(idCon);
		sessionGlobal.deleteConnaissance(idCon);
		pModel.addAttribute("connaissances", mConnaissanceService.getAllConnaissance());
		return new RedirectView("/dashbord");
	}

	/**
	 * 
	 * @param pModel
	 * @param idCon
	 * @return
	 */
	@RequestMapping(value = "/consultConnaissance/update/{id_ExEtu}", method = RequestMethod.GET)
	public String modifierConnaissances(Model pModel, @PathVariable("id_ExEtu") Long idCon) {

		pModel.addAttribute("connaissances", mConnaissanceService.getConnaissances(idCon));

		return "modifierConnaissance";
	}

	/**
	 * 
	 * @param idCon
	 * @param nom
	 * @param ordre
	 * @param pModel
	 * @return
	 */
	@RequestMapping(value = "/consultConnaissance/update/{id_ExEtu}", method = RequestMethod.POST)
	public RedirectView PostmodifierConnaissances(@PathVariable("id_ExEtu") Long idCon, @RequestParam("nom") String nom,
			@RequestParam("ordre") int ordre, ModelMap pModel) {

		mConnaissanceService.updateConnaissance(idCon, nom, ordre);
		sessionGlobal.updateConnaissance(idCon, nom, ordre);
		pModel.addAttribute("connaissances", mConnaissanceService.getAllConnaissance());

		
		return new RedirectView("/dashbord");
	}

	/**
	 * 
	 * @param idCon
	 * @param pModel
	 * @return
	 */
	@RequestMapping(value = "/consultConnaissance/valider/{id_ExEtu}", method = RequestMethod.GET)
	public RedirectView ValiderConnaissanceAdmin(@PathVariable("id_ExEtu") Long idCon, ModelMap pModel) {

		mConnaissanceService.updateConnaissanceToTrue(idCon);
		sessionGlobal.deleteConnaissance(idCon);
		return new RedirectView("/validerConnaissance");
	}

	/**
	 * 
	 * @param idCon
	 * @param idExercice
	 * @param pModel
	 * @return
	 */
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
		exercice.setConnaissance(connaissances);
		mRepositoryExercice.save(exercice);

		System.out.println("marouane abakarim");
		return new RedirectView("/consulterExercice");
	}
}
