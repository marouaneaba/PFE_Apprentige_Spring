package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.History;

public interface RepositoryHistory extends CrudRepository<History, Long>  {

	/*@Query("select ex from History h , Etudiant etu ,Exercice ex where h.Score = :score and etu.idEns = :etudiant")
	List<Exercice> findByScoreAndEtudiant(@Param("score")int score,@Param("etudiant")Long etudiant);*/
	
	List<History> findByScoreAndEtudiant(int score,Etudiant etudiant);
	History findByExercice(Exercice ex);
	
}
