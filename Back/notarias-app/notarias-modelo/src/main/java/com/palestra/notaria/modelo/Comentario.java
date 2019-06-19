package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm01 database table.
 * 
 */
@Entity
@Table(name="tbbsnm01")
public class Comentario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcomentario;

	private String dstexto;

	private String idsesion;
	
	private String idobjeto;

	//bi-directional many-to-one association to TareaPendiente
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	private Timestamp tmstmp;
	

    public Comentario() {
    }

	public String getIdcomentario() {
		return this.idcomentario;
	}

	public void setIdcomentario(String idcomentario) {
		this.idcomentario = idcomentario;
	}

	public String getDstexto() {
		return this.dstexto;
	}

	public void setDstexto(String dstexto) {
		this.dstexto = dstexto;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public String getIdobjeto() {
		return idobjeto;
	}

	public void setIdobjeto(String idobjeto) {
		this.idobjeto = idobjeto;
	}

	
}