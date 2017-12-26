package com.lille1.PFE.Service;



import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Repository.RepositoryEtudiant;

@Service
public class EtudiantService {
	
	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant; 
	private boolean connected = false;
	

	
	@Transactional
	public Etudiant addEtudiant(String pseudo, String password, String email,String type){
		return mRepositoryEtudiant.save(new Etudiant(pseudo, password, email,type));
	}
	
	
	@Transactional
	public Iterable<Etudiant> getAllEtudiant(){
		return mRepositoryEtudiant.findAll();
	}
	
	@Transactional
	public Etudiant VerifyEtudiant(String pseudo,String password){
		return mRepositoryEtudiant.findByNomAndPassword(pseudo,password);
	}
	
	public Etudiant getEtudiantById(Long id){
		return mRepositoryEtudiant.findOne(id);
	}
	
	@Transactional
	public void updateEtudiant(Long id,String pseudo,String password,String email){
		mRepositoryEtudiant.updateEtudiant(id, pseudo, password, email);
	}
	
	@Transactional
	public void deleteEtudiantById(Long id){
		mRepositoryEtudiant.delete(id);
	}
	
	public List<Etudiant> convertIterableToList(Iterable<Etudiant> iterable) {
        if (iterable instanceof List) {
            return (List<Etudiant>) iterable;
        }
        List<Etudiant> list = new ArrayList<>();
        if (iterable != null) {
            for (Etudiant e : iterable) {
                list.add(e);
            }
        }
        return list;
    }
	
	
}
