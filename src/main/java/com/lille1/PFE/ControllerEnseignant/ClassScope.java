package com.lille1.PFE.ControllerEnseignant;

import java.util.ArrayList;
import java.util.List;


import com.lille1.PFE.Entity.*;

public class ClassScope {


	public List<Connaissance> connaisances;

	public ClassScope() {
		this.connaisances = new ArrayList<>();
	}

	public ClassScope(List<Connaissance> connaisances) {
		this.connaisances = connaisances;
	}

	public List<Connaissance> getConnaissance() {
		return connaisances;
	}

	public void setConnaissance(List<Connaissance> connaisances) {
		this.connaisances = connaisances;
	}

	public void addConnaissance(Connaissance connaissance) {
		this.connaisances.add(connaissance);
	}

	public void deleteConnaissance(Long id) {
		for (int i = 0; i < this.connaisances.size(); i++) {
			if (this.connaisances.get(i).getId_ExEtu() == id) {
				this.connaisances.remove(i);
				break;
			}
		}
	}

	public void updateConnaissance(Long id, String nom, int ordre) {
		for (int i = 0; i < this.connaisances.size(); i++) {
			if (this.connaisances.get(i).getId_ExEtu() == id) {
				this.connaisances.get(i).setNom(nom);
				this.connaisances.get(i).setOrdre(ordre);
				break;
			}
		}
	}

	@Override
	public String toString() {
		return "ClassScope [message=" + connaisances + "]";
	}

}
