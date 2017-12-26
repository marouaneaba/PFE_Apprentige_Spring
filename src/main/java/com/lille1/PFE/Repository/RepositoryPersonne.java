package com.lille1.PFE.Repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;

public interface RepositoryPersonne extends PersonneBaseRepository<Personne>{


	public Personne findByNomAndPassword(String pseudo,String password);
	@Query("select p.role from Personne p where p.nom = :nom")
	public String findByNom(@Param("nom")String nom);
}
