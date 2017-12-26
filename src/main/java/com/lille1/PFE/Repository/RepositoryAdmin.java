package com.lille1.PFE.Repository;

import org.springframework.data.repository.CrudRepository;

import com.lille1.PFE.Entity.Admin;
import com.lille1.PFE.Entity.Etudiant;

public interface RepositoryAdmin extends PersonneBaseRepository<Admin>{

	public Admin findAdminByNomAndPassword(String pseudo,String password);
	public Admin findByNom(String pseudo);
}
