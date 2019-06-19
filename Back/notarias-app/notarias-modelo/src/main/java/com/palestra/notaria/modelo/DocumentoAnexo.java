package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbbsnm59")
public class DocumentoAnexo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String idanexo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "iddocnotmas")
	private DocumentoNotarialMaster docnotmas;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idcita", referencedColumnName="idcita"),
		@JoinColumn(name="version", referencedColumnName="version")
		})
	private CalendarioCita cita;

	private String idsesion;

	private Timestamp tmstmp;

	public String getIdanexo() {
		return idanexo;
	}

	public void setIdanexo(String idanexo) {
		this.idanexo = idanexo;
	}

	public DocumentoNotarialMaster getDocnotmas() {
		return docnotmas;
	}

	public void setDocnotmas(DocumentoNotarialMaster docnotmas) {
		this.docnotmas = docnotmas;
	}

	public CalendarioCita getCita() {
		return cita;
	}

	public void setCita(CalendarioCita cita) {
		this.cita = cita;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

}
