package com.lille1.PFE.ControllerEtudiant;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Service.ExerciceService;

@Controller
public class ControllerApprentissage {

	@Autowired
	private RepositoryExercice mRepositoryExercice;
	
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant; 
	
	@Autowired
	private ExerciceService mExerciceService; 
	
	
	
	@RequestMapping(value="/apprentisage",method = RequestMethod.GET)
    public String GetAceuill(HttpServletRequest request,ModelMap pModel) {
		
		HttpSession session = request.getSession();
		Personne personne = (Personne)session.getAttribute("user");
		Etudiant etudiant = mRepositoryEtudiant.findOne(personne.getIdEns());
		List<Connaissance> distinctConnaissnceOfExercice = mRepositoryExercice.findConnaissance();
		List<Connaissance> connaissanceEtudiant = null;
		try{
			connaissanceEtudiant = etudiant.getConnaissances() ;
		}catch(Exception e){}
		List<Connaissance> connaissance_proposer = new ArrayList<>();
		
		if(connaissanceEtudiant == null ){
			
			connaissance_proposer = distinctConnaissnceOfExercice;
			
		}else if(connaissanceEtudiant != null && connaissanceEtudiant.size()>0){
			

			boolean find = false;
			for(int i=0;i<distinctConnaissnceOfExercice.size();i++){
				for(int j=0;j<connaissanceEtudiant.size();j++){
					if(distinctConnaissnceOfExercice.get(i).getNom().equals(connaissanceEtudiant.get(j).getNom())){
						find = true;
					}
					if(!find){
						connaissance_proposer.add(distinctConnaissnceOfExercice.get(i));
					}
				}
			}
		}
		List<Exercice> exercice_fournie = new ArrayList<>();
		for(int i=0;i<connaissance_proposer.size();i++){
			List<Exercice> exercices = mRepositoryExercice.findByConnaissance(connaissance_proposer.get(0));
			for(int j=0;j<exercices.size();j++){
				if(!exercice_fournie.contains(exercices.get(j))){
					exercice_fournie.add(exercices.get(j));
				}
			}
		}
		
		
		//System.out.println(connaissance_proposer);
		System.out.println(exercice_fournie);
		pModel.addAttribute("exercice",exercice_fournie.get(0));
		return "apprentissage";
        
    }
}
