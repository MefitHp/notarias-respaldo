package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class DocumentoObjetoPK implements Serializable {

	private static final long serialVersionUID = -178788096398006636L;
	
	private String iddocobjeto;
	
	private Integer version;

	public String getIddocobjeto() {
		return iddocobjeto;
	}

	public void setIddocobjeto(String iddocobjeto) {
		this.iddocobjeto = iddocobjeto;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((iddocobjeto == null) ? 0 : iddocobjeto.hashCode());
		result = prime * result + ((version == null) ? 0 : version.hashCode());
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
		DocumentoObjetoPK other = (DocumentoObjetoPK) obj;
		if (iddocobjeto == null) {
			if (other.iddocobjeto != null)
				return false;
		} else if (!iddocobjeto.equals(other.iddocobjeto))
			return false;
		if (version == null) {
			if (other.version != null)
				return false;
		} else if (!version.equals(other.version))
			return false;
		return true;
	}

}
