package com.lille1.PFE.Repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.*;

@Transactional
public interface RepositoryEtudiant extends PersonneBaseRepository<Etudiant>{
	
	
	@Modifying
	@Query("update Etudiant e set e.nom = :nom , e.password = :password , e.email = :email where e.idEns = :idEtu")
	public int updateEtudiant(@Param("idEtu")Long id,@Param("nom")String pseudo,
			@Param("password")String password,@Param("email")String email );
	
	public Etudiant findByNomAndPassword(String pseudo,String password);
	public Etudiant findByNom(String pseudo);

}
