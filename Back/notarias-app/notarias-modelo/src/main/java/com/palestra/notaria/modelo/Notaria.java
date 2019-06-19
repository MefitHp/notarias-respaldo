package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.OneToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm16 database table.
 * 
 */
@Entity
@Table(name="tbbsnm16")
public class Notaria implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idnotaria;

	private String dslogo;

	private String idsesion;

	private String innumnot;
	
	@Column(name="isasociada", columnDefinition="int")
	private Boolean asociada;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Domicilio
	@ManyToOne
	@JoinColumn(name="iddomicilio")
	private Domicilio domicilio;
	
	@OneToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Usuario
//	@OneToMany(mappedBy="tbbsnm16")
//	private List<Usuario> tbcfgm03s;

    public Notaria() {
    }

	public String getIdnotaria() {
		return this.idnotaria;
	}

	public void setIdnotaria(String idnotaria) {
		this.idnotaria = idnotaria;
	}

	public String getDslogo() {
		return this.dslogo;
	}

	public void setDslogo(String dslogo) {
		this.dslogo = dslogo;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public String getInnumnot() {
		return this.innumnot;
	}

	public void setInnumnot(String innumnot) {
		this.innumnot = innumnot;
	}

	public Boolean getAsociada() {
		return asociada;
	}

	public void setAsociada(Boolean asociada) {
		this.asociada = asociada;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}
	
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
//	public List<Usuario> getTbcfgm03s() {
//		return this.tbcfgm03s;
//	}
//
//	public void setTbcfgm03s(List<Usuario> tbcfgm03s) {
//		this.tbcfgm03s = tbcfgm03s;
//	}
	
}