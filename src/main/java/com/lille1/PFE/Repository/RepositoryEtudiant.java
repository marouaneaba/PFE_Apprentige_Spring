package com.lille1.PFE.Repository;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.*;

@Transactional
public interface RepositoryEtudiant extends PersonneBaseRepository<Etudiant> {

	@Modifying
	@Query("update Etudiant e set e.nom = :nom , e.password = :password , e.email = :email where e.idEns = :idEtu")
	public int updateEtudiant(@Param("idEtu") Long id, @Param("nom") String pseudo, @Param("password") String password,
			@Param("email") String email);

	public Etudiant findByNomAndPassword(String pseudo, String password);

	@Query("select e from Etudiant e where e.nom = :nom")
	public Etudiant findByNome(@Param("nom") String nom);

	@Query("select distinct ec from Etudiant e inner JOIN e.connaissances ec where e.idEns = :id")
	public List<Connaissance> findConnaissaceByEtudiant(@Param("id") Long idEtu);

	@Query("select distinct c from Etudiant e inner JOIN e.connaissances ec , Connaissance c  where c != ec")
	public List<Connaissance> findConnaissaceNonEtudiant();

}
