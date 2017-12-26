package com.lille1.PFE.Entity;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Personne {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idEns;
	@Column
	private String nom;
	@Column
	private String password;
	@Column
	private String email;
	@Column
	private String role;
	@Column
    private boolean enabled = true;

    @Column
    private boolean accountNonLocked = true;

    @Column
    private boolean credentialsNonExpired = true;
    
	
	public Personne() {}


	public Personne(String nom, String password, String email, String role) {
		this.nom = nom;
		this.password = password;
		this.email = email;
		this.role = role;
	}


	public Long getIdEns() {
		return idEns;
	}


	public void setIdEns(Long idEns) {
		this.idEns = idEns;
	}


	public String getNom() {
		return nom;
	}


	public void setNom(String nom) {
		this.nom = nom;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}


	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}


	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}


	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}


	@Override
    public boolean equals(Object o) {
        boolean rValue = false;
        if (o != null) {
            if (o instanceof Personne) {
                Personne tEntity = (Personne) o;
                if (Objects.equals(tEntity.idEns, this.idEns)) {
                    rValue = true;
                }
            }
        }
        return rValue;
    }


	@Override
	public String toString() {
		return "Personne [idEns=" + idEns + ", nom=" + nom + ", password=" + password + ", email=" + email + ", role="
				+ role + ", enabled=" + enabled + ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired="
				+ credentialsNonExpired + "]";
	}


	
	
	
}
