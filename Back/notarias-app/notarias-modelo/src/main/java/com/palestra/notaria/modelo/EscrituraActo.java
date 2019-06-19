package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm36 database table.
 * 
 */
@Entity
@Table(name="tbbsnm36")
public class EscrituraActo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idescacto;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	//bi-directional many-to-one association to Escritura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

    public EscrituraActo() {
    }

	public String getIdescacto() {
		return this.idescacto;
	}

	public void setIdescacto(String idescacto) {
		this.idescacto = idescacto;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Acto getActo() {
		return acto;
	}
	
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	public Escritura getEscritura() {
		return escritura;
	}
	
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	
}