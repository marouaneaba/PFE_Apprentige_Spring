package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lille1.PFE.Entity.*;
import com.lille1.PFE.Repository.RepositoryEnseignant;



@Service
public class EnseignantService {

	
	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant; 

	
	@Transactional
	public Enseignant setEnseignant(String name, String password,String email,String type){
		return mRepositoryEnseignant.save(new Enseignant(name, password,email,type));
	}
	
	
	
	
	@Transactional
	public Iterable<Enseignant> getAllEnseignant(){
		return mRepositoryEnseignant.findAll();
	}
	
	@Transactional
	public Enseignant VerifyEnseignant(String pseudo,String password){
		return mRepositoryEnseignant.findByNomAndPassword(pseudo,password);
	}
	
	public void deleteEns(Long id){
		mRepositoryEnseignant.delete(id);
	}
	
	public Enseignant getEnseignantById(Long id){
		return mRepositoryEnseignant.findOne(id);
	}
	
	@Transactional
	public void updateEnseignant(Long id,String nom,String password,String email){
		mRepositoryEnseignant.updateEnseignantById(id, nom, password, email);
	}
	
	public List<Connaissance> getAllConnaissaneEnseignantById(Long id){
		Enseignant enseignant = mRepositoryEnseignant.findOne(id);
		List<Connaissance> connaissances = new ArrayList<>();
		for(int i=0;i<enseignant.getExercices().size();i++){
			Exercice exercices = enseignant.getExercices().get(i);
			for(int j=0;j<exercices.getConnaissance().size();j++){
				if(!connaissances.contains(exercices.getConnaissance().get(j))){
					connaissances.add(exercices.getConnaissance().get(j));
				}
			}
		}
		return connaissances;
	}
}
