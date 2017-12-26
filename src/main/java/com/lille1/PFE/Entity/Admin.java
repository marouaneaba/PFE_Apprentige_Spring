package com.lille1.PFE.Entity;

import javax.persistence.Entity;


@Entity
public class Admin extends Personne{

	
	
	public Admin(){}
	
	public Admin(String nom, String password, String email, String type) {
		super(nom,password,email,type);
	}

	@Override
	public String toString() {
		return "Admin [ Personne = "+super.toString()+" ]";
	}

	
}
