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
@Table(name = "tbbsnm58")
public class InvitadoCalendarioCita implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String idinvitado;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusuario")
	private Usuario usuario;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcompareciente")
	private Compareciente compareciente;

	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idcita", referencedColumnName="idcita"),
		@JoinColumn(name="version", referencedColumnName="version")
		})
	private CalendarioCita cita;

	private String idsesion;

	private Timestamp tmstmp;

	public String getIdinvitado() {
		return idinvitado;
	}

	public void setIdinvitado(String idinvitado) {
		this.idinvitado = idinvitado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Compareciente getCompareciente() {
		return compareciente;
	}

	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
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
