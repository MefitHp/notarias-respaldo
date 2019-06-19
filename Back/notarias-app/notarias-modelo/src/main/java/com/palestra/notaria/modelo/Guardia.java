package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm25 database table.
 * 
 */
@Entity
@Table(name="tbbsnm25")
public class Guardia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idguardia;

    @Temporal( TemporalType.DATE)
	private Date fechafinal;

    @Temporal( TemporalType.DATE)
	private Date fechainicial;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idabogado")
	private Usuario tbcfgm03;

    public Guardia() {
    }

	public String getIdguardia() {
		return this.idguardia;
	}

	public void setIdguardia(String idguardia) {
		this.idguardia = idguardia;
	}

	public Date getFechafinal() {
		return this.fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public Date getFechainicial() {
		return this.fechainicial;
	}

	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
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

	public Usuario getTbcfgm03() {
		return this.tbcfgm03;
	}

	public void setTbcfgm03(Usuario tbcfgm03) {
		this.tbcfgm03 = tbcfgm03;
	}
	
}