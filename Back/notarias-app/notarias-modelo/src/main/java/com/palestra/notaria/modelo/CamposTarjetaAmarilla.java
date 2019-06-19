package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm62")
public class CamposTarjetaAmarilla implements Serializable {

	private static final long serialVersionUID = -8365507512976160551L;

	@Id
	private Integer identificador;
	
	@ManyToOne
	@JoinColumn(name="idoperacion")
	private Operacion operacion;
	
	private String dscampo;
	
	private String dscodigo;
	
	private String idsesion;
	
	private Timestamp tmstmp;
	
	public String getDscampo() {
		return dscampo;
	}
	
	public void setDscampo(String dscampo) {
		this.dscampo = dscampo;
	}
	
	public String getDscodigo() {
		return dscodigo;
	}
	
	public void setDscodigo(String dscodigo) {
		this.dscodigo = dscodigo;
	}

	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
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
