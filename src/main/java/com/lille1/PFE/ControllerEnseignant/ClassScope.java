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
	
	public void addConnaissance(Connaissance connaissance){
		this.connaisances.add(connaissance);
	}

	@Override
	public String toString() {
		return "ClassScope [message=" + connaisances + "]";
	}
	
	
	
}
