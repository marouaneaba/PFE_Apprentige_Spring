package com.lille1.PFE.Service;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Variable;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryVariable;

@Service
public class ExerciceService {

	@Autowired
	private RepositoryExercice mRepositoryExercice;
	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	@Autowired
	private RepositoryVariable mRepositoryVariable;
	
	private SAXBuilder sxb = new SAXBuilder();
	
	
	
	public List<Variable> SaveVaribale(String value){
		value = "<doc>"+value+"</doc>";
		List<Variable> varibales = new ArrayList<>();
		
		Document document = null;
		String[] variables = null;
		String[] types = null;
	      try
	      {
	     
	         document = sxb.build(new ByteArrayInputStream(value.getBytes()));
	      }
	      catch(Exception e){}

	      Element racine =document.getRootElement();

		   List listEtudiants = racine.getChildren("li");
		   Iterator index = listEtudiants.iterator();
		   while(index.hasNext())
		   {
			  
		      Element courant = (Element)index.next();
		      List<Element> nomVariableElement = courant.getChildren();
		      
		      variables = nomVariableElement.get(0).getText().trim().split("\n");
		      types = nomVariableElement.get(2).getText().trim().split("\n");
		   
			   for(int i=0;i<variables.length;i++){
				   varibales.add(new Variable(variables[i],types[i]));
			    	  System.out.println("var : "+variables[i]);
			    	  System.out.println("type : "+types[i]);
			   }
		   }
		   return varibales;
	}
	
	public List<Exercice> convertIterableToList(Iterable<Exercice> iterable) {
        if (iterable instanceof List) {
            return (List<Exercice>) iterable;
        }
        List<Exercice> list = new ArrayList<>();
        if (iterable != null) {
            for (Exercice e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
	
	public List<Exercice> getAllExercices(){
		return convertIterableToList(mRepositoryExercice.findAll());
	}
	
	public void deleteExercice(Long id){
		List<Variable> variables = mRepositoryExercice.findOne(id).getVariables();
		for(int i=0;i<variables.size();i++){
			mRepositoryVariable.delete(variables.get(i).getId());
		}
		mRepositoryExercice.delete(mRepositoryExercice.findOne(id));
	}
	
	public Exercice getExerciceById(Long id){
		return mRepositoryExercice.findOne(id);
	}
	
	public void addExerciceRepository(String nomExercice,String enonceExercice,
								String XMLSolution,String XMLSolutionNettoyer,
								List<Connaissance> connaissances,List<Variable> variables,Enseignant enseignant){
		
		Exercice exercice = new Exercice (nomExercice,enonceExercice,XMLSolution,XMLSolutionNettoyer);
		mRepositoryExercice.save(exercice);
		//exercice.setConnaissance(connaissances);
		//mRepositoryConnaissance.save(connaissances);
		//exercice.setVariables(variables);
		//mRepositoryVariable.save(variables);
		//exercice.setEnseignant(enseignant);
		//mRepositoryPersonne.save(enseignant);
		
	}
	
	public void addExerciceEnseigantRepository(String nomExercice,String enonceExercice,
				String XMLSolution,String XMLSolutionNettoyer,
				List<Connaissance> connaissances,List<Variable> variables,Personne personne){
	
		Enseignant enseignantRechercher = mRepositoryEnseignant.findOne(personne.getIdEns());
		Exercice exercice = new Exercice(nomExercice,enonceExercice,XMLSolution,XMLSolutionNettoyer);
		enseignantRechercher.setExercices(exercice);
		mRepositoryExercice.save(exercice);
		exercice.setConnaissance(connaissances);
		mRepositoryConnaissance.save(connaissances);
		exercice.setVariables(null);
		exercice.setEnseignant(null);
		
	
	}
	
	public List<Exercice> getExerciceEnseignant(Long id){
		return mRepositoryEnseignant.findOne(id).getExercices();
	}
	
	
	public void saveExercice(String nameExercice,String ennoncer_exercice,String variable,
			String codeBrouillon,String codeNetoyer,List<String> connaissancesSelected,Personne personne){
		
		Enseignant enseignant = mRepositoryEnseignant.findOne(personne.getIdEns());
		Exercice exercice = new Exercice(nameExercice,ennoncer_exercice,codeBrouillon,codeNetoyer);
		
		System.out.println(codeBrouillon);
		List<Connaissance> connaissances = new ArrayList<>();
		for(int i=0;i<connaissancesSelected.size();i++){
			if( connaissancesSelected.get(i) != null && !connaissancesSelected.get(i).equals(""))
			connaissances.add(mRepositoryConnaissance.findOne(Long.parseLong(connaissancesSelected.get(i))));
		}
		exercice.setConnaissance(connaissances);
		List<Variable> variables = null;/*SaveVaribale(variable);
		for(int i=0;i<variables.size();i++){
			System.out.println("mon objet varibale : "+variables.get(i));
		}*/
		exercice.setVariables(variables);
		mRepositoryVariable.save(variables);
		
		enseignant.setExercices(exercice);
		mRepositoryExercice.save(exercice);
		
	}
	
}
