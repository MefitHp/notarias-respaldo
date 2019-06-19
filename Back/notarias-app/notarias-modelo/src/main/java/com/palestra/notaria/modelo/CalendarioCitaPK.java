package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Embeddable;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

@Embeddable
public class CalendarioCitaPK implements Serializable {

	private static final long serialVersionUID = -2996922328354668371L;

	private String idcita;

	private Integer version;

	public CalendarioCitaPK() {
	}

	public String getIdcita() {
		return idcita;
	}

	public void setIdcita(String idcita) {
		this.idcita = idcita;
	}

	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CalendarioCitaPK)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		CalendarioCitaPK calendarioCitaPK = (CalendarioCitaPK) obj;
		return new EqualsBuilder()
				.append(getIdcita(), calendarioCitaPK.getIdcita())
				.append(getVersion(), calendarioCitaPK.getVersion())
				.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 145).append(getIdcita())
				.append(getVersion()).toHashCode();
	}

}
