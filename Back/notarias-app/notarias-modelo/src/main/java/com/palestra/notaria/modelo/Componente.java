package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

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
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm50 database table.
 * 
 */
@Entity
@Table(name="tbbsnm50")
@NamedQueries({
	@NamedQuery(name="Componente.findBySubFormulario",  query="SELECT c FROM Componente c WHERE c.subformulario.idconsubform = :idsubformulario"),
})
public class Componente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcomponente;

	private String dsayuda;

	private String dscatalogo;

	private String dsetiqueta;
	
	@Transient
	private Expresion expresion;

	private String dsexpresionvalidacion;

	@Lob
	private String dslistavalores;

	private String dsnombrevariable;

	private String dstablabusqueda;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean isparasubform;

	@Column(columnDefinition="INT")
	private Boolean isrequerido;

	private Integer longitudmaxima;

	private Integer longitudminima;

	private Timestamp tmstmp;
	
	private Integer inposicion;
	
	private Integer inorden;

	//bi-directional many-to-one association to ConFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idconformulario", referencedColumnName="idconFormulario"),
		@JoinColumn(name="versionform", referencedColumnName="version")
		})
	private ConFormulario conFormulario;

	//bi-directional many-to-one association to ConSubFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idsubformulario")
	private ConSubFormulario subformulario;

	//bi-directional many-to-one association to CatalogoElemento
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="idtipocomponente")
	private ElementoCatalogo tipocomponente;

//	//bi-directional many-to-one association to ValorFormulario
//	@OneToMany(mappedBy="tbbsnm50")
//	private List<ValorFormulario> tbbsnm52s;
//
//	//bi-directional many-to-one association to ValorSubFormulario
//	@OneToMany(mappedBy="tbbsnm50")
//	private List<ValorSubFormulario> tbbsnm53s;

	public Componente() {
	}
	
	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}
	
	public String getIdcomponente() {
		return this.idcomponente;
	}

	public void setIdcomponente(String idcomponente) {
		this.idcomponente = idcomponente;
	}

	public String getDsayuda() {
		return this.dsayuda;
	}

	public void setDsayuda(String dsayuda) {
		this.dsayuda = dsayuda;
	}

	public String getDscatalogo() {
		return this.dscatalogo;
	}

	public void setDscatalogo(String dscatalogo) {
		this.dscatalogo = dscatalogo;
	}

	public String getDsetiqueta() {
		return this.dsetiqueta;
	}

	public void setDsetiqueta(String dsetiqueta) {
		this.dsetiqueta = dsetiqueta;
	}

	public String getDsexpresionvalidacion() {
		return this.dsexpresionvalidacion;
	}

	public void setDsexpresionvalidacion(String dsexpresionvalidacion) {
		this.dsexpresionvalidacion = dsexpresionvalidacion;
	}

	public String getDslistavalores() {
		return this.dslistavalores;
	}

	public void setDslistavalores(String dslistavalores) {
		this.dslistavalores = dslistavalores;
	}

	public String getDsnombrevariable() {
		return this.dsnombrevariable;
	}

	public void setDsnombrevariable(String dsnombrevariable) {
		this.dsnombrevariable = dsnombrevariable;
	}

	public String getDstablabusqueda() {
		return this.dstablabusqueda;
	}

	public void setDstablabusqueda(String dstablabusqueda) {
		this.dstablabusqueda = dstablabusqueda;
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

	public ConFormulario getConFormulario() {
		return conFormulario;
	}

	public void setConFormulario(ConFormulario conFormulario) {
		this.conFormulario = conFormulario;
	}

	public ConSubFormulario getSubformulario() {
		return subformulario;
	}

	public void setSubformulario(ConSubFormulario subformulario) {
		this.subformulario = subformulario;
	}

	public ElementoCatalogo getTipocomponente() {
		return tipocomponente;
	}

	public void setTipocomponente(ElementoCatalogo tipocomponente) {
		this.tipocomponente = tipocomponente;
	}

	public Boolean getIsparasubform() {
		return isparasubform;
	}

	public void setIsparasubform(Boolean isparasubform) {
		this.isparasubform = isparasubform;
	}

	public Boolean getIsrequerido() {
		return isrequerido;
	}

	public void setIsrequerido(Boolean isrequerido) {
		this.isrequerido = isrequerido;
	}

	public Integer getLongitudmaxima() {
		return longitudmaxima;
	}

	public void setLongitudmaxima(Integer longitudmaxima) {
		this.longitudmaxima = longitudmaxima;
	}

	public Integer getLongitudminima() {
		return longitudminima;
	}

	public void setLongitudminima(Integer longitudminima) {
		this.longitudminima = longitudminima;
	}

	public Integer getInposicion() {
		return inposicion;
	}
	
	public void setInposicion(Integer inposicion) {
		this.inposicion = inposicion;
	}

	public Integer getInorden() {
		return inorden;
	}
	
	public void setInorden(Integer inorden) {
		this.inorden = inorden;
	}
}