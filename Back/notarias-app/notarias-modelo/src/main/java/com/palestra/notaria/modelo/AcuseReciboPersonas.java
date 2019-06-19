package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm60b")
public class AcuseReciboPersonas implements Serializable {

	private static final long serialVersionUID = -2620329601031829166L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	private String dsnombrecompleto;
	
	@ManyToOne
	@JoinColumn(name="idacuse",referencedColumnName="idacuse")
	private AcuseReciboEscritura acuseReciboEscritura;
	
	public AcuseReciboPersonas(){
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDsnombrecompleto() {
		return dsnombrecompleto;
	}

	public void setDsnombrecompleto(String dsnombrecompleto) {
		this.dsnombrecompleto = dsnombrecompleto;
	}

	public AcuseReciboEscritura getAcuseReciboEscritura() {
		return acuseReciboEscritura;
	}

	public void setAcuseReciboEscritura(AcuseReciboEscritura acuseReciboEscritura) {
		this.acuseReciboEscritura = acuseReciboEscritura;
	}
}
