package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm89")
public class ManejoSesion implements Serializable{

	private static final long serialVersionUID = -3956685004417616236L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String idsesion;
	
	private String idusuario;
	
	private Timestamp fchinicio;
	
	private Timestamp fchfinprogr;
	
	private Timestamp fchtermino;
	
	private String inip;
	
	@Column(columnDefinition="LONGTEXT")
	private String agente;
	
	private Timestamp tmstmp;
	
	@Column(columnDefinition="INT")
	private Boolean islogout;

	public String getIdusuario() {
		return idusuario;
	}

	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getFchinicio() {
		return fchinicio;
	}

	public void setFchinicio(Timestamp fchinicio) {
		this.fchinicio = fchinicio;
	}

	public Timestamp getFchfinprogr() {
		return fchfinprogr;
	}

	public void setFchfinprogr(Timestamp fchfinprogr) {
		this.fchfinprogr = fchfinprogr;
	}

	public Timestamp getFchtermino() {
		return fchtermino;
	}

	public void setFchtermino(Timestamp fchtermino) {
		this.fchtermino = fchtermino;
	}

	public String getInip() {
		return inip;
	}

	public void setInip(String inip) {
		this.inip = inip;
	}

	public String getAgente() {
		return agente;
	}

	public void setAgente(String agente) {
		this.agente = agente;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Boolean getIslogout() {
		return islogout;
	}

	public void setIslogout(Boolean islogout) {
		this.islogout = islogout;
	}
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}

}
