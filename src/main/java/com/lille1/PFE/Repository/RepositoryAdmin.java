package com.lille1.PFE.Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.lille1.PFE.Entity.Admin;


public interface RepositoryAdmin extends PersonneBaseRepository<Admin> {

	public Admin findAdminByNomAndPassword(String pseudo, String password);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Admin a SET a.nom = :name , a.password = :password, a.email = :email  where a.idEns = :AdminID")
	public int updateConnaissanceById(@Param("AdminID") Long id, @Param("name") String pseudo,
			@Param("password") String password, @Param("email") String email);

	@Query("select a from Admin a where a.nom = :nom")
	public Admin findByNome(@Param("nom") String nom);
}
