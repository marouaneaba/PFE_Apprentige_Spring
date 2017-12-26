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
	private String ordre;
	@Column
	private String score;
	
	
	
	
	public Variable() {}
	
	public Variable(String nom, String ordre, String score) {
		this.Nom = nom;
		this.ordre = ordre;
		this.score = score;
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



	public String getOrdre() {
		return ordre;
	}



	public void setOrdre(String ordre) {
		this.ordre = ordre;
	}



	public String getScore() {
		return score;
	}



	public void setScore(String score) {
		this.score = score;
	}



	@Override
	public String toString() {
		return "Variable [id=" + id + ", Nom=" + Nom + ", ordre=" + ordre + ", score=" + score + "]";
	}
	
	
	
	
	
	
}
