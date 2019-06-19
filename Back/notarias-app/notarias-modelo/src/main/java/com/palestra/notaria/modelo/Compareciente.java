package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapKey;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

/**
 * The persistent class for the tbbsnm21 database table.
 * 
 */
@Entity
@Table(name = "tbbsnm21")
public class Compareciente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idcompareciente;

	private String alias;

	private String idsesion;
	
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idfirma")
	private Firma firma;

	private String dsocupacion;
	
	@Column(columnDefinition = "INT")
	private Boolean isrepresentante;

	private Timestamp tmstmp;

	// bi-directional many-to-one association to Acto
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idacto")
	private Acto acto;

	// bi-directional many-to-one association to Persona
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpersona")
	private Persona persona;

	@Column(columnDefinition="INT")
	private Boolean isavisoextranjero;
	
	@Column(columnDefinition="INT")
	private Boolean isextranjeroinscrito;
	
	public Boolean getIsextranjeroinscrito() {
		return isextranjeroinscrito;
	}
	public void setIsextranjeroinscrito(Boolean isextranjeroinscrito) {
		this.isextranjeroinscrito = isextranjeroinscrito;
	}
	public Boolean getIsavisoextranjero() {
		return isavisoextranjero;
	}
	public void setIsavisoextranjero(Boolean isavisoextranjero) {
		this.isavisoextranjero = isavisoextranjero;
	}

	// bi-directional many-to-one association to TipoCompareciente
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipocompareciente")
	private TipoCompareciente tipoCompareciente;


	
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.REMOVE })
	@JoinColumn(name = "idregri")
	private RegistroRi registroRi;
	
	@ManyToOne
	@JoinColumn(name="idtratamiento")
	private ElementoCatalogo tratamiento;
	
	@Column(columnDefinition="INT")
	private Boolean amboscompran;
	
	@ManyToOne
	@JoinColumn(name="idregimen")
	private ElementoCatalogo regimen;

	@ManyToOne
	@JoinColumn(name="idestadocivil")
	private ElementoCatalogo estadoCivil;
	
	@ManyToOne
	@JoinColumn(name="iddomicilio")
	private Domicilio domicilio;
	
	@ManyToOne
	@JoinColumn(name="idcontacto")
	private Contacto contacto;
	
	public Compareciente() {
	}
	public Contacto getContacto() {
		return contacto;
	}
	public void setContacto(Contacto contacto) {
		this.contacto = contacto;
	}
	public Domicilio getDomicilio() {
		return domicilio;
	}
	public void setDomicilio(Domicilio domicilio) {
		this.domicilio = domicilio;
	}
	public ElementoCatalogo getEstadoCivil() {
		return estadoCivil;
	}
	public void setEstadoCivil(ElementoCatalogo estadoCivil) {
		this.estadoCivil = estadoCivil;
	}
	public String getDsocupacion() {
		return dsocupacion;
	}
	public void setDsocupacion(String dsocupacion) {
		this.dsocupacion = dsocupacion;
	}
	public Boolean getAmboscompran() {
		return amboscompran;
	}
	public void setAmboscompran(Boolean amboscompran) {
		this.amboscompran = amboscompran;
	}
	public ElementoCatalogo getRegimen() {
		return regimen;
	}
	public void setRegimen(ElementoCatalogo regimen) {
		this.regimen = regimen;
	}

	public ElementoCatalogo getTratamiento() {
		return tratamiento;
	}
	public void setTratamiento(ElementoCatalogo tratamiento) {
		this.tratamiento = tratamiento;
	}

	public String getIdcompareciente() {
		return this.idcompareciente;
	}

	public void setIdcompareciente(String idcompareciente) {
		this.idcompareciente = idcompareciente;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIsrepresentante() {
		return isrepresentante;
	}

	public void setIsrepresentante(Boolean isrepresentante) {
		this.isrepresentante = isrepresentante;
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

	public Persona getPersona() {
		return persona;
	}

	public void setPersona(Persona persona) {
		this.persona = persona;
	}

	public TipoCompareciente getTipoCompareciente() {
		return tipoCompareciente;
	}

	public void setTipoCompareciente(TipoCompareciente tipoCompareciente) {
		this.tipoCompareciente = tipoCompareciente;
	}

	public RegistroRi getRegistroRi() {
		return registroRi;
	}

	public void setRegistroRi(RegistroRi registroRi) {
		this.registroRi = registroRi;
	}
	

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Compareciente)) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		Compareciente compareciente = (Compareciente) obj;
		return new EqualsBuilder().append(getIdcompareciente(),
				compareciente.getIdcompareciente())

		.isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(15, 145).append(getIdcompareciente())
				.toHashCode();
	}
	public Firma getFirma() {
		return firma;
	}
	public void setFirma(Firma firma) {
		this.firma = firma;
	}
}