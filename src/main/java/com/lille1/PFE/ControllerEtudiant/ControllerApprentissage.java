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
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
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
	private ExerciceService mExerciceService; 
	
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	
	@Autowired
	private ConnaissanceService mConnaissanceService;
	
	
	@RequestMapping(value="/apprentisage",method = RequestMethod.GET)
    public String GetAceuill(HttpServletRequest request,ModelMap pModel) {
		
		HttpSession session = request.getSession();
		Personne personne = (Personne)session.getAttribute("user");
		Etudiant etudiant = mRepositoryEtudiant.findOne(personne.getIdEns());
		
		/* connaissance lié à l'etudiant */
		List<Connaissance> connaissancesEtudiant = mRepositoryEtudiant.findConnaissaceByEtudiant(etudiant.getIdEns());
		/* connaissance tout les connaissances enregistrée dans notre base de données */
		List<Connaissance> connaissancesNonEtudiant = mConnaissanceService.getAllConnaissance();
		/* les connaissances n'est pas liée à l'etudiant */
		connaissancesNonEtudiant.removeAll(connaissancesEtudiant);
		// trié Attrubt Ordre d'etity Connaissance dans l'ordre coissante.
		Comparator<Connaissance> comparator = (x, y) -> (x.getOrdre() > y.getOrdre()) ? 1 : ((x.getOrdre() == y.getOrdre()) ? 0 : -1);
		connaissancesNonEtudiant.sort(comparator);
		//System.out.println("exercice Non Etudiant : "+connaissancesNonEtudiant);
		if(connaissancesNonEtudiant == null){
			// afficher au etudiant , vous avez réussi tout les niveaux liée a nos exercice ,
			// Veuillez Attendre la mise à jour des nouvelle connaissances à faire.
		}else{
			int niveaux = connaissancesNonEtudiant.get(0).getOrdre();
			List<Exercice> exercice = mRepositoryExercice.findByConnaissanceOrdre(niveaux);
			System.out.println("exercice by Ordre : "+exercice);
			Collections.shuffle(exercice);
			System.out.println("exercice by Ordre : "+exercice);
		}
		
		
		/*System.out.println("-----");
		List<Connaissance> distinctConnaissnceOfExercice = mRepositoryExercice.findConnaissance();
		System.out.println("-----");
		List<Connaissance> connaissanceEtudiant = null;
		try{
			connaissanceEtudiant = etudiant.getConnaissances() ;
			// not null size betwen 0 and +00.
		}catch(Exception e){}
		List<Connaissance> connaissance_proposer = new ArrayList<>();
		
		if(connaissanceEtudiant.size() == 0 ){
			
			connaissance_proposer = distinctConnaissnceOfExercice;
			System.out.println("t3 : "+connaissance_proposer);
		}else if(connaissanceEtudiant != null && connaissanceEtudiant.size()>0){
			

			boolean find = false;
			for(int i=0;i<distinctConnaissnceOfExercice.size();i++){
				for(int j=0;j<connaissanceEtudiant.size();j++){
					if(distinctConnaissnceOfExercice.get(i).getNom().equals(connaissanceEtudiant.get(j).getNom())){
						find = true;
						break;
					}
					//if(!find){
						//connaissance_proposer.add(distinctConnaissnceOfExercice.get(i));
					//}
				}
				if(find == false){
					connaissance_proposer.add(distinctConnaissnceOfExercice.get(i));
					//find = true;
				}
				find = false;
			}
			System.out.println("t3 : "+connaissance_proposer);
		}
		
		List<Exercice> exercice_fournie = new ArrayList<>();
		for(int i=0;i<connaissance_proposer.size();i++){
			List<Exercice> exercices = mRepositoryExercice.findByConnaissance(connaissance_proposer.get(i));
			for(int j=0;j<exercices.size();j++){
				if(!exercice_fournie.contains(exercices.get(j))){
					exercice_fournie.add(exercices.get(j));
				}
			}
		}
		
		
		//System.out.println(connaissance_proposer);
		System.out.println(exercice_fournie);
		//pModel.addAttribute("exercice",exercice_fournie.get(0));
		pModel.addAttribute("exercice",exercice_fournie.get(0));*/
		return "apprentissage";
        
    }
	
	
	@RequestMapping(value="/apprentisage",method = RequestMethod.POST)
    public String POSTApprentissage(HttpServletRequest request,ModelMap pModel) {
		
		String code = request.getParameter("code");
		
		code = code
				.trim()
				.replaceAll("\"", "'");
		SaxHandler mSaxHandler = new SaxHandler();
		mSaxHandler.setResult("");
		String codeNetoyer = new SaxHandler().parserString( code);
		
		if( code.equals("") ){
			System.out.println("i'm here ");	
		}
		
		System.out.println("code : "+codeNetoyer);
		
		return null;
	}
}
