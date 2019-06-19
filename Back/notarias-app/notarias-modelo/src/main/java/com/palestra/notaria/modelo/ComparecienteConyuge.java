package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbbsnm61")
@NamedQueries({
	@NamedQuery(name="buscar",query="SELECT c FROM ComparecienteConyuge c WHERE c.conyugeCompra.idcompareciente = :idcompareciente " +
				"OR c.conyugeActual.idcompareciente=:idcompareciente OR c.sujeto.idcompareciente = :idcompareciente")})
public class ComparecienteConyuge implements Serializable{

	private static final long serialVersionUID = 7949084920116137095L;
	
	@Id
	@OneToOne
	@JoinColumn(name="idsujeto",referencedColumnName="idcompareciente")
	private Compareciente sujeto;
	
	@OneToOne
	@JoinColumn(name="idconyugecompra",referencedColumnName="idcompareciente")
	private Compareciente conyugeCompra;
	
	@OneToOne
	@JoinColumn(name="idconyugeactual", referencedColumnName="idcompareciente")
	private Compareciente conyugeActual;
	
	@Transient
	private Boolean issoltero;
	
	@Column(columnDefinition="INT")
	private Boolean ismismoconyuge;
	
	private String idsesion;
	
	private Timestamp tmstmp;
	
	public Boolean getIsmismoconyuge() {
		return ismismoconyuge;
	}
	public void setIsmismoconyuge(Boolean ismismoconyuge) {
		this.ismismoconyuge = ismismoconyuge;
	}
	public Boolean getIssoltero() {
		return issoltero;
	}
	public void setIssoltero(Boolean issoltero) {
		this.issoltero = issoltero;
	}
	public Compareciente getConyugeActual() {
		return conyugeActual;
	}
	public void setConyugeActual(Compareciente conyugeActual) {
		this.conyugeActual = conyugeActual;
	}
	public Compareciente getConyugeCompra() {
		return conyugeCompra;
	}
	public void setConyugeCompra(Compareciente conyugeCompra) {
		this.conyugeCompra = conyugeCompra;
	}

	public Compareciente getSujeto() {
		return sujeto;
	}

	public void setSujeto(Compareciente sujeto) {
		this.sujeto = sujeto;
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
