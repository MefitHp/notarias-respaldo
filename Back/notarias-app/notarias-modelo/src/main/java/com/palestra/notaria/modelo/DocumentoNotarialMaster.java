package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm35 database table.
 * 
 */
@Entity
@Table(name="tbbsnm35")
public class DocumentoNotarialMaster implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddocnotmas;

    @Temporal( TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	private Timestamp tmstmp;

    @Lob()
	private String txtdoc;

	//bi-directional many-to-one association to Escritura
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

    public DocumentoNotarialMaster() {
    }

	public String getIddocnotmas() {
		return this.iddocnotmas;
	}

	public void setIddocnotmas(String iddocnotmas) {
		this.iddocnotmas = iddocnotmas;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
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

	public String getTxtdoc() {
		return this.txtdoc;
	}

	public void setTxtdoc(String txtdoc) {
		this.txtdoc = txtdoc;
	}

	public Escritura getEscritura() {
		return this.escritura;
	}

	public void setEscritura(Escritura tbbsnm24) {
		this.escritura = tbbsnm24;
	}
	
}