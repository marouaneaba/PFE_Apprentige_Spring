package com.lille1.PFE.ControllerEnseignant;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Service.ExerciceService;
import com.lille1.PFE.sax.SaxHandler;

@Controller
public class ControllerExercice {


	@Autowired
	private ExerciceService mExerciceService;
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	private boolean message = false;

	/**
	 * envoyer un interface à l'enseignant pour ajouter un exercice
	 * @param request nos permet de récuperer les données envoyé par l'utilisateur
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return afficher un interface de saisie de l'exercice à l'utilisateur
	 */
	@RequestMapping(value = "/AjouterExercice", method = RequestMethod.GET)
	public String getPage(HttpServletRequest request, ModelMap pModel) {


		HttpSession session = request.getSession();
		Enseignant enseignant = (Enseignant) session.getAttribute("user");

		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(true));
		List<Connaissance> connaissanceNONValider = mRepositoryConnaissance
				.findConnaissanceByEnseignantAndValider(enseignant, false);
		pModel.addAttribute("connaissancesNonValider", connaissanceNONValider);
		System.out.println("i'm here 22 ");
		if (message) {
			System.out.println("i'm here 22 message : " + message);
			pModel.addAttribute("message", "Veuillez bien saisier l'exercice !!");
			message = false;
		} else {
			pModel.addAttribute("message", "");
		}

		return "AjouterExercice";
	}

	/**
	 * verifier les informations envoyer par l'utilisateur pour ajouter un exercice , nettoyer les doonées envoyer par l'utilisateur
	 * ajouter l'exercice dans la base de données.
	 * @param request request nos permet de récuperer les données envoyé par l'utilisateur : nom d'exercice , ennocer et la solution sous forme XML
	 * @param pModel pModel sur le quel on peux enregistrer des données à l'afficher sur l'interface 
	 * @return aprés la saisie de l'exercice par l'enseignement , un interface de l'acceuil envoyé à l'enseignant
	 */
	@RequestMapping(value = "/AjouterExercice", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public RedirectView recupererExercice(HttpServletRequest request, ModelMap pModel) {

		System.out.println("POST arrivé ");
		
		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		/**/
		String nameExercice = request.getParameter("nomExercice");
		String exercice = request.getParameter("exercice");
		String variable = request.getParameter("variable");
		String codeBrouillon = request.getParameter("code");
		List<String> connaissancesSelected = Arrays.asList(request.getParameter("tab").split(","));

		
		/* enlevé les espace au début et la fin de la chaine */
		exercice = exercice.trim();
		variable = variable.trim();
		codeBrouillon = codeBrouillon
								.trim()
								.replaceAll("\"", "'")
								.replaceAll("'<'","'inf'")
								.replaceAll("'>'","'sup'")
								.replaceAll("'<='","'inf='")
								.replaceAll("'>='","'sup='");
		
		
		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString(codeBrouillon,"code");
		System.out.println("- : "+codeNetoyer+" , fin");
		
		if (nameExercice.equals("") || exercice.equals("") || variable.equals("") || codeBrouillon.equals("")
				|| connaissancesSelected.size() == 0) {
			System.out.println("i'm here ");
			message = true;
			return new RedirectView("/AjouterExercice");

		}
		System.out.println("varibale : ** : "+variable);
		mExerciceService.saveExercice(nameExercice, exercice, variable, codeBrouillon, codeNetoyer,
				connaissancesSelected, personne);


		System.out.println("passé");
		return new RedirectView("/enseignant");
	}

}
