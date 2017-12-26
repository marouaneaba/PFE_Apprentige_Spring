package com.lille1.PFE.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Etudiant;

public interface RepositoryEnseignant extends PersonneBaseRepository<Enseignant>{
	
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Enseignant p SET p.nom = :nom , p.password = :password , "
			+ "p.email = :email  where p.idEns = :enseigantID")
	
	public int updateEnseignantById(@Param("enseigantID")Long id,@Param("nom")String nom,
									@Param("password")String password,@Param("email")String email);
	
	public Enseignant findByNomAndPassword(String nom, String password);
	public Enseignant findByNom(String pseudo);
}
