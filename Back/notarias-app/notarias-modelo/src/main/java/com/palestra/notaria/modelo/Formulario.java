package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm51 database table.
 * 
 */
@Entity
@Table(name="tbbsnm51")
@NamedQueries(value={
		@NamedQuery(name="Formulario.findByIdFormulario", query="SELECT f FROM Formulario f WHERE f.idformulario = :idformulario"),
		@NamedQuery(name="Formulario.findByConFormulario", query = "SELECT f FROM Formulario f WHERE f.conFormulario.id.idconFormulario = :identificador AND f.conFormulario.id.version = :version")
})
public class Formulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idformulario;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	//bi-directional many-to-one association to ConFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idconformulario", referencedColumnName="idconFormulario"),
		@JoinColumn(name="versionform", referencedColumnName="version")
		})
	private ConFormulario conFormulario;

	//bi-directional many-to-one association to ValorFormulario
	@OneToMany(mappedBy="formulario", cascade = {CascadeType.ALL})
	private List<ValorFormulario> listaValFormulario;

	//bi-directional many-to-one association to ValorSubFormulario
	@OneToMany(mappedBy="formulario", cascade = {CascadeType.ALL})
	private List<ValorSubFormulario> listaValSubFormulario;

	public Formulario() {
		
	}

	public String getIdformulario() {
		return this.idformulario;
	}

	public void setIdformulario(String idformulario) {
		this.idformulario = idformulario;
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

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}

	public ConFormulario getConFormulario() {
		return conFormulario;
	}

	public void setConFormulario(ConFormulario conFormulario) {
		this.conFormulario = conFormulario;
	}

	public List<ValorFormulario> getListaValFormulario() {
		return listaValFormulario;
	}

	public void setListaValFormulario(List<ValorFormulario> listaValFormulario) {
		this.listaValFormulario = listaValFormulario;
	}

	public List<ValorSubFormulario> getListaValSubFormulario() {
		return listaValSubFormulario;
	}

	public void setListaValSubFormulario(
			List<ValorSubFormulario> listaValSubFormulario) {
		this.listaValSubFormulario = listaValSubFormulario;
	}

}