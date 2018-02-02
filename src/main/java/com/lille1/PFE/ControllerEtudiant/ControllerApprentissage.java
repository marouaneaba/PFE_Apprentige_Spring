package com.lille1.PFE.ControllerEtudiant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryHistory;
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.ExerciceService;
import com.lille1.PFE.sax.SaxHandler;

@Controller
public class ControllerApprentissage {

	@Autowired
	private RepositoryExercice mRepositoryExercice;

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Autowired
	private ConnaissanceService mConnaissanceService;
	
	@Autowired
	private RepositoryHistory mRepositoryHistory;

	@Autowired
	private ExerciceService mExerciceService;
	
	int niveaux;
	private Connaissance connaissanceREPLY;
	private Exercice ExerciceREPLY;

	@RequestMapping(value = "/apprentisage", method = RequestMethod.GET)
	public String GetAceuill(HttpServletRequest request, ModelMap pModel) {
		
		pModel.addAttribute("etudiant",true);
		
		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		Etudiant etudiant = mRepositoryEtudiant.findOne(personne.getIdEns());

		session.removeAttribute("result");

		/* connaissance lié à l'etudiant */
		List<Connaissance> connaissancesEtudiant = mRepositoryEtudiant.findConnaissaceByEtudiant(etudiant.getIdEns());
		/*
		 * connaissance tout les connaissances enregistrée dans notre base de
		 * données
		 */
		List<Connaissance> connaissancesNonEtudiant = mConnaissanceService.getAllConnaissance();
		/* les connaissances n'est pas liée à l'etudiant */
		connaissancesNonEtudiant.removeAll(connaissancesEtudiant);
		// trié Attrubt Ordre d'etity Connaissance dans l'ordre coissante.
		Comparator<Connaissance> comparator = (x, y) -> (x.getOrdre() > y.getOrdre()) ? 1
				: ((x.getOrdre() == y.getOrdre()) ? 0 : -1);
		connaissancesNonEtudiant.sort(comparator);
		
		List<Exercice> exercicees = null;
		for(int i=0;i<connaissancesNonEtudiant.size();i++){
			
			if(mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)) != null 
					&& mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)).size()>0){
				exercicees = mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i));
				connaissanceREPLY = connaissancesNonEtudiant.get(i);
				break;
			}else if(mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)) == null 
					&& mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)).size() == 0){
				//arréter tous les connaissance et fait.
			}
		}
		//List<Exercice> exercices = mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant);
		List<History> history = mRepositoryHistory.findByScoreAndEtudiant(1,etudiant);
		List<Exercice> exercices1ByEtudiant = new ArrayList<Exercice>();
		for(int i=0;i<history.size();i++){
			exercices1ByEtudiant.add(history.get(i).getExercice());
		}
		exercicees.removeAll(exercices1ByEtudiant);
		
		
		//connaissanceREPLY = connaissancesNonEtudiant.get(0);
		//niveaux = connaissancesNonEtudiant.get(0).getOrdre();
		//System.out.println("niveaux : " + niveaux);
		// peux etre aucun exercice n'est liée avec se exercice.
		//List<Exercice> exercice = mRepositoryExercice.findByConnaissanceOrdre(niveaux);

		if (connaissancesNonEtudiant == null || connaissancesNonEtudiant.size() == 0 || exercicees.size() == 0) {
			session.setAttribute("result", "finie");
			System.out.println("finie , vous avez passé tout les connaissances où "
					+ "exercice de cette connaissance n'est pas encore ajouté ");
			// afficher au etudiant , vous avez réussi tout les niveaux liée a
			// nos exercice ,
			// Veuillez Attendre la mise à jour des nouvelle connaissances à
			// faire.
		} else {
			// niveaux = connaissancesNonEtudiant.get(0).getOrdre();
			// System.out.println("niveaux : " +niveaux);
			// List<Exercice> exercice =
			// mRepositoryExercice.findByConnaissanceOrdre(niveaux);
			System.out.println("exercice by Ordre : " + exercicees);
			Collections.shuffle(exercicees);
			System.out.println("exercice by Ordre : " + exercicees);
			ExerciceREPLY = exercicees.get(0);
			pModel.addAttribute("exercice", exercicees.get(0));
		}

		return "apprentissage";
	}

	@RequestMapping(value = "/apprentisage", method = RequestMethod.POST)
	public RedirectView POSTApprentissage(HttpServletRequest request, ModelMap pModel) {

		System.out.println("POST arrivé");

		HttpSession session = request.getSession();

		String code = request.getParameter("code");
		System.out.println("code : " + code);

		//code = code.trim().replaceAll("\"", "'");
		code = code
				.trim()
				.replaceAll("\"", "'")
				.replaceAll("'<'","'inf'")
				.replaceAll("'>'","'sup'")
				.replaceAll("'<='","'inf='")
				.replaceAll("'>='","'sup='");
		System.out.println("code : " + code);

		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString(code,"code");
		System.out.println("- : "+codeNetoyer+" , fin");
		if (code.equals("")) {
			System.out.println("vous avez rien saisier comme code ");
		}

		System.out.println("code Netoyer : " + codeNetoyer);
		Etudiant etudiant = mRepositoryEtudiant.findOne(((Etudiant) session.getAttribute("user")).getIdEns());
		
		if (mExerciceService.ExerciceComparTo(codeNetoyer, ExerciceREPLY.getXMLSolutionNettoyer())) { // solution correct

			//Connaissance connaissance = mRepositoryConnaissance.findByOrdre(niveaux);
			Connaissance connaissance = mRepositoryConnaissance.findOne(connaissanceREPLY.getId_ExEtu());
			
			etudiant.setConnaissances(connaissance);
			mRepositoryEtudiant.save(etudiant);
			session.setAttribute("result", "oui");
			session.setAttribute("niveaux", niveaux);
			
			/**/
			History history = new History(1);
			mRepositoryHistory.save(history);
			history.setEtudiant(etudiant);
			history.setConnaissance(connaissanceREPLY);
			history.setExercice(ExerciceREPLY);
			mRepositoryHistory.save(history);

		} else if (mExerciceService.ExerciceComparTo(codeNetoyer, ExerciceREPLY.getXMLSolutionNettoyer())) { // solution non correct

			session.setAttribute("niveaux", niveaux - 1);
			session.setAttribute("result", "non");
			History history = new History(1);
			mRepositoryHistory.save(history);
			history.setEtudiant(etudiant);
			history.setConnaissance(connaissanceREPLY);
			history.setExercice(ExerciceREPLY);
		}

		return new RedirectView("/aceuil");
	}
}
