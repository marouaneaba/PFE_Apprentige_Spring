package com.lille1.PFE.ControllerEnseignant;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.view.RedirectView;

import com.lille1.PFE.Entity.Admin;
import com.lille1.PFE.Entity.Enseignant;
import com.lille1.PFE.Entity.Etudiant;
import com.lille1.PFE.Entity.Exercice;
import com.lille1.PFE.Entity.Personne;
import com.lille1.PFE.Repository.RepositoryAdmin;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.Repository.RepositoryPersonne;
import com.lille1.PFE.Service.ExerciceService;

@Controller
@RequestMapping("/dashbord")
public class ControllerDashbord {

	@Autowired
	private RepositoryAdmin mRepositoryAdmin;

	@Autowired
	private RepositoryEtudiant mRepositoryEtudiant;

	@Autowired
	private RepositoryPersonne mRepositoryPersonne;

	@Autowired
	private RepositoryEnseignant mRepositoryEnseignant;

	@Autowired
	private RepositoryExercice mRepositoryExercice;

	@Autowired
	private ExerciceService mExerciceService;

	@RequestMapping(method = RequestMethod.GET)
	public RedirectView VerifyLogin(HttpServletRequest request, ModelMap pModel, Principal principal) {

		// Enseignant enseignant =
		// mRepositoryEnseignant.findByNome(principal.getName());
		/*
		 * System.out.println("ens : dachbord : "+enseignant); List<Exercice>
		 * exercices =
		 * mExerciceService.convertIterableToList(mRepositoryExercice.findAll())
		 * ; for(int i=0;i<exercices.size();i++){
		 * System.out.println("exercice : "+exercices.get(i)); }
		 */

		Etudiant etudiant = mRepositoryEtudiant.findByNome(principal.getName());
		Enseignant enseignant = mRepositoryEnseignant.findByNome(principal.getName());
		Admin admin = mRepositoryAdmin.findByNome(principal.getName());

		HttpSession session = request.getSession();
		Personne personne = null;// mRepositoryPersonne.findByNome(principal.getName());

		if (etudiant != null) {
			personne = (Personne) etudiant;
			session.setAttribute("user", (Personne) etudiant);
		} else if (enseignant != null) {
			personne = (Personne) enseignant;
			session.setAttribute("user", (Personne) enseignant);
		} else if (admin != null) {
			personne = (Personne) admin;
			session.setAttribute("user", (Personne) admin);
		}

		if (personne.getRole().equals("enseignant")) {
			return new RedirectView("/enseignant");

		} else if (personne.getRole().equals("admin")) {
			return new RedirectView("/admin");
		} else {
			return new RedirectView("/aceuil");
		}

	}
}
