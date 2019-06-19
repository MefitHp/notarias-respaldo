package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm53 database table.
 * 
 */
@Entity
@Table(name="tbbsnm53")
public class ValorSubFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idvalorsubform;

	private String idsesion;

	private Integer registro;

	private Timestamp tmstmp;

	
	@Column(columnDefinition="TEXT")
	private String valorcadena;

	private Double valordoble;

	private Integer valorentero;

	//bi-directional many-to-one association to Componente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idcomponente")
	private Componente componente;

//	//bi-directional many-to-one association to ConSubFormulario
//	@ManyToOne
//	@JoinColumn(name="idcofsubform")
//	private ConSubFormulario tbbsnm49;

	//bi-directional many-to-one association to Formulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idformulario")
	private Formulario formulario;

	public ValorSubFormulario() {
	}

	public String getIdvalorsubform() {
		return this.idvalorsubform;
	}

	public void setIdvalorsubform(String idvalorsubform) {
		this.idvalorsubform = idvalorsubform;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getValorcadena() {
		return this.valorcadena;
	}

	public void setValorcadena(String valorcadena) {
		this.valorcadena = valorcadena;
	}

	public Integer getRegistro() {
		return registro;
	}

	public void setRegistro(Integer registro) {
		this.registro = registro;
	}

	public Double getValordoble() {
		return valordoble;
	}

	public void setValordoble(Double valordoble) {
		this.valordoble = valordoble;
	}

	public Integer getValorentero() {
		return valorentero;
	}

	public void setValorentero(Integer valorentero) {
		this.valorentero = valorentero;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

}