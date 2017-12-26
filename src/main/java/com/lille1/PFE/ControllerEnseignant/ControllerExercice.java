package com.lille1.PFE.ControllerEnseignant;


import java.util.ArrayList;
import java.util.Enumeration;
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
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Service.AjoutExerciceService;
import com.lille1.PFE.Service.ConnaissanceService;
import com.lille1.PFE.Service.ExerciceService;
import com.lille1.PFE.sax.SaxHandler;

@Controller
@RequestMapping("/AjouterExercice")
public class ControllerExercice {

	@Autowired
	private AjoutExerciceService mAjoutExerciceService;
	
	@Autowired
	private ConnaissanceService mConnaissanceService;
	
	@Autowired
	private ExerciceService mExerciceService;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	
	@Resource(name="globalSessionMessage")
	ClassScope sessionGlobal;
	
	@RequestMapping(method = RequestMethod.GET)
    public String getPage(HttpServletRequest request,ModelMap pModel) {
		
		List<Connaissance> connaissances = mConnaissanceService.getAllConnaissance();
		HttpSession session = request.getSession();
		
		pModel.addAttribute("connaissances",mRepositoryConnaissance.findConnaissanceByValider(true));
		pModel.addAttribute("connaissancesNonValider",sessionGlobal.getConnaissance());
		return "AjouterExercice";
    }
	
	
	@RequestMapping(method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public RedirectView recupererExercice(HttpServletRequest request,ModelMap pModel) {
		
		HttpSession session = request.getSession();
		Personne personne = (Personne)session.getAttribute("user");
		String nameExercice = request.getParameter("nomExercice");
		String exercice = request.getParameter("exercice");
		String variable = request.getParameter("variable");
		String codeBrouillon = request.getParameter("code");
		//String[] connaissancesSelected = request.getParameterValues("connaissancesSelect");
		
		/* enlevé les espace au début et la fin de la chaine */
			exercice = exercice.trim();
			variable = variable.trim();
			codeBrouillon = codeBrouillon
								.trim()
								.replaceAll("\"", "'");
		
			System.out.println("post1 : "+nameExercice);
			System.out.println("post2 : "+exercice);
			System.out.println("post3 : "+variable);
			SaxHandler mSaxHandler = new SaxHandler();
			mSaxHandler.setResult("");
			String codeNetoyer = new SaxHandler().parserString( codeBrouillon);
			System.out.println("post4 : "+new SaxHandler().parserString( codeBrouillon));
			
			/*mExerciceService.addExerciceRepository(nameExercice,exercice,codeBrouillon
					,codeNetoyer,null,null,personne);*/
			List<Connaissance> connaissances = new ArrayList<>();
			connaissances.add(new Connaissance("nom1","ordre1","score1",true));
			connaissances.add(new Connaissance("nom2","ordre2","score2",true));
			mExerciceService.addExerciceEnseigantRepository(nameExercice,exercice,codeBrouillon
					,codeNetoyer,connaissances,null,personne);//connaissance,variable
			System.out.println("----: "+mRepositoryEnseignant.findOne(personne.getIdEns()));
		System.out.println("arivé terminer fin ligne ");
		pModel.addAttribute("connaissances",mConnaissanceService.getAllConnaissance());
		
		return new RedirectView("/enseignant");
    }
	
}
