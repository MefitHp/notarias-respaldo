package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm54 database table.
 * 
 */
@Entity
@Table(name="tbbsnm54")
public class SolicitudPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idsolpago;

	private String concepto;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean isanticipo;

	private Timestamp tmstmp;

	private Double valor;

	//bi-directional many-to-one association to Expediente
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;

	//bi-directional many-to-one association to DatoFiscalPago
	@OneToMany(mappedBy="solicitudPago")
	private List<DatoFiscalPago> listaDatosFiscalPago;
	
	@Transient
	private Double importesaldo;

	public SolicitudPago() {
	}

	public String getIdsolpago() {
		return this.idsolpago;
	}

	public void setIdsolpago(String idsolpago) {
		this.idsolpago = idsolpago;
	}

	public String getConcepto() {
		return this.concepto;
	}

	public void setConcepto(String concepto) {
		this.concepto = concepto;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIsanticipo() {
		return isanticipo;
	}

	public void setIsanticipo(Boolean isanticipo) {
		this.isanticipo = isanticipo;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Double getValor() {
		return this.valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public List<DatoFiscalPago> getListaDatosFiscalPago() {
		return listaDatosFiscalPago;
	}

	public void setListaDatosFiscalPago(List<DatoFiscalPago> listaDatosFiscalPago) {
		this.listaDatosFiscalPago = listaDatosFiscalPago;
	}
	
	public Double getImportesaldo() {
		return importesaldo;
	}

	public void setImportesaldo(Double importesaldo) {
		this.importesaldo = importesaldo;
	}

	

}