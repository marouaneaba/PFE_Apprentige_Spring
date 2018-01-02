package com.lille1.PFE.Entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
public class Enseignant extends Personne {

	@OneToMany
	private List<Exercice> exercices;

	public Enseignant() {
	}

	public Enseignant(String nom, String password, String email, String type) {
		super(nom, password, email, type);
		this.exercices = new ArrayList<>();
	}

	public List<Exercice> getExercices() {
		return exercices;
	}

	public void setExercices(Exercice exercices) {
		this.exercices.add(exercices);
		// this.exercices = exercices;
	}

	@Override
	public String toString() {
		return "Enseignant [ Personne = " + super.toString() + " ,exercices=" + exercices + "]";
	}

}
