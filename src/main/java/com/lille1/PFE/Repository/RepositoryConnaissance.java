package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.lille1.PFE.Entity.Connaissance;

public interface RepositoryConnaissance extends CrudRepository<Connaissance, Long>{

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Connaissance c SET c.nom = :name , c.ordre = :ordre where c.id_ExEtu = :connaissanceID")
	public int updateConnaissanceById(@Param("connaissanceID")Long id,
						@Param("name")String nom,@Param("ordre")String ordre);
	
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Connaissance c SET c.valider = 1 where c.id_ExEtu = :connaissanceID")
	public int updateConnaissanceById(@Param("connaissanceID")Long id);
	
	
	public List<Connaissance> findConnaissanceByValider(boolean valider);
	
	
}
