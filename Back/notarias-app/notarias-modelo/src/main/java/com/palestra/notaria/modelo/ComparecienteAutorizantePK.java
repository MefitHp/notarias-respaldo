package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class ComparecienteAutorizantePK implements Serializable {

	private static final long serialVersionUID = -3463467185119415184L;

	private String idcompareciente;
	
	private String idautorizante;

	public String getIdcompareciente() {
		return idcompareciente;
	}

	public void setIdcompareciente(String idcompareciente) {
		this.idcompareciente = idcompareciente;
	}

	public String getIdautorizante() {
		return idautorizante;
	}

	public void setIdautorizante(String idautorizante) {
		this.idautorizante = idautorizante;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((idautorizante == null) ? 0 : idautorizante.hashCode());
		result = prime * result
				+ ((idcompareciente == null) ? 0 : idcompareciente.hashCode());
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
		ComparecienteAutorizantePK other = (ComparecienteAutorizantePK) obj;
		if (idautorizante == null) {
			if (other.idautorizante != null)
				return false;
		} else if (!idautorizante.equals(other.idautorizante))
			return false;
		if (idcompareciente == null) {
			if (other.idcompareciente != null)
				return false;
		} else if (!idcompareciente.equals(other.idcompareciente))
			return false;
		return true;
	}
	
}
