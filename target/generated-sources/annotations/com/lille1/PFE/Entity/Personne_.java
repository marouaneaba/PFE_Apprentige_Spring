package com.lille1.PFE.Entity;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Personne.class)
public abstract class Personne_ {

	public static volatile SingularAttribute<Personne, String> password;
	public static volatile SingularAttribute<Personne, String> role;
	public static volatile SingularAttribute<Personne, Boolean> credentialsNonExpired;
	public static volatile SingularAttribute<Personne, Long> idEns;
	public static volatile SingularAttribute<Personne, String> nom;
	public static volatile SingularAttribute<Personne, String> email;
	public static volatile SingularAttribute<Personne, Boolean> enabled;
	public static volatile SingularAttribute<Personne, Boolean> accountNonLocked;

}

