package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tbbsnm46 database table.
 * 
 */
@Embeddable
public class ConFormularioPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	private String idconFormulario;

	private Integer version;

	public ConFormularioPK() {
	}
	public String getIdconFormulario() {
		return this.idconFormulario;
	}
	public void setIdconFormulario(String idconFormulario) {
		this.idconFormulario = idconFormulario;
	}
	public Integer getVersion() {
		return this.version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ConFormularioPK)) {
			return false;
		}
		ConFormularioPK castOther = (ConFormularioPK)other;
		return 
			this.idconFormulario.equals(castOther.idconFormulario)
			&& (this.version == castOther.version);
	}

//	public int hashCode() {
//		final int prime = 31;
//		int hash = 17;
//		hash = hash * prime + this.idconFormulario.hashCode();
//		hash = hash * prime + this.version;
//		
//		return hash;
//	}
}