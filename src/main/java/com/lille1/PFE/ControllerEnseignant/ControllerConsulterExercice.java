package com.lille1.PFE.ControllerEnseignant;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryHistory;
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.ExerciceService;
import com.lille1.PFE.Service.HistoryService;

@Controller
@RequestMapping("/consulterExercice")
public class ControllerConsulterExercice {

	@Autowired
	private ExerciceService mExerciceService;
	@Autowired
	private ConnaissanceService mConnaissanceService;
	@Autowired
	private HistoryService mHistoryService;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	@Autowired
	private RepositoryHistory mRepositoryHistory;
	@Autowired
	private RepositoryExercice mRepositoryExercice;
	
	
	/**
	 * 
	 * @param request
	 * @param pModel
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public String recupererExercice(HttpServletRequest request, ModelMap pModel) {

		HttpSession session = request.getSession();
		Personne personne = (Personne) session.getAttribute("user");
		if (personne.getRole().equals("admin")) {
			pModel.addAttribute("consulter", false);
			pModel.addAttribute("exercices", mExerciceService.getAllExercices());
		} else if (personne.getRole().equals("enseignant")) {
			pModel.addAttribute("consulter", true);
			pModel.addAttribute("exercices", mExerciceService.getExerciceEnseignant(personne.getIdEns()));
		}

		return "ConsulterExercices";
	}

	/**
	 * 
	 * @param model
	 * @param idEx
	 * @return
	 */
	@RequestMapping(value = "/update/{id_ex}", method = RequestMethod.GET)
	public String editExerciceGet(Model model, @PathVariable("id_ex") Long idEx) {

		Exercice exercice = mExerciceService.getExerciceById(idEx);

		model.addAttribute("exercice", exercice);
		model.addAttribute("exerciceXML", exercice.getXMLSolution());
		model.addAttribute("connaissancees", mConnaissanceService.getAllConnaissance());

		System.out.println("sol : " + exercice.getXMLSolution());
		System.out.println("solNE : " + exercice.getXMLSolutionNettoyer());
		return "ModifierExercice";
	}

	/**
	 * 
	 * @param model
	 * @param idEx
	 * @return
	 */
	@RequestMapping(value = "/delete/{id_ex}", method = RequestMethod.GET)
	public RedirectView deleteExercice( Model model, @PathVariable("id_ex") Long idEx) {
		
		List<History> historys = mRepositoryHistory.findByExercice(mRepositoryExercice.findOne(idEx));
		mRepositoryHistory.delete(historys);
		
		List<Enseignant> ens = mRepositoryEnseignant.finByExercices2(idEx);
		for(int i=0;i<ens.size();i++){
			ens.get(i).getExercices().clear();
		}
		mRepositoryEnseignant.save(ens);

		model.addAttribute("exercices", mExerciceService.getAllExercices());
		return new RedirectView("/consulterExercice");
	}

}
