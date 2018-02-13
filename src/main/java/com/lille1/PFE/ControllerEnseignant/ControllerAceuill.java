package com.lille1.PFE.ControllerEnseignant;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lille1.PFE.ControllerEtudiant.ResultExercice;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryHistory;
import com.lille1.PFE.Service.ConnaissanceService;

@Controller
@RequestMapping("/aceuil")
public class ControllerAceuill {

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;
	@Autowired
	private RepositoryExercice mRepositoryExercice;
	@Autowired
	private ConnaissanceService mConnaissanceService;
	@Autowired
	private RepositoryHistory mRepositoryHistory;
	
	@RequestMapping(method = RequestMethod.GET)
	public String GetAceuill(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();

		Etudiant etudiant = mRepositoryEtudiant.findOne(((Etudiant) session.getAttribute("user")).getIdEns());
		pModel.addAttribute("user", etudiant.getNom());

		String result = (String) session.getAttribute("result");
		System.out.println("result : " + result);

		List<Connaissance> connaissancesEtudiant = mRepositoryEtudiant.findConnaissaceByEtudiant(etudiant.getIdEns());
		Comparator<Connaissance> comparator = (x, y) -> (x.getOrdre() > y.getOrdre()) ? 1
				: ((x.getOrdre() == y.getOrdre()) ? 0 : -1);
		connaissancesEtudiant.sort(comparator);
		pModel.addAttribute("connaissanceEtu", connaissancesEtudiant);
		
		List<Connaissance> connaissancesNonEtudiant = mConnaissanceService.getAllConnaissance();
		connaissancesNonEtudiant.removeAll(connaissancesEtudiant);
		
		List<Exercice> exercices = null;
		for(int i=0;i<connaissancesNonEtudiant.size();i++){
			
			if(mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)) != null 
					&& mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)).size()>0){
				exercices = mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i));
				break;
			}else if(mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)) == null 
					&& mRepositoryExercice.findByConnaissance(connaissancesNonEtudiant.get(i)).size() == 0){
				
				result = null;
			}
		}
		
		
		List<History> history = mRepositoryHistory.findByScoreAndEtudiant(1,etudiant);
		
		List<Exercice> exercices1ByEtudiant = new ArrayList<Exercice>();
		for(int i=0;i<history.size();i++){
			exercices1ByEtudiant.add(history.get(i).getExercice());
		}
		
		try{
			exercices.removeAll(exercices1ByEtudiant);
		}catch(NullPointerException e){
			
		}
		
		
		if (result == null || mConnaissanceService.getNiveaux(etudiant.getIdEns())== null || exercices == null ||exercices.size() == 0) {
			
			System.out.println("11 : ");
			if(mConnaissanceService.getNiveaux(etudiant.getIdEns()) != null && exercices != null && exercices.size()>0){
				int niveaux = Integer.parseInt(mConnaissanceService.getNiveaux(etudiant.getIdEns()));
				System.out.println("22 : ");
				
				pModel.addAttribute("niveaux", niveaux - 1);
				pModel.addAttribute("niveauxSuivant", niveaux);
				pModel.addAttribute("Suivant", false);
			}else if(mConnaissanceService.getNiveaux(etudiant.getIdEns())== null || exercices == null ||exercices.size() == 0){
				System.out.println("33 : ");
				pModel.addAttribute("connaissanceEtu", connaissancesEtudiant);
				if(connaissancesEtudiant.size() == 0){
					pModel.addAttribute("niveaux", 0);
					pModel.addAttribute("message", " Veuillez Attendre la mise à jour des connaissances !!");
				}else{
					pModel.addAttribute("niveaux", connaissancesEtudiant.get(connaissancesEtudiant.size()-1).getOrdre());
					pModel.addAttribute("message", "vous avez réussi tout les niveaux liée a nos exercice \n Veuillez Attendre une mise à jour !!");
				}
				pModel.addAttribute("Suivant", true);
				pModel.addAttribute("message", "vous avez réussi tout les niveaux liée a nos exercice \n Veuillez Attendre une mise à jour !!");
			}
		}else if (result != null && result.equals("oui")) {
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
		}

		return "/aceuil";
	}
}
