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
@Table(name="tbbsnm08b")
@NamedQueries({
	@NamedQuery(name="PDNBloqueTexto.findById", query = "SELECT bt FROM PDNBloqueTexto bt WHERE bt.identificador = :id"),
	@NamedQuery(name="PDNBloqueTexto.findByPosicion", query = "SELECT bt FROM PDNBloqueTexto bt WHERE bt.identificador = :id AND posicion = :posicion")
})
public class PDNBloqueTexto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name="idbtexto")
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
	
	@Lob()
	@Column(name="dsvaltxt", columnDefinition = "TEXT")
	private String bloqueTexto;
	
	@Column(name="idsesion")
	private String sesion;
	
	@Column(name="tmstmp")
	private Timestamp fechaHora;
	
	
	public PDNBloqueTexto(){
		fechaHora = new Timestamp(Calendar.getInstance().getTimeInMillis());
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


	public String getBloqueTexto() {
		return bloqueTexto;
	}


	public void setBloqueTexto(String bloqueTexto) {
		this.bloqueTexto = bloqueTexto;
	}


	public String getSesion() {
		return sesion;
	}


	public void setSesion(String sesion) {
		this.sesion = sesion;
	}      
	
}
