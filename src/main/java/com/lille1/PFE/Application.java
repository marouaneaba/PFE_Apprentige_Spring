package com.lille1.PFE;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.context.WebApplicationContext;

import com.lille1.PFE.Repository.RepositoryAdmin;
import com.lille1.PFE.Repository.RepositoryConnaissance;
import com.lille1.PFE.Repository.RepositoryEnseignant;
import com.lille1.PFE.Repository.RepositoryEtudiant;
import com.lille1.PFE.Repository.RepositoryExercice;
import com.lille1.PFE.ControllerEnseignant.ClassScope;
import com.lille1.PFE.Entity.Admin;
import com.lille1.PFE.Entity.Connaissance;
import com.lille1.PFE.Entity.Enseignant;


@SpringBootApplication
@EnableJpaRepositories
public class Application extends SpringBootServletInitializer{

	
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        //return application.sources(Application.class);
        return configureApplication (application);
    }
	
	private static SpringApplicationBuilder configureApplication(SpringApplicationBuilder builder) {
        return builder.sources(Application.class).bannerMode(Mode.OFF);
    }
	
	public static void main(String[] args) {
		//SpringApplication.run(Application.class, args);
		configureApplication(new SpringApplicationBuilder()).run(args);
	}


	@Bean
	@Scope(value = WebApplicationContext.SCOPE_GLOBAL_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
	public ClassScope globalSessionMessage() {
		return new ClassScope();
	}

	/*@Bean
	public CommandLineRunner loadexercices(RepositoryExercice mRepositoryExercice,
			RepositoryConnaissance mRepositoryConnaissance, RepositoryAdmin mRepositoryAdmin,RepositoryEnseignant mRepositoryEnseignant ,RepositoryEtudiant mRepositoryEtudiant) {
		return (args) -> {

			mRepositoryEnseignant.deleteAll();
			Enseignant ens = new Enseignant("ens", "ens","ens@ens.ens","enseignant");
			mRepositoryEnseignant.save(ens);
			
			mRepositoryAdmin.deleteAll();
			Admin admin = new Admin("admin","admin","admin@admin.admin","admin");
			mRepositoryAdmin.save(admin);
			
			mRepositoryConnaissance.deleteAll();
			Connaissance con = new Connaissance("if", 1,false);
			mRepositoryConnaissance.save(con);
			
			// mRepositoryEtudiant.deleteAll();
			// mRepositoryConnaissance.deleteAll();

			/* création des connaissances */
			/*
			 * List<Etudiant> etudiants = new ArrayList<>(); etudiants.add(new
			 * Etudiant("etu1 ","etu1","email 1","etudiant")); etudiants.add(new
			 * Etudiant("etu2 ","etu2","email 2","etudiant")); etudiants.add(new
			 * Etudiant("etu3 ","etu3","email 3","etudiant"));
			 * 
			 * List<Connaissance> connaissances2 = new ArrayList<>();
			 * connaissances2.add(new
			 * Connaissance("boucle for","ordre : 1","score : 0"));
			 * connaissances2.add(new
			 * Connaissance("condition if","ordre : 1","score : 0"));
			 * connaissances2.add(new
			 * Connaissance("boucle tantque","ordre : 1","score : 0"));
			 * 
			 * for (Etudiant etudiant : etudiants) {
			 * 
			 * mRepositoryEtudiant.save(etudiants);
			 * etudiant.setConnaissances(connaissances2);
			 * mRepositoryConnaissance.save(connaissances2);
			 * 
			 * }
			 

		};
	}*/

}
