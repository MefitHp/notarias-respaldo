package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class PlantillaDocumentoNotarialSubOperacionPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected String iddocnot;
	protected Integer inversion;
	
	protected String idSubOperacion;
	
	public PlantillaDocumentoNotarialSubOperacionPk() {
	}

	public PlantillaDocumentoNotarialSubOperacionPk(String iddocnot,
			Integer inversion, String suboperacion) {
		super();
		this.iddocnot = iddocnot;
		this.inversion = inversion;
		this.idSubOperacion = suboperacion;
	}

	public String getIddocnot() {
		return iddocnot;
	}

	public void setIddocnot(String iddocnot) {
		this.iddocnot = iddocnot;
	}

	public Integer getInversion() {
		return inversion;
	}

	public void setInversion(Integer inversion) {
		this.inversion = inversion;
	}

	public String getIdSubOperacion() {
		return idSubOperacion;
	}

	public void setIdSubOperacion(String idSubOperacion) {
		this.idSubOperacion = idSubOperacion;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlantillaDocumentoNotarialSubOperacionPk)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PlantillaDocumentoNotarialSubOperacionPk pk = (PlantillaDocumentoNotarialSubOperacionPk) obj;
		return new EqualsBuilder().append(getIddocnot(), pk.getIddocnot())
				.append(getInversion(), pk.getInversion())
				.append(getIdSubOperacion(), pk.getIdSubOperacion()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 145).append(getIddocnot())
				.append(getInversion())
				.append(idSubOperacion).toHashCode();
	}
	
}
