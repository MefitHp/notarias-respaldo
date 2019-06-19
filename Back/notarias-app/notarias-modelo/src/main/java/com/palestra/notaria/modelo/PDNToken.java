package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
@Entity
@Table(name="tbbsnm08c")
@NamedQueries({
	@NamedQuery(name="PDNToken.findById", query = "SELECT tk FROM PDNToken tk WHERE tk.identificador = :id"),
	@NamedQuery(name="PDNToken.findByPlantilla", query = "SELECT tk FROM PDNToken tk WHERE tk.plantilla = :plantilla ORDER BY tk.posicion"),
	@NamedQuery(name="PDNToken.findByPosicion", query = "SELECT tk FROM PDNToken tk WHERE tk.identificador = :id AND posicion = :posicion")
})
public class PDNToken implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Column(name="idtoken")
	@Id
	private String identificador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="iddocnot", referencedColumnName="iddocnot"),
		@JoinColumn(name="inversion", referencedColumnName="inversion")
		})
	private PlantillaDocumentoNotarial plantilla;
	
	@Column(name="inorden")
	private int orden;
	
	@Column(name="inposicion")
	private int posicion;
	
	@Column(name="intipvar")
	private String tipoVariable;
	
	@Column(name="dsnomvar", columnDefinition = "TEXT")
	private String nombreVariable;
	
	@Lob()
	@Column(name="dsvalvar", columnDefinition = "TEXT")
	private String valor;
	
	@Column(name="idsesion")
	private String sesion;
	
	@Column(name="tmstmp")
	private Timestamp fechaHora;

	
	
	public PDNToken() {
		setFechaHora(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public PlantillaDocumentoNotarial getPlantilla() {
		return plantilla;
	}

	public void setPlantilla(PlantillaDocumentoNotarial plantilla) {
		this.plantilla = plantilla;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public int getPosicion() {
		return posicion;
	}

	public void setPosicion(int posicion) {
		this.posicion = posicion;
	}

	public String getTipoVariable() {
		return tipoVariable;
	}

	public void setTipoVariable(String tipoVariable) {
		this.tipoVariable = tipoVariable;
	}

	public String getNombreVariable() {
		return nombreVariable;
	}

	public void setNombreVariable(String nombreVariable) {
		this.nombreVariable = nombreVariable;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	private void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}

	
	
}
