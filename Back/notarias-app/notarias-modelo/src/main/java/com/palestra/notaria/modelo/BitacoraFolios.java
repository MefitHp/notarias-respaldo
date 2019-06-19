package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Table(name="tbbsnm69")
@Entity
public class BitacoraFolios implements Serializable {

	private static final long serialVersionUID = 8583048380063896543L;

	@Id
	private String iddevolucionfolio;
	
	@ManyToOne
	@JoinColumn(name="idusuariorecibe")
	private Usuario usuarioRecibe;
	
	@ManyToOne
	@JoinColumn(name="idusuarioentrega")
	private Usuario usuarioEntrega;
	
	private String dscomentario;
	
	private Timestamp fechaoperacion;
	
	private String tipooperacion;
	
	@Column(name="dsnumeroescritura")
	private String numeroescritura;
	
	public String getNumeroescritura() {
		return numeroescritura;
	}

	public void setNumeroescritura(String numeroescritura) {
		this.numeroescritura = numeroescritura;
	}

	private String idsesion;
	
	private Timestamp tmstmp;
	
		

	public String getIddevolucionfolio() {
		return iddevolucionfolio;
	}

	public void setIddevolucionfolio(String iddevolucionfolio) {
		this.iddevolucionfolio = iddevolucionfolio;
	}

	public Usuario getUsuarioRecibe() {
		return usuarioRecibe;
	}

	public void setUsuarioRecibe(Usuario usuarioRecibe) {
		this.usuarioRecibe = usuarioRecibe;
	}

	public Usuario getUsuarioEntrega() {
		return usuarioEntrega;
	}

	public void setUsuarioEntrega(Usuario usuarioEntrega) {
		this.usuarioEntrega = usuarioEntrega;
	}

	public String getDscomentario() {
		return dscomentario;
	}

	public void setDscomentario(String dscomentario) {
		this.dscomentario = dscomentario;
	}

	public Timestamp getFechaoperacion() {
		return fechaoperacion;
	}

	public void setFechaoperacion(Timestamp fechaoperacion) {
		this.fechaoperacion = fechaoperacion;
	}

	public String getTipooperacion() {
		return tipooperacion;
	}

	public void setTipooperacion(String tipooperacion) {
		this.tipooperacion = tipooperacion;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	
}
