package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ProcesoRolPK implements Serializable{

	private static final long serialVersionUID = -1542593143620748139L;

	private String idrol;
	
	private String idproceso;

	public String getIdrol() {
		return idrol;
	}

	public void setIdrol(String idrol) {
		this.idrol = idrol;
	}

	public String getIdproceso() {
		return idproceso;
	}

	public void setIdproceso(String idproceso) {
		this.idproceso = idproceso;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idproceso == null) ? 0 : idproceso.hashCode());
		result = prime * result + ((idrol == null) ? 0 : idrol.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcesoRolPK other = (ProcesoRolPK) obj;
		if (idproceso == null) {
			if (other.idproceso != null)
				return false;
		} else if (!idproceso.equals(other.idproceso))
			return false;
		if (idrol == null) {
			if (other.idrol != null)
				return false;
		} else if (!idrol.equals(other.idrol))
			return false;
		return true;
	}

}
