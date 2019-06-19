package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbcfgm08 database table.
 * 
 */
@Entity
@Table(name="tbcfgm08")
public class Variable implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idvariable;

	@Column(name="intipvar", columnDefinition="char")
	private String tipoVariable;
	
	private Integer cdlongitud;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean isnulo;

	@Column(columnDefinition="INT")
	private Boolean ispermitevalor;

	@Column(columnDefinition="INT")
	private Boolean isselmultiple;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	private String dsfiltrado;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne
	@JoinColumn(name="intipodato")
	private ElementoCatalogo tipoDato;

	//bi-directional many-to-one association to Validacion
//	@OneToMany(mappedBy="tbcfgm08")
//	private List<Validacion> tbcfgm09s;

    public Variable() {
    	setTipoVariable("var");
    	setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
    }

	public String getIdvariable() {
		return this.idvariable;
	}

	public void setIdvariable(String idvariable) {
		this.idvariable = idvariable;
	}

	public Integer getCdlongitud() {
		return this.cdlongitud;
	}

	public void setCdlongitud(Integer cdlongitud) {
		this.cdlongitud = cdlongitud;
	}


	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}
	
	public Boolean getIsnulo() {
		return isnulo;
	}
	
	public void setIsnulo(Boolean isnulo) {
		this.isnulo = isnulo;
	}
	
	public void setIspermitevalor(Boolean ispermitevalor) {
		this.ispermitevalor = ispermitevalor;
	}
	
	public Boolean getIspermitevalor() {
		return ispermitevalor;
	}
	
	public void setIsselmultiple(Boolean isselmultiple) {
		this.isselmultiple = isselmultiple;
	}
	
	public Boolean getIsselmultiple() {
		return isselmultiple;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public ElementoCatalogo getTipoDato() {
		return tipoDato;
	}
	
	public void setTipoDato(ElementoCatalogo tipoDato) {
		this.tipoDato = tipoDato;
	}
	
	public Boolean getIsactivo() {
		return isactivo;
	}
	
	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}
	
	public String getDsfiltrado() {
		return dsfiltrado;
	}
	
	public void setDsfiltrado(String dsfiltrado) {
		this.dsfiltrado = dsfiltrado;
	}

	public String getTipoVariable() {
		return tipoVariable;
	}

	public void setTipoVariable(String tipoVariable) {
		this.tipoVariable = tipoVariable;
	}
	
//	public List<Validacion> getTbcfgm09s() {
//		return this.tbcfgm09s;
//	}
//
//	public void setTbcfgm09s(List<Validacion> tbcfgm09s) {
//		this.tbcfgm09s = tbcfgm09s;
//	}

}