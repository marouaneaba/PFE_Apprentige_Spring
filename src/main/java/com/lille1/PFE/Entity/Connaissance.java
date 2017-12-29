package com.lille1.PFE.Entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Connaissance  {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_ExEtu;
	
	@Column
	private String nom;
	@Column 
	private String ordre;
	@Column
	private String score;
	@Column 
	private boolean valider;
	@ManyToOne
	private Enseignant enseignant;
	
	
	public Connaissance() {}
	
	public Connaissance(String nom, String ordre, String score,boolean valider) {
		this.nom = nom;
		this.ordre = ordre;
		this.score = score;
	}
	public Long getId_ExEtu() {
		return id_ExEtu;
	}
	public void setId_ExEtu(Long id_ExEtu) {
		this.id_ExEtu = id_ExEtu;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
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
	
	public boolean isValider() {
		return valider;
	}

	public void setValider(boolean valider) {
		this.valider = valider;
	}

	
	
	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	@Override
	public String toString() {
		return "Connaissance [id_ExEtu=" + id_ExEtu + ", nom=" + nom + ", ordre=" + ordre + 
				", score=" + score + ", enseignat ]";
	}
	
	
	
	
	
	
}
