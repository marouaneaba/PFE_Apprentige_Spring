package com.lille1.PFE.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Exercice.class)
public abstract class Exercice_ {

	public static volatile SingularAttribute<Exercice, Long> id_ex;
	public static volatile ListAttribute<Exercice, Connaissance> connaissance;
	public static volatile ListAttribute<Exercice, Variable> variables;
	public static volatile SingularAttribute<Exercice, String> enonceExercice;
	public static volatile SingularAttribute<Exercice, String> XMLSolutionNettoyer;
	public static volatile SingularAttribute<Exercice, Enseignant> enseignant;
	public static volatile SingularAttribute<Exercice, String> XMLSolution;
	public static volatile SingularAttribute<Exercice, String> nomExercice;

}

