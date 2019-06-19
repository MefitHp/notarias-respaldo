package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the tbbsnm09 database table.
 * 
 */
@Entity
@Table(name="tbbsnm09")
public class Inmueble implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idinmueble;

	private String cdcatastral;

    @Lob()
	private String dscolindancias;

	private String dsctaagua;

	private String dsdescripcion;

	private String dsfoja;

	private String dslibro;

	private String dspredio;

	private String dsseccion;

	private String dssuperficie;

	private String dstomo;

	/** Precio del inmueble **/
	private Double valcatastral;

	private String dsvolumen;

	@Temporal( TemporalType.DATE)
	private Date fchinscripcion;

	private String idsesion;

	private Integer ininscripcion;

	private Timestamp tmstmp;
	
	@Lob
	private String dsdomcompleto;
	
	@Column(columnDefinition="INT")
	private Boolean isasistido;
	
	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idvocterreno")
	private ElementoCatalogo vocterreno;
	
	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idvochabitacional")
	private ElementoCatalogo vochabitacional;
	
	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idvoccomercial")
	private ElementoCatalogo voccomercial;

	//bi-directional many-to-one association to Domicilio
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iddomicilio")
	private Domicilio domicilio;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;
	
	private Double precio;
	
	private Double gravamen;
	
	private Double avaluos;
	
	@Column(length = 65535,columnDefinition="text")
	private String otrosavaluos;

    public Inmueble() {
    }

	public String getIdinmueble() {
		return this.idinmueble;
	}
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}
	
	public void setIdinmueble(String idinmueble) {
		this.idinmueble = idinmueble;
	}

	public String getCdcatastral() {
		return this.cdcatastral;
	}

	public void setCdcatastral(String cdcatastral) {
		this.cdcatastral = cdcatastral;
	}

	public String getDscolindancias() {
		return this.dscolindancias;
	}

	public void setDscolindancias(String dscolindancias) {
		this.dscolindancias = dscolindancias;
	}

	public String getDsctaagua() {
		return this.dsctaagua;
	}

	public void setDsctaagua(String dsctaagua) {
		this.dsctaagua = dsctaagua;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsfoja() {
		return this.dsfoja;
	}

	public void setDsfoja(String dsfoja) {
		this.dsfoja = dsfoja;
	}

	public String getDslibro() {
		return this.dslibro;
	}

	public void setDslibro(String dslibro) {
		this.dslibro = dslibro;
	}

	public String getDspredio() {
		return this.dspredio;
	}

	public void setDspredio(String dspredio) {
		this.dspredio = dspredio;
	}

	public String getDsseccion() {
		return this.dsseccion;
	}

	public void setDsseccion(String dsseccion) {
		this.dsseccion = dsseccion;
	}

	public String getDssuperficie() {
		return this.dssuperficie;
	}

	public void setDssuperficie(String dssuperficie) {
		this.dssuperficie = dssuperficie;
	}

	public String getDstomo() {
		return this.dstomo;
	}

	public void setDstomo(String dstomo) {
		this.dstomo = dstomo;
	}

	public Double getValcatastral() {
		return valcatastral;
	}

	public void setValcatastral(Double valcatastral) {
		this.valcatastral = valcatastral;
	}

	public ElementoCatalogo getVocterreno() {
		return vocterreno;
	}

	public void setVocterreno(ElementoCatalogo vocterreno) {
		this.vocterreno = vocterreno;
	}

	public ElementoCatalogo getVochabitacional() {
		return vochabitacional;
	}

	public void setVochabitacional(ElementoCatalogo vochabitacional) {
		this.vochabitacional = vochabitacional;
	}

	public ElementoCatalogo getVoccomercial() {
		return voccomercial;
	}

	public void setVoccomercial(ElementoCatalogo voccomercial) {
		this.voccomercial = voccomercial;
	}

	public String getDsvolumen() {
		return this.dsvolumen;
	}

	public void setDsvolumen(String dsvolumen) {
		this.dsvolumen = dsvolumen;
	}

	public Date getFchinscripcion() {
		return fchinscripcion;
	}
	
	public void setFchinscripcion(Date fchinscripcion) {
		this.fchinscripcion = fchinscripcion;
	}
	
	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Integer getIninscripcion() {
		return this.ininscripcion;
	}

	public void setIninscripcion(Integer ininscripcion) {
		this.ininscripcion = ininscripcion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public String getDsdomcompleto() {
		return dsdomcompleto;
	}
	
	public void setDsdomcompleto(String dsdomcompleto) {
		this.dsdomcompleto = dsdomcompleto;
	}
	
	public Boolean getIsasistido() {
		return isasistido;
	}
	
	public void setIsasistido(Boolean isasistido) {
		this.isasistido = isasistido;
	}

	public Domicilio getDomicilio() {
		return domicilio;
	}

	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	public Double getAvaluos() {
		return avaluos;
	}
	
	public void setAvaluos(Double avaluos) {
		this.avaluos = avaluos;
	}
	
	public Double getGravamen() {
		return gravamen;
	}
	
	public void setGravamen(Double gravamen) {
		this.gravamen = gravamen;
	}
	
	public String getOtrosavaluos() {
		return otrosavaluos;
	}
	
	public void setOtrosavaluos(String otrosavaluos) {
		this.otrosavaluos = otrosavaluos;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}