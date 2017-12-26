package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.lille1.PFE.Entity.*;

public interface RepositoryExercice extends CrudRepository<Exercice, Long>{

	List<Exercice> findByNomExercice(String nom_exercice);
	
	@Query("select distinct e.connaissance FROM  Exercice e")
	List<Connaissance> findConnaissance();
	
	List<Exercice> findByConnaissance(Connaissance connaissances);
}
