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

@Table(name="tbbsnm70")
@Entity
public class DevolucionRechazadaFolio implements Serializable {

	private static final long serialVersionUID = 6964010089447932924L;

	@Id
	@OneToOne
	@JoinColumn(name="iddevolucionfolio")
	private BitacoraFolios devolucionFolio;
	
	@ManyToOne
	@JoinColumn(name="idusuariorechaza")
	private Usuario usuarioRechaza;
	
	@Column(columnDefinition="TEXT",name="dsmotivo")
	private String motivo;
	
	@Column(columnDefinition="INT")
	private Boolean isresuelta;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public BitacoraFolios getDevolucionFolio() {
		return devolucionFolio;
	}

	public void setDevolucionFolio(BitacoraFolios devolucionFolio) {
		this.devolucionFolio = devolucionFolio;
	}

	public Usuario getUsuarioRechaza() {
		return usuarioRechaza;
	}

	public void setUsuarioRechaza(Usuario usuarioRechaza) {
		this.usuarioRechaza = usuarioRechaza;
	}

	public String getMotivo() {
		return motivo;
	}

	public void setMotivo(String motivo) {
		this.motivo = motivo;
	}

	public Boolean getIsresuelta() {
		return isresuelta;
	}

	public void setIsresuelta(Boolean isresuelta) {
		this.isresuelta = isresuelta;
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
