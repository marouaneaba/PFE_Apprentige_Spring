package com.lille1.PFE.ControllerEtudiant;

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
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Service.ConnaissanceService;
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

	int niveaux;

	@RequestMapping(value = "/apprentisage", method = RequestMethod.GET)
	public String GetAceuill(HttpServletRequest request, ModelMap pModel) {

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
		niveaux = connaissancesNonEtudiant.get(0).getOrdre();
		System.out.println("niveaux : " + niveaux);
		List<Exercice> exercice = mRepositoryExercice.findByConnaissanceOrdre(niveaux);

		if (connaissancesNonEtudiant == null || connaissancesNonEtudiant.size() == 0 || exercice.size() == 0) {
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
			System.out.println("exercice by Ordre : " + exercice);
			Collections.shuffle(exercice);
			System.out.println("exercice by Ordre : " + exercice);
			pModel.addAttribute("exercice", exercice.get(0));
		}

		return "apprentissage";
	}

	@RequestMapping(value = "/apprentisage", method = RequestMethod.POST)
	public RedirectView POSTApprentissage(HttpServletRequest request, ModelMap pModel) {

		System.out.println("POST arrivé");

		HttpSession session = request.getSession();

		String code = request.getParameter("code");
		System.out.println("code : " + code);

		code = code.trim().replaceAll("\"", "'");
		System.out.println("code : " + code);

		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString(code);

		if (code.equals("")) {
			System.out.println("vous avez rien saisier comme code ");
		}

		System.out.println("code Netoyer : " + codeNetoyer);

		if (true) { // solution correct

			Connaissance connaissance = mRepositoryConnaissance.findByOrdre(niveaux);
			Etudiant etudiant = mRepositoryEtudiant.findOne(((Etudiant) session.getAttribute("user")).getIdEns());
			etudiant.setConnaissances(connaissance);
			mRepositoryEtudiant.save(etudiant);
			session.setAttribute("result", "oui");
			session.setAttribute("niveaux", niveaux);

		} else if (true) { // solution non correct

			session.setAttribute("niveaux", niveaux - 1);
			session.setAttribute("result", "non");

		}

		return new RedirectView("/aceuil");
	}
}
