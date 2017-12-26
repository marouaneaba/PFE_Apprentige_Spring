package com.lille1.PFE.Repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Personne;

@NoRepositoryBean
public interface PersonneBaseRepository<T extends Personne> extends CrudRepository<T, Long>{

	
}
