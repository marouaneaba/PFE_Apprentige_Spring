package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;

public interface RepositoryHistory extends CrudRepository<History, Long>  {


	
	List<History> findByScoreAndEtudiant(int score,Etudiant etudiant);
	List<History> findByExercice(Exercice ex);
	
	List<History> findByEtudiant(Etudiant etu);
	List<History> findByConnaissance(Connaissance connaissances);
	
}
