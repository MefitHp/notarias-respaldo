package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * The persistent class for the tbbsnm28 database table.
 * 
 */
@Entity
@Table(name = "tbbsnm28")
@NamedQueries({
	@NamedQuery(name="Persona.FindById", query = "SELECT p FROM Persona p WHERE p.idpersona = :idpersona"),
	@NamedQuery(name="Persona.FindByRfc", query = "SELECT p FROM Persona p WHERE p.dsrfc = :rfc"),
	@NamedQuery(name="Persona.FindByNombreCompleto", query = "SELECT p FROM Persona p WHERE CONCAT(p.dsnombre, ' ', p.dsapellidopat, ' ', p.dsapellidomat) LIKE :nombreCompleto"),
})
@XmlRootElement(name="Persona")
@XmlAccessorType(XmlAccessType.FIELD)
public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idpersona;

	private String dsapellidomat;

	private String dsapellidopat;

	private String dscurp;

	private String dsnombre;	

	private String dsnombrecompleto;

	private String dsrfc;
	
	private String dslugarnacimiento;
	
	private String emails;
	
	@Temporal(TemporalType.DATE)
	private Date fechanacimiento;

	private String idsesion;

	@Column(name="tmstmp")
	@XmlJavaTypeAdapter(value=TimestampAdapter.class)
	private Timestamp tmstmp;

	// bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipopersona")
	private ElementoCatalogo tipopersona;

	// bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idnacionalidad")
	private ElementoCatalogo nacionalidad;
	
	@Column(columnDefinition="INT")
	@XmlTransient
	private Boolean isextranjero;
	
	@XmlElement(name="isextranjero")
	public Boolean getIsextranjero() {
		return isextranjero;
	}

	public void setIsextranjero(Boolean isextranjero) {
		this.isextranjero = isextranjero;
	}

	@XmlElement(name="iscapitalextranjero")
	public Boolean getIscapitalextranjero() {
		return iscapitalextranjero;
	}

	public void setIscapitalextranjero(Boolean iscapitalextranjero) {
		this.iscapitalextranjero = iscapitalextranjero;
	}

	@Column(columnDefinition="INT")
	@XmlTransient
	private Boolean iscapitalextranjero;
	
	
	
	@Transient
	@XmlTransient
	private Domicilio domicilio;
	
	@ManyToOne
	@JoinColumn(name="idregimenfiscal")
	private ElementoCatalogo regimenfiscal;
	
	
	

	public Persona() {
	}
	
	public String getEmails() {
		return emails;
	}
	public void setEmails(String emails) {
		this.emails = emails;
	}
	public ElementoCatalogo getRegimenfiscal() {
		return regimenfiscal;
	}
	public void setRegimenfiscal(ElementoCatalogo regimenfiscal) {
		this.regimenfiscal = regimenfiscal;
	}
	
	@XmlElement(name="domicilio")
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}

	public String getIdpersona() {
		return this.idpersona;
	}

	public void setIdpersona(String idpersona) {
		this.idpersona = idpersona;
	}

	public String getDsapellidomat() {
		return this.dsapellidomat;
	}

	public void setDsapellidomat(String dsapellidomat) {
		this.dsapellidomat = dsapellidomat;
	}

	public String getDsapellidopat() {
		return this.dsapellidopat;
	}

	public void setDsapellidopat(String dsapellidopat) {
		this.dsapellidopat = dsapellidopat;
	}

	public String getDscurp() {
		return this.dscurp;
	}

	public void setDscurp(String dscurp) {
		this.dscurp = dscurp;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDsnombrecompleto() {
		return this.dsnombrecompleto;
	}

	public void setDsnombrecompleto(String dsnombrecompleto) {
		this.dsnombrecompleto = dsnombrecompleto;
	}

	public String getDsrfc() {
		return this.dsrfc;
	}

	public void setDsrfc(String dsrfc) {
		this.dsrfc = dsrfc;
	}

	public Date getFechanacimiento() {
		return this.fechanacimiento;
	}

	public void setFechanacimiento(Date fechanacimiento) {
		this.fechanacimiento = fechanacimiento;
	}

	public String getDslugarnacimiento() {
		return dslugarnacimiento;
	}
	public void setDslugarnacimiento(String dslugarnacimiento) {
		this.dslugarnacimiento = dslugarnacimiento;
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

	public ElementoCatalogo getTipopersona() {
		return tipopersona;
	}

	public void setTipopersona(ElementoCatalogo tipopersona) {
		this.tipopersona = tipopersona;
	}

	public ElementoCatalogo getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(ElementoCatalogo nacionalidad) {
		this.nacionalidad = nacionalidad;
	}
}