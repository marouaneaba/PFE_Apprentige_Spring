package com.lille1.PFE.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEtudiant;

@Service
public class ConnaissanceService {

	@Autowired
	private RepositoryConnaissance mRepositoryConnaissance;

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Autowired
	private ConnaissanceService mConnaissanceService;

	// transformer un Iterable à une list de Connaissance
	public List<Connaissance> convertIterableToList(Iterable<Connaissance> iterable) {
		if (iterable instanceof List) {
			return (List<Connaissance>) iterable;
		}
		List<Connaissance> list = new ArrayList<>();
		if (iterable != null) {
			for (Connaissance e : iterable) {
				list.add(e);
			}
		}
		return list;
	}

	//retourner tous les Connaissances
	public List<Connaissance> getAllConnaissance() {

		return convertIterableToList(mRepositoryConnaissance.findAll());
	}

	// supprimer une Connaissances à partir ID
	public void supprimerConnaissances(Long id) {
		mRepositoryConnaissance.delete(id);
	}

	// retourner une Connaissance à partir ID
	public Connaissance getConnaissances(Long id) {
		return mRepositoryConnaissance.findOne(id);
	}

	// modifier la Connaissance
	@Transactional
	public void updateConnaissance(Long id, String nom, int ordre) {
		mRepositoryConnaissance.updateConnaissanceById(id, nom, ordre);
	}

	//modifier l'attribut confirmer de connaissance à true
	@Transactional
	public void updateConnaissanceToTrue(Long id) {
		mRepositoryConnaissance.updateConnaissanceById(id);
	}

	// retourner le niveaux d'un étudiant
	public String getNiveaux(Long idEtudiant) {
		List<Connaissance> connaissancesEtudiant = mRepositoryEtudiant.findConnaissaceByEtudiant(idEtudiant);
		List<Connaissance> connaissancesNonEtudiant = mConnaissanceService.getAllConnaissance();
		connaissancesNonEtudiant.removeAll(connaissancesEtudiant);
		Comparator<Connaissance> comparator = (x, y) -> (x.getOrdre() > y.getOrdre()) ? 1
				: ((x.getOrdre() == y.getOrdre()) ? 0 : -1);
		connaissancesNonEtudiant.sort(comparator);

		if (connaissancesNonEtudiant == null || connaissancesNonEtudiant.size() == 0) {


			return null;

		}
		return "" + connaissancesNonEtudiant.get(0).getOrdre();

	}
}
