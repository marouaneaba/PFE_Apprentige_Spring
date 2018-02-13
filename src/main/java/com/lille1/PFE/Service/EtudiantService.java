package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.History;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryHistory;

@Service
public class EtudiantService {

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;
	@Autowired
	private RepositoryHistory mRepositoryHistory;
	
	// ajouter un Etudiant
	@Transactional
	public Etudiant addEtudiant(String pseudo, String password, String email, String type) {
		return mRepositoryEtudiant.save(new Etudiant(pseudo, password, email, type));
	}

	//retourner tous les Etudiants stocker dans la base de données
	@Transactional
	public Iterable<Etudiant> getAllEtudiant() {
		return mRepositoryEtudiant.findAll();
	}

	// récupérer un Etudiant à partir son Pseudo et password
	@Transactional
	public Etudiant VerifyEtudiant(String pseudo, String password) {
		return mRepositoryEtudiant.findByNomAndPassword(pseudo, password);
	}

	// retourner un Etudiant à partir son ID
	public Etudiant getEtudiantById(Long id) {
		return mRepositoryEtudiant.findOne(id);
	}

	//modification un Etudaint 
	@Transactional
	public void updateEtudiant(Long id, String pseudo, String password, String email) {
		mRepositoryEtudiant.updateEtudiant(id, pseudo, password, email);
	}

	//supprimer un etudiant
	@Transactional
	public void deleteEtudiantById(Long id) {
		
		List<History> historys =  mRepositoryHistory.findByEtudiant(mRepositoryEtudiant.findOne(id));
		
		mRepositoryHistory.delete(historys);
		
		mRepositoryEtudiant.delete(id);
	}

	//transformer un Iterable des etudiants à une list des etudiants
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
