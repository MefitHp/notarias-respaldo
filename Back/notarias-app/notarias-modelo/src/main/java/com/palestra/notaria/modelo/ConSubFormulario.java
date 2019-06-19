package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
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

@Entity
@Table(name="tbbsnm49")
@NamedQueries({
	@NamedQuery(name="ConSubFormulario.findByIdConFormulario", query="SELECT sf FROM ConSubFormulario sf WHERE sf.conFormulario.id.idconFormulario = :idconformulario AND sf.conFormulario.id.version = :version"),
	@NamedQuery(name="ConSubFormulario.findByPosicion", query="SELECT sf FROM ConSubFormulario sf WHERE sf.conFormulario.id.idconFormulario = :idconformulario AND sf.conFormulario.id.version = :version AND sf.inposicion = :inposicion"),
	@NamedQuery(name="ConSubFormulario.findByFormulario", query="SELECT sf FROM ConSubFormulario sf WHERE sf.conFormulario.id.idconFormulario = :idconformulario AND sf.conFormulario.id.version = :version"),

	
})
public class ConSubFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idconsubform;

	private String idsesion;

	private String nombre;
	
	@Column(unique=true)
	private String dsnombrecorto;

	private int inposicion;
	
	private Timestamp tmstmp;

	//bi-directional many-to-one association to ConFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idconformulario", referencedColumnName="idconFormulario"),
		@JoinColumn(name="versionform", referencedColumnName="version")
		})
	private ConFormulario conFormulario;

	//bi-directional many-to-one association to Componente
	@OneToMany(mappedBy="subformulario", cascade= CascadeType.ALL)
	private List<Componente> listaComponentes;
//
//	//bi-directional many-to-one association to ValorSubFormulario
//	@OneToMany(mappedBy="tbbsnm49")
//	private List<ValorSubFormulario> tbbsnm53s;

	public ConSubFormulario() {
	}

	public String getIdconsubform() {
		return this.idconsubform;
	}

	public void setIdconsubform(String idconsubform) {
		this.idconsubform = idconsubform;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public String getDsnombrecorto() {
		return dsnombrecorto;
	}
	
	public void setDsnombrecorto(String dsnombrecorto) {
		this.dsnombrecorto = dsnombrecorto;
	}

	public int getInposicion() {
		return inposicion;
	}

	public void setInposicion(int inposicion) {
		this.inposicion = inposicion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public ConFormulario getConFormulario() {
		return conFormulario;
	}

	public void setConFormulario(ConFormulario conFormulario) {
		this.conFormulario = conFormulario;
	}

	public List<Componente> getListaComponentes() {
		return listaComponentes;
	}

	public void setListaComponentes(List<Componente> listaComponentes) {
		this.listaComponentes = listaComponentes;
	}

}