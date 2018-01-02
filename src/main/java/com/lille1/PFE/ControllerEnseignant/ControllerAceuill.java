package com.lille1.PFE.ControllerEnseignant;

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

import com.lille1.PFE.ControllerEtudiant.ResultExercice;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Service.ConnaissanceService;

@Controller
@RequestMapping("/aceuil")
public class ControllerAceuill {

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Autowired
	private ConnaissanceService mConnaissanceService;

	@RequestMapping(method = RequestMethod.GET)
	public String GetAceuill(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();

		Etudiant etudiant = mRepositoryEtudiant.findOne(((Etudiant) session.getAttribute("user")).getIdEns());
		pModel.addAttribute("user", etudiant.getNom());

		String result = (String) session.getAttribute("result");
		System.out.println("result : " + result);

		if (result == null) {
			int niveaux = Integer.parseInt(mConnaissanceService.getNiveaux(etudiant.getIdEns()));

			List<Connaissance> connaissancesEtudiant = mRepositoryEtudiant
					.findConnaissaceByEtudiant(etudiant.getIdEns());
			Comparator<Connaissance> comparator = (x, y) -> (x.getOrdre() > y.getOrdre()) ? 1
					: ((x.getOrdre() == y.getOrdre()) ? 0 : -1);
			connaissancesEtudiant.sort(comparator);
			pModel.addAttribute("connaissanceEtu", connaissancesEtudiant);
			pModel.addAttribute("niveaux", niveaux - 1);
			pModel.addAttribute("niveauxSuivant", niveaux);
		}

		if (result != null && result.equals("oui")) {
			System.out.println("oui");
			int niveaux = (int) session.getAttribute("niveaux");
			String tete = "Félicitation : " + etudiant.getNom();
			String mot = "vous avez attient le niveaux : " + niveaux;
			ResultExercice mResultExercice = new ResultExercice(niveaux, tete, mot, true);

			pModel.addAttribute("screen", mResultExercice);
			pModel.addAttribute("niveaux2", niveaux + 1);
		} else if (result != null && result.equals("non")) {
			System.out.println("non");
			int niveaux = (int) session.getAttribute("niveaux");
			String tete = "Exercice non Correct : ";
			String mot = "vous étes sur le méme niveaux : " + niveaux;
			ResultExercice mResultExercice = new ResultExercice(niveaux, tete, mot, false);

			pModel.addAttribute("screen", mResultExercice);
			pModel.addAttribute("niveaux2", niveaux + 1);
		} else if (result != null && result.equals("finie")) {
			System.out.println("finie");
			int niveaux = (int) session.getAttribute("niveaux");
			String tete = "Félicitation";
			String mot = "Vous avez réussie tout les connaissances \n Veuillez attendre une mise à jour des connaissances";
			ResultExercice mResultExercice = new ResultExercice(niveaux, tete, mot, false);

			pModel.addAttribute("screen", mResultExercice);
			pModel.addAttribute("niveaux2", niveaux);
		}

		return "/aceuil";
	}
}
