package com.lille1.PFE.ControllerEtudiant;

public class ResultExercice {

	private int niveaux;
	private String Tete; // Félicitation + nom etudiant, exercice non correct;
	private String leMot; // vous avez attient le niveaux.. , vous étes encore
							// dans le niveaux ..
	private boolean Correct;// félicitation , exercice Non correct

	public ResultExercice(int niveaux, String tete, String leMot, boolean correct) {
		this.niveaux = niveaux;
		this.Tete = tete;
		this.leMot = leMot;
		this.Correct = correct;
	}

	public int getNiveaux() {
		return niveaux;
	}

	public void setNiveaux(int niveaux) {
		this.niveaux = niveaux;
	}

	public String getLeMot() {
		return leMot;
	}

	public void setLeMot(String leMot) {
		this.leMot = leMot;
	}

	public boolean isCorrect() {
		return Correct;
	}

	public void setCorrect(boolean correct) {
		Correct = correct;
	}

	public String getTete() {
		return Tete;
	}

	public void setTete(String tete) {
		Tete = tete;
	}

	@Override
	public String toString() {
		return "ResultExercice [niveaux=" + niveaux + ", Tete=" + Tete + ", leMot=" + leMot + ", Correct=" + Correct
				+ "]";
	}

}
