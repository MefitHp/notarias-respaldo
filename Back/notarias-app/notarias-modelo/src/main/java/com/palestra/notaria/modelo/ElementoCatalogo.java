package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * The persistent class for the tbcfgm91 database table.
 * 
 */
@Entity
@Table(name="tbcfgm91")
@XmlRootElement(name="ElementoCatalogo")
public class ElementoCatalogo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idelemento;

	private String dselemento;
	
	private String dscodigo;

	@Column(columnDefinition="INT")
	private Boolean iseliminado;
	
	private String idsesion;

	@XmlTransient
	private Timestamp tmstmp;

//	//bi-directional many-to-one association to Documento
//	@OneToMany(mappedBy="tbcfgm91")
//	private List<Documento> tbbsnm22s;
//
//	//bi-directional many-to-one association to Persona
//	@OneToMany(mappedBy="tbcfgm911")
//	private List<Persona> tbbsnm28s1;
//
//	//bi-directional many-to-one association to Persona
//	@OneToMany(mappedBy="tbcfgm912")
//	private List<Persona> tbbsnm28s2;
//
//	//bi-directional many-to-one association to Persona
//	@OneToMany(mappedBy="tbcfgm913")
//	private List<Persona> tbbsnm28s3;
//
//	//bi-directional many-to-one association to Tramite
//	@OneToMany(mappedBy="tbcfgm91")
//	private List<Tramite> tbbsnm40s;
//
//	//bi-directional many-to-one association to Variable
//	@OneToMany(mappedBy="tbcfgm91")
//	private List<Variable> tbcfgm08s;
//
//	//bi-directional many-to-one association to Validacion
//	@OneToMany(mappedBy="tbcfgm91")
//	private List<Validacion> tbcfgm09s;

	//bi-directional many-to-many association to Rol
//    @ManyToMany
//	@JoinTable(
//		name="tbcfgm17"
//		, joinColumns={
//			@JoinColumn(name="idproceso")
//			}
//		, inverseJoinColumns={
//			@JoinColumn(name="idrol")
//			}
//		)
//	private List<Rol> rol;

	//bi-directional many-to-one association to Catalogo
	@ManyToOne
	@JoinColumn(name="idcatalogo")
	private Catalogo catalogo;

    public ElementoCatalogo() {
    }
    
    public ElementoCatalogo(String id){
    	this.idelemento = id;
    }

    @XmlElement(name="idelemento")
	public String getIdelemento() {
		return this.idelemento;
	}

	public void setIdelemento(String idelemento) {
		this.idelemento = idelemento;
	}

	@XmlElement(name="dscodigo")
	public String getDscodigo() {
		return dscodigo;
	}
	
	public void setDscodigo(String dscodigo) {
		this.dscodigo = dscodigo;
	}
	
	@XmlElement(name="dselemento")
	public String getDselemento() {
		return this.dselemento;
	}

	public void setDselemento(String dselemento) {
		this.dselemento = dselemento;
	}

	@XmlElement(name="iseliminado")
	public Boolean getIseliminado() {
		return iseliminado;
	}
	
	public void setIseliminado(Boolean iseliminado) {
		this.iseliminado = iseliminado;
	}
	
	@XmlElement(name="idsesion")
	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	@XmlElement(name="tmstmp")
	@XmlJavaTypeAdapter(value=TimestampAdapter.class)
	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

//	public List<Documento> getTbbsnm22s() {
//		return this.tbbsnm22s;
//	}
//
//	public void setTbbsnm22s(List<Documento> tbbsnm22s) {
//		this.tbbsnm22s = tbbsnm22s;
//	}
//	
//	public List<Persona> getTbbsnm28s1() {
//		return this.tbbsnm28s1;
//	}
//
//	public void setTbbsnm28s1(List<Persona> tbbsnm28s1) {
//		this.tbbsnm28s1 = tbbsnm28s1;
//	}
//	
//	public List<Persona> getTbbsnm28s2() {
//		return this.tbbsnm28s2;
//	}
//
//	public void setTbbsnm28s2(List<Persona> tbbsnm28s2) {
//		this.tbbsnm28s2 = tbbsnm28s2;
//	}
//	
//	public List<Persona> getTbbsnm28s3() {
//		return this.tbbsnm28s3;
//	}
//
//	public void setTbbsnm28s3(List<Persona> tbbsnm28s3) {
//		this.tbbsnm28s3 = tbbsnm28s3;
//	}
//	
//	public List<Tramite> getTbbsnm40s() {
//		return this.tbbsnm40s;
//	}
//
//	public void setTbbsnm40s(List<Tramite> tbbsnm40s) {
//		this.tbbsnm40s = tbbsnm40s;
//	}
//	
//	public List<Variable> getTbcfgm08s() {
//		return this.tbcfgm08s;
//	}
//
//	public void setTbcfgm08s(List<Variable> tbcfgm08s) {
//		this.tbcfgm08s = tbcfgm08s;
//	}
//	
//	public List<Validacion> getTbcfgm09s() {
//		return this.tbcfgm09s;
//	}
//
//	public void setTbcfgm09s(List<Validacion> tbcfgm09s) {
//		this.tbcfgm09s = tbcfgm09s;
//	}
	
//	public List<Rol> getRol() {
//		return rol;
//	}
//	
//	public void setRol(List<Rol> rol) {
//		this.rol = rol;
//	}

	@XmlElement(name="catalogo")
	public Catalogo getCatalogo() {
		return catalogo;
	}
	public void setCatalogo(Catalogo tbcfgm90) {
		this.catalogo = tbcfgm90;
	}
}