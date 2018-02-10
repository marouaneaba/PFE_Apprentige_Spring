package com.lille1.PFE.Entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

@Entity
public class Exercice { // peux etre faut instancier les deux List par = new
						// ArrayList dans le constructeur

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id_ex;
	@Column
	private String nomExercice;
	@Column
	private String enonceExercice;
	@Lob
	@Column
	private String XMLSolution;
	@Lob
	@Column
	private String XMLSolutionNettoyer;
	@ManyToMany
	private List<Connaissance> connaissance;
	@ManyToMany
	private List<Variable> variables;

	@OneToOne
	private Enseignant enseignant;

	public Exercice() {
	}

	public Exercice(String nomExercice, String enonceExercice, String xMLSolution, String xMLSolutionNettoyer) {
		
		this.nomExercice = nomExercice;
		this.enonceExercice = enonceExercice;
		this.XMLSolution = xMLSolution;
		this.XMLSolutionNettoyer = xMLSolutionNettoyer;
		this.connaissance = new ArrayList<>();
		this.variables = new ArrayList<>();

	}

	public Long getId_ex() {
		return id_ex;
	}

	public void setId_ex(Long id_ex) {
		this.id_ex = id_ex;
	}

	public String getNomExercice() {
		return nomExercice;
	}

	public void setNomExercice(String nomExercice) {
		this.nomExercice = nomExercice;
	}

	public String getEnonceExercice() {
		return enonceExercice;
	}

	public void setEnonceExercice(String enonceExercice) {
		this.enonceExercice = enonceExercice;
	}

	public String getXMLSolution() {
		return XMLSolution;
	}

	public void setXMLSolution(String xMLSolution) {
		XMLSolution = xMLSolution;
	}

	public String getXMLSolutionNettoyer() {
		return XMLSolutionNettoyer;
	}

	public void setXMLSolutionNettoyer(String xMLSolutionNettoyer) {
		XMLSolutionNettoyer = xMLSolutionNettoyer;
	}

	public List<Connaissance> getConnaissance() {
		return connaissance;
	}

	public void setConnaissance(List<Connaissance> connaissance) {
		this.connaissance = connaissance;
	}

	public List<Variable> getVariables() {
		return variables;
	}

	public void setVariables(List<Variable> variables) {
		this.variables = variables;
	}

	public Enseignant getEnseignant() {
		return enseignant;
	}

	public void setEnseignant(Enseignant enseignant) {
		this.enseignant = enseignant;
	}

	@Override
	public String toString() {
		return "Exercice [id_ex=" + id_ex + "]";
	}

}
