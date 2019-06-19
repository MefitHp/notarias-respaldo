package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm60")
public class AcuseReciboEscritura implements Serializable {

	private static final long serialVersionUID = 8606360763835467658L;

	@Id
	private String idacuse;
	
	@ManyToOne
	@JoinColumn(referencedColumnName="idescritura",name="idescritura")
	private Escritura escritura;
	
	@Enumerated(EnumType.STRING)
	@Column(columnDefinition="ENUM")
	private EnumAcuseReciboEscritura enumentrega;
	
	private Date fchentrega;
	
	private String idsesion;
	
	private Timestamp tmstmp;
	
	public AcuseReciboEscritura(){
		
	}

	public String getIdacuse() {
		return idacuse;
	}

	public void setIdacuse(String idacuse) {
		this.idacuse = idacuse;
	}

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

	public EnumAcuseReciboEscritura getEnumentrega() {
		return enumentrega;
	}

	public void setEnumentrega(EnumAcuseReciboEscritura enumentrega) {
		this.enumentrega = enumentrega;
	}

	public Date getFchentrega() {
		return fchentrega;
	}

	public void setFchentrega(Date fchentrega) {
		this.fchentrega = fchentrega;
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
}
