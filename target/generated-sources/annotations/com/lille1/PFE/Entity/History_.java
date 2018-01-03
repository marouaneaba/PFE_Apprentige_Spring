package com.lille1.PFE.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(History.class)
public abstract class History_ {

	public static volatile SingularAttribute<History, Integer> score;
	public static volatile SingularAttribute<History, Connaissance> connaissance;
	public static volatile SingularAttribute<History, Exercice> exercice;
	public static volatile SingularAttribute<History, Long> id;
	public static volatile SingularAttribute<History, Etudiant> etudiant;

}

