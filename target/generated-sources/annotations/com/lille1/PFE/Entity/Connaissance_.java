package com.lille1.PFE.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Connaissance.class)
public abstract class Connaissance_ {

	public static volatile SingularAttribute<Connaissance, String> score;
	public static volatile SingularAttribute<Connaissance, Boolean> valider;
	public static volatile SingularAttribute<Connaissance, Long> id_ExEtu;
	public static volatile SingularAttribute<Connaissance, String> ordre;
	public static volatile SingularAttribute<Connaissance, Enseignant> enseignant;
	public static volatile SingularAttribute<Connaissance, String> nom;

}

