package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

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
	private RepositoryVariable mRepositoryVariable;
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;
	
	
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
	
}
