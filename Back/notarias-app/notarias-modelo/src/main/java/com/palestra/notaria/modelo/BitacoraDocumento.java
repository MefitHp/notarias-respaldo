package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm19 database table.
 * 
 */
@Entity
@Table(name="tbbsnm19")
public class BitacoraDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idbitacoradocumento;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto tbbsnm18;

	//bi-directional many-to-one association to DocumentoExpediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iddocumentoexp")
	private DocumentoExpediente tbbsnm23;

    public BitacoraDocumento() {
    }

	public String getIdbitacoradocumento() {
		return this.idbitacoradocumento;
	}

	public void setIdbitacoradocumento(String idbitacoradocumento) {
		this.idbitacoradocumento = idbitacoradocumento;
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

	public Acto getTbbsnm18() {
		return this.tbbsnm18;
	}

	public void setTbbsnm18(Acto tbbsnm18) {
		this.tbbsnm18 = tbbsnm18;
	}
	
	public DocumentoExpediente getTbbsnm23() {
		return this.tbbsnm23;
	}

	public void setTbbsnm23(DocumentoExpediente tbbsnm23) {
		this.tbbsnm23 = tbbsnm23;
	}
	
}