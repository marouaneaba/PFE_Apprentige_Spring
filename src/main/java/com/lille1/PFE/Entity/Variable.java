package com.lille1.PFE.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Variable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String Nom;
	@Column
	private String Type;

	public Variable() {
	}

	public Variable(String nom, String type) {

		Nom = nom;
		Type = type;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
		Type = type;
	}

	@Override
	public String toString() {
		return "Variable [id=" + id + ", Nom=" + Nom + ", Type=" + Type + "]";
	}

}
