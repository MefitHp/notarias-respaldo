package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;


@Entity
@Table(name = "tbbsnm40a")
@XmlRootElement(name="TramiteUsuario")
public class TramiteUsuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtramiteusuario;
	private String idsesion;
	private Timestamp tmstmp;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	@XmlElement(name="usuario")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idtramite")
	@XmlElement(name="tramite")
	private Tramite tramite;
	
	public TramiteUsuario(){}
	
	public TramiteUsuario(Usuario usu, Tramite t){
		this.usuario = usu;
		this.tramite = t;
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

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getIdtramiteusuario() {
		return idtramiteusuario;
	}

	public void setIdtramiteusuario(String idtramiteusuario) {
		this.idtramiteusuario = idtramiteusuario;
	}

}