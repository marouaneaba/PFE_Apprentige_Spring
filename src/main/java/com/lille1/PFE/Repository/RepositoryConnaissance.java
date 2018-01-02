package com.lille1.PFE.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Enseignant;

public interface RepositoryConnaissance extends CrudRepository<Connaissance, Long> {

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Connaissance c SET c.nom = :name , c.ordre = :ordre where c.id_ExEtu = :connaissanceID")
	public int updateConnaissanceById(@Param("connaissanceID") Long id, @Param("name") String nom,
			@Param("ordre") int ordre);

	@Modifying(clearAutomatically = true)
	@Query("UPDATE Connaissance c SET c.valider = 1 where c.id_ExEtu = :connaissanceID")
	public int updateConnaissanceById(@Param("connaissanceID") Long id);

	public List<Connaissance> findConnaissanceByValider(boolean valider);

	public List<Connaissance> findConnaissanceByEnseignantAndValider(Enseignant enseignant, boolean valider);

	// @Query("select c from Connaissance c where c.id_ExEtu != (select
	// ec.id_ExEtu from Etudiant e JOIN e.connaissances ec)")
	// @Query("select distinct c from Etudiant e inner JOIN e.connaissances ec ,
	// Connaissance c where c != ec")
	// @Query("select ec from Etudiant e JOIN e.connaissances ec")
	@Query("select distinct ec from Etudiant e inner JOIN e.connaissances ec where ec != connai ")
	public List<Connaissance> findConnaissaceNonEtudiant(@Param("connai") List<Connaissance> connaissanceEtudiant);

	public Connaissance findByOrdre(int order);

}
