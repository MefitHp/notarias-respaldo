package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgmcr")
@NamedQueries(value={
		@NamedQuery(name="RolGestionCompetencia.findById", query="SELECT gc FROM RolGestionCompetencia gc WHERE gc.identificador = :identificador"),
		@NamedQuery(name="RolGestionCompetencia.findByIdRol", query = "SELECT gc FROM RolGestionCompetencia gc WHERE gc.rol.idrol = :idrol"),
		@NamedQuery(name="RolGestionCompetencia.findByRolPrefijo", query = "SELECT gc FROM RolGestionCompetencia gc WHERE gc.rol.dsprefijo = :rolprefijo"),		
		@NamedQuery(name="RolGestionCompetencia.findByPantalla", query = "SELECT gc FROM RolGestionCompetencia gc WHERE gc.pantalla = :pantalla"),
		@NamedQuery(name="RolGestionCompetencia.findByRolPantalla", query = "SELECT gc FROM RolGestionCompetencia gc WHERE gc.rol.idrol = :idrol AND gc.pantalla = :pantalla")
})
public class RolGestionCompetencia implements Serializable {

	private static final long serialVersionUID = -6852222092241213061L;

	@Id
	@Column(name="idgescom")
	private String identificador;
	
	@ManyToOne(fetch=FetchType.EAGER)
//	@Column(name="idrol")
	@JoinColumn(name="idrol")
	private Rol rol;
	
	@Column(name="dsscreen")
	private String pantalla;
	
	@Column(name="inmodrol")
	private int modo;
	
	@Column(name="idsesion")
	private String sesion;

	@Column(name="tmstmp")
	private Timestamp fechaHora;
	
	public RolGestionCompetencia(){}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getPantalla() {
		return pantalla;
	}

	public void setPantalla(String pantalla) {
		this.pantalla = pantalla;
	}

	public int getModo() {
		return modo;
	}

	public void setModo(int modo) {
		this.modo = modo;
	}

	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	
	public Timestamp getFechaHora(){
		return fechaHora;
	} 
	
	void setFechaHora(Timestamp fechaHora){
		this.fechaHora = fechaHora;
	}
	
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
