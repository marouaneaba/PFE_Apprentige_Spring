package com.lille1.PFE.ControllerEnseignant;

import java.util.ArrayList;
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
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.ExerciceService;
import com.lille1.PFE.sax.SaxHandler;

@Controller
public class ControllerExercice {

	@Autowired
	private ConnaissanceService mConnaissanceService;

	@Autowired
	private ExerciceService mExerciceService;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Resource(name = "globalSessionMessage")
	ClassScope sessionGlobal;

	private boolean message = false;

	@RequestMapping(value = "/AjouterExercice", method = RequestMethod.GET)
	public String getPage(HttpServletRequest request, ModelMap pModel) {

		/*
		 * List<Connaissance> connaissances =
		 * mConnaissanceService.getAllConnaissance();
		 */
		HttpSession session = request.getSession();
		Enseignant enseignant = (Enseignant) session.getAttribute("user");

		pModel.addAttribute("connaissances", mRepositoryConnaissance.findConnaissanceByValider(true));
		List<Connaissance> connaissanceNONValider = mRepositoryConnaissance
				.findConnaissanceByEnseignantAndValider(enseignant, false);
		pModel.addAttribute("connaissancesNonValider", connaissanceNONValider);
		// pModel.addAttribute("connaissancesNonValider",sessionGlobal.getConnaissance());
		System.out.println("i'm here 22 ");
		/*if (message) {
			System.out.println("i'm here 22 message : " + message);
			pModel.addAttribute("message", "hhhhh");
			message = false;
		} else {
			pModel.addAttribute("message", "");
		}*/

		return "AjouterExercice";
	}

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
		
		System.out.println("- : "+codeBrouillon);
		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString(codeBrouillon,"code");
		/*codeNetoyer.replaceAll("'inf'","'<'")
				   .replaceAll("'sup'","'>'");
		System.out.println("karim : "+codeNetoyer);*/
		if (nameExercice.equals("") || exercice.equals("") || variable.equals("") || codeBrouillon.equals("")
				|| connaissancesSelected.size() == 0) {
			System.out.println("i'm here ");
			message = true;
			return new RedirectView("/AjouterExercice");

		}

		mExerciceService.saveExercice(nameExercice, exercice, variable, codeBrouillon, codeNetoyer,
				connaissancesSelected, personne);

		/*
		 * System.out.println("post4 : "+new SaxHandler().parserString(
		 * codeBrouillon)); System.out.println("post1 : "+nameExercice);
		 * System.out.println("post2 : "+exercice);
		 * System.out.println("post3 : "+variable); for(int
		 * i=0;i<connaissancesSelected.size();i++){
		 * System.out.println("post3-3 : "+connaissancesSelected.get(i)); }
		 */

		/*
		 * mExerciceService.addExerciceRepository(nameExercice,exercice,
		 * codeBrouillon ,codeNetoyer,null,null,personne);
		 */
		/*
		 * List<Connaissance> connaissances = new ArrayList<>();
		 * connaissances.add(new Connaissance("nom1","ordre1","score1",true));
		 * connaissances.add(new Connaissance("nom2","ordre2","score2",true));
		 * mExerciceService.addExerciceEnseigantRepository(nameExercice,exercice
		 * ,codeBrouillon
		 * ,codeNetoyer,connaissances,null,personne);//connaissance,variable
		 * System.out.println("----: "+mRepositoryEnseignant.findOne(personne.
		 * getIdEns())); System.out.println("arivé terminer fin ligne ");
		 * pModel.addAttribute("connaissances",mConnaissanceService.
		 * getAllConnaissance());
		 */
		System.out.println("passé");
		return new RedirectView("/enseignant");
	}

}
