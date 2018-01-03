package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Connaissance;

public interface RepositoryExercice extends CrudRepository<Exercice, Long> {

	List<Exercice> findByNomExercice(String nom_exercice);

	@Query("select distinct e.connaissance FROM  Exercice e")
	List<Connaissance> findConnaissance();

	List<Exercice> findByConnaissance(Connaissance connaissances);

	List<Exercice> findByConnaissanceOrdre(int ordre);
	
	@Query("select distinct e FROM  Exercice e , Connaissance con where con in :con")
	List<Exercice> findByConnaissance(@Param("con")List<Connaissance>  connaissances);

}
