package com.lille1.PFE.Entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class Etudiant extends Personne{
	
	
	
	@ManyToMany
	private List<Connaissance> connaissances;
	
	public Etudiant(){}
	
	public Etudiant(String nom, String password, String email, String type) {
		super(nom,password,email,type);
		connaissances = new ArrayList<>();
	}

	public List<Connaissance> getConnaissances() {
		return connaissances;
	}

	public void setConnaissances(List<Connaissance> connaissances) {
		this.connaissances = connaissances;
	}

	@Override
	public String toString() {
		return "Etudiant [ Personne = "+super.toString()+" ,connaissances=" + connaissances + "]";
	}
	
	
	
	
	
}
