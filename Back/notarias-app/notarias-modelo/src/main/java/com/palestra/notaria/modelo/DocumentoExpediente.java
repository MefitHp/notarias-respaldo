package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * The persistent class for the tbbsnm23 database table.
 * 
 */
@Entity
@Table(name="tbbsnm23")
public class DocumentoExpediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddocumentoexpediente;

	private String dsnombreVar;

	private String dsvalorVar;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to BitacoraDocumento
	@OneToMany(mappedBy="tbbsnm23")
	private List<BitacoraDocumento> tbbsnm19s;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iddocumento")
	private Documento documento;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;

    public DocumentoExpediente() {
    }

	public String getIddocumentoexpediente() {
		return this.iddocumentoexpediente;
	}

	public void setIddocumentoexpediente(String iddocumentoexpediente) {
		this.iddocumentoexpediente = iddocumentoexpediente;
	}

	public String getDsnombreVar() {
		return this.dsnombreVar;
	}

	public void setDsnombreVar(String dsnombreVar) {
		this.dsnombreVar = dsnombreVar;
	}

	public String getDsvalorVar() {
		return this.dsvalorVar;
	}

	public void setDsvalorVar(String dsvalorVar) {
		this.dsvalorVar = dsvalorVar;
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

	public List<BitacoraDocumento> getTbbsnm19s() {
		return this.tbbsnm19s;
	}

	public void setTbbsnm19s(List<BitacoraDocumento> tbbsnm19s) {
		this.tbbsnm19s = tbbsnm19s;
	}
	
	public Acto getActo() {
		return this.acto;
	}

	public void setActo(Acto tbbsnm18) {
		this.acto = tbbsnm18;
	}
	
	public Expediente getExpediente() {
		return this.expediente;
	}

	public void setExpediente(Expediente tbbsnm32) {
		this.expediente = tbbsnm32;
	}
	
	public Documento getDocumento() {
		return documento;
	}
	
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	
}