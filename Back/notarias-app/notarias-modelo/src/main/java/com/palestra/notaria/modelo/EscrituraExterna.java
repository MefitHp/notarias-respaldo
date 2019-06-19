package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="tbbsnm72")
public class EscrituraExterna implements Serializable {

	private static final long serialVersionUID = 95509133494292006L;

	@Id
	private String idescrituraexterna;

	private Double costo;

	@Column(unique=true)
	private String dsnumescritura;

    @Temporal( TemporalType.DATE)
	private Date fechacreacion;

    @Temporal( TemporalType.DATE)
	private Date fechaencuadernado;

    @Temporal( TemporalType.DATE)
	private Date fechafirma;

    @Temporal( TemporalType.DATE)
	private Date fechaimpresion;
    
    private Long folioini;
    
    private Long foliofin;

	private String idsesion;
	
	@Column(columnDefinition="INT")
	private Boolean isfirmaditto;
	
	@Temporal( TemporalType.DATE)
	private Date fechafirmaditto;

	private Timestamp tmstmp;

	@Column(name="idlibro")
	private String libro;

	@Column(name="idexpediente")
	private String expediente;

	@Column(name="idnotario")
	private String notario;

	public String getIdescrituraExterna() {
		return idescrituraexterna;
	}

	public void setIdescrituraExterna(String idescrituraexterna) {
		this.idescrituraexterna = idescrituraexterna;
	}

	public Double getCosto() {
		return costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}

	public String getDsnumescritura() {
		return dsnumescritura;
	}

	public void setDsnumescritura(String dsnumescritura) {
		this.dsnumescritura = dsnumescritura;
	}

	public Date getFechacreacion() {
		return fechacreacion;
	}

	public void setFechacreacion(Date fechacreacion) {
		this.fechacreacion = fechacreacion;
	}

	public Date getFechaencuadernado() {
		return fechaencuadernado;
	}

	public void setFechaencuadernado(Date fechaencuadernado) {
		this.fechaencuadernado = fechaencuadernado;
	}

	public Date getFechafirma() {
		return fechafirma;
	}

	public void setFechafirma(Date fechafirma) {
		this.fechafirma = fechafirma;
	}

	public Date getFechaimpresion() {
		return fechaimpresion;
	}

	public void setFechaimpresion(Date fechaimpresion) {
		this.fechaimpresion = fechaimpresion;
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

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIsfirmaditto() {
		return isfirmaditto;
	}

	public void setIsfirmaditto(Boolean isfirmaditto) {
		this.isfirmaditto = isfirmaditto;
	}

	public Date getFechafirmaditto() {
		return fechafirmaditto;
	}

	public void setFechafirmaditto(Date fechafirmaditto) {
		this.fechafirmaditto = fechafirmaditto;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	public String getExpediente() {
		return expediente;
	}
	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}
	public String getLibro() {
		return libro;
	}
	public void setLibro(String libro) {
		this.libro = libro;
	}
	public String getNotario() {
		return notario;
	}
	public void setNotario(String notario) {
		this.notario = notario;
	}

}
