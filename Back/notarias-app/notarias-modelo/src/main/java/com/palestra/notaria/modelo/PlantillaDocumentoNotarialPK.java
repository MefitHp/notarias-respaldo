package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class PlantillaDocumentoNotarialPK implements Serializable {

	private static final long serialVersionUID = -2502987079022083610L;
	protected String iddocnot;
	protected Integer inversion;

	public PlantillaDocumentoNotarialPK() {
	}

	public PlantillaDocumentoNotarialPK(String iddocnot, Integer inversion) {
		super();
		this.iddocnot = iddocnot;
		this.inversion = inversion;
	}

	public String getIddocnot() {
		return iddocnot;
	}

	public Integer getInversion() {
		return inversion;
	}
	
	public void setInversion(Integer inversion) {
		this.inversion = inversion;
	}
	
	public void setIddocnot(String iddocnot) {
		this.iddocnot = iddocnot;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PlantillaDocumentoNotarialPK)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		PlantillaDocumentoNotarialPK pk = (PlantillaDocumentoNotarialPK) obj;
		return new EqualsBuilder().append(getIddocnot(), pk.getIddocnot())
				.append(this.getInversion(), pk.getInversion()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 145).append(this.getIddocnot())
				.append(this.getInversion()).toHashCode();
	}

}
