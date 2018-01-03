package com.lille1.PFE.Entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class History {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private int score;// 0 Où 1.

	@OneToOne
	private Etudiant etudiant;

	@OneToOne
	private Exercice exercice;

	@OneToOne
	private Connaissance connaissance;

	public History() {}

	public History(int score) {
		this.score = score;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getScore() {
		return this.score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Etudiant getEtudiant() {
		return etudiant;
	}

	public void setEtudiant(Etudiant etudiant) {
		this.etudiant = etudiant;
	}

	public Exercice getExercice() {
		return exercice;
	}

	public void setExercice(Exercice exercice) {
		this.exercice = exercice;
	}

	public Connaissance getConnaissance() {
		return connaissance;
	}

	public void setConnaissance(Connaissance connaissance) {
		this.connaissance = connaissance;
	}

	@Override
	public String toString() {
		return "History [id=" + id + ", Score=" + this.score + ", etudiant=" + etudiant + ", exercice=" + exercice
				+ ", connaissance=" + connaissance + "]";
	}

}
