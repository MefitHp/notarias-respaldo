package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import com.palestra.notaria.modelo.Tramite;

import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm42 database table.
 * 
 */
@Entity
@Table(name="tbbsnm42")
public class BitacoraGeneral implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idbitgeneral;

	private String dsdescripcion;

	private String dsentidad;

	private String dsoperacion;

	private Timestamp fecha;


	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;

	//bi-directional many-to-one association to Expediente
	@ManyToOne
	@JoinColumn(name="idexpediente")
	private Expediente expediente;
	
	//bi-directional many-to-one association to Expediente
	@ManyToOne
	@JoinColumn(name="idtramite")
	private Tramite tramite;

	public BitacoraGeneral() {
	}

	public String getIdbitgeneral() {
		return this.idbitgeneral;
	}

	public void setIdbitgeneral(String idbitgeneral) {
		this.idbitgeneral = idbitgeneral;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsentidad() {
		return this.dsentidad;
	}

	public void setDsentidad(String dsentidad) {
		this.dsentidad = dsentidad;
	}

	public String getDsoperacion() {
		return this.dsoperacion;
	}

	public void setDsoperacion(String dsoperacion) {
		this.dsoperacion = dsoperacion;
	}

	public Timestamp getFecha() {
		return this.fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	
	

}