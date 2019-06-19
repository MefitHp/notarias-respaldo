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
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm24 database table.
 * 
 */
@Entity
@Table(name="tbbsnm24")
public class Escritura implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idescritura;

	private Double costo;

	@Column(unique=true)
	private String dsnumescritura;

    @Temporal( TemporalType.DATE)
	private Date fechacreacion;
    
    @Temporal( TemporalType.DATE)
	private Date fechadepase;
    
    
    private Long folioini;
    
    private Long foliofin;

	private String idsesion;
	
	@Column(columnDefinition="INT")
	private Boolean nopaso;
	
	
	private String idtachafirma;
	
	
	

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Libro
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idlibro")
	private Libro libro;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idnotario")
	private Usuario notario;

    public Escritura() {
    }

	public String getIdescritura() {
		return this.idescritura;
	}

	public void setIdescritura(String idescritura) {
		this.idescritura = idescritura;
	}

	public Double getCosto() {
		return this.costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDsnumescritura() {
		return this.dsnumescritura;
	}

	public void setDsnumescritura(String dsnumescritura) {
		this.dsnumescritura = dsnumescritura;
	}

	public Date getFechacreacion() {
		return this.fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
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
	
	public Libro getLibro() {
		return libro;
	}
	
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
		
	public Usuario getNotario() {
		return notario;
	}
	
	public void setNotario(Usuario notario) {
		this.notario = notario;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}
	
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}



	public Long getFolioini() {
		return folioini;
	}

	public void setFolioini(Long folioini) {
		this.folioini = folioini;
	}

	public Long getFoliofin() {
		return foliofin;
	}

	public void setFoliofin(Long foliofin) {
		this.foliofin = foliofin;
	}


	public Boolean getNopaso() {
		return nopaso;
	}

	public void setNopaso(Boolean nopaso) {
		this.nopaso = nopaso;
	}

	public String getIdtachafirma() {
		return idtachafirma;
	}

	public void setIdtachafirma(String idtachafirma) {
		this.idtachafirma = idtachafirma;
	}
	

	public Date getFechadepase() {
		return fechadepase;
	}

	public void setFechadepase(Date fechadepase) {
		this.fechadepase = fechadepase;
	}
	
}