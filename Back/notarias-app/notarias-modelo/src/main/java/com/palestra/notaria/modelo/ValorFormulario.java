package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;


/**
 * The persistent class for the tbbsnm52 database table.
 * 
 */
@Entity
@Table(name="tbbsnm52")
@NamedQueries(value={
		@NamedQuery(name="ValorFormulario.findByIdComponente", query="SELECT vf FROM ValorFormulario vf WHERE vf.formulario  = :formulario AND vf.componente.dsnombrevariable = :nombreVariable"),
		@NamedQuery(name="ValorFormulario.findByIdComponenteActo", query="SELECT vf FROM ValorFormulario vf WHERE vf.formulario.acto.idacto = :idacto AND vf.componente.dsnombrevariable = :nombreVariable"),		
})
public class ValorFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idvalorform;

	private String idsesion;

	private Timestamp tmstmp;

	@Column(columnDefinition="TEXT")
	private String valorcadena;

	private Double valordoble;

	private Integer valorentero;

	//bi-directional many-to-one association to Formulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idformulario")
	private Formulario formulario;

	//bi-directional many-to-one association to Componente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idcomponente")
	private Componente componente;

	public ValorFormulario() {
	}

	public String getIdvalorform() {
		return this.idvalorform;
	}

	public void setIdvalorform(String idvalorform) {
		this.idvalorform = idvalorform;
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

	public Formulario getFormulario() {
		return formulario;
	}

	public void setFormulario(Formulario formulario) {
		this.formulario = formulario;
	}

	public Componente getComponente() {
		return componente;
	}

	public void setComponente(Componente componente) {
		this.componente = componente;
	}

}