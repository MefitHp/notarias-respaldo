package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tbbsnm57")
public class CalendarioCita implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private CalendarioCitaPK id;
	
	private String dsestatus;

	private Timestamp fechainicio;

	private Timestamp fechatermino;

	private String dsactividad;
	
	@Column(columnDefinition = "INT")
	private Boolean isreprogramdo;

	@Column(length = 65535,columnDefinition="text")
	private String dsdescripcion;

	@Column(columnDefinition = "INT")
	private Boolean notificarcorreo;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idexpediente")
	private Expediente expediente;
	
	/** usuario que agendo la cita **/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idusragendo")
	private Usuario usragendo;
	
	private String idsesion;

	private Timestamp tmstmp;
	
	public CalendarioCitaPK getId() {
		return id;
	}
	
	public void setId(CalendarioCitaPK id) {
		this.id = id;
	}
	
	public String getDsestatus() {
		return dsestatus;
	}

	public void setDsestatus(String dsestatus) {
		this.dsestatus = dsestatus;
	}


	public Timestamp getFechainicio() {
		return fechainicio;
	}

	public void setFechainicio(Timestamp fechainicio) {
		this.fechainicio = fechainicio;
	}

	public Timestamp getFechatermino() {
		return fechatermino;
	}

	public void setFechatermino(Timestamp fechatermino) {
		this.fechatermino = fechatermino;
	}

	public Boolean getIsreprogramdo() {
		return isreprogramdo;
	}

	public void setIsreprogramdo(Boolean isreprogramdo) {
		this.isreprogramdo = isreprogramdo;
	}

	public String getDsactividad() {
		return dsactividad;
	}

	public void setDsactividad(String dsactividad) {
		this.dsactividad = dsactividad;
	}

	public String getDsdescripcion() {
		return dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public Boolean getNotificarcorreo() {
		return notificarcorreo;
	}

	public void setNotificarcorreo(Boolean notificarcorreo) {
		this.notificarcorreo = notificarcorreo;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Usuario getUsragendo() {
		return usragendo;
	}
	
	public void setUsragendo(Usuario usragendo) {
		this.usragendo = usragendo;
	}
	
}
