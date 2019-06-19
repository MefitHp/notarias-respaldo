package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm55 database table.
 * 
 */
@Entity
@Table(name="tbbsnm55")
public class DatoFiscalPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddatofiscapago;

	private String dscorreoelectronico;

	private String dscurp;

	@Lob
	private String dsdircompleta;

	private String dsnombrepagador;

	private String dsrfc;

	private String idsesion;

	private Integer innumpago;
		
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="status")
	private ElementoCatalogo status;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to SolicitudPago
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="idsolicitudpago")
	private SolicitudPago solicitudPago;

	//bi-directional many-to-one association to DatoPago
	@OneToMany(mappedBy="datofiscapago")
	private List<DatoPago> listaDatoPago;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idcompareciente")
	private Compareciente compareciente;

	public DatoFiscalPago() {
	}

	public String getIddatofiscapago() {
		return this.iddatofiscapago;
	}

	public void setIddatofiscapago(String iddatofiscapago) {
		this.iddatofiscapago = iddatofiscapago;
	}

	public String getDscorreoelectronico() {
		return this.dscorreoelectronico;
	}

	public void setDscorreoelectronico(String dscorreoelectronico) {
		this.dscorreoelectronico = dscorreoelectronico;
	}

	public String getDscurp() {
		return this.dscurp;
	}

	public void setDscurp(String dscurp) {
		this.dscurp = dscurp;
	}

	public String getDsdircompleta() {
		return this.dsdircompleta;
	}

	public void setDsdircompleta(String dsdircompleta) {
		this.dsdircompleta = dsdircompleta;
	}

	public String getDsnombrepagador() {
		return this.dsnombrepagador;
	}

	public void setDsnombrepagador(String dsnombrepagador) {
		this.dsnombrepagador = dsnombrepagador;
	}

	public String getDsrfc() {
		return this.dsrfc;
	}

	public void setDsrfc(String dsrfc) {
		this.dsrfc = dsrfc;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public ElementoCatalogo getStatus() {
		return status;
	}

	public void setStatus(ElementoCatalogo status) {
		this.status = status;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public SolicitudPago getSolicitudPago() {
		return solicitudPago;
	}

	public void setSolicitudPago(SolicitudPago solicitudPago) {
		this.solicitudPago = solicitudPago;
	}

	public Integer getInnumpago() {
		return innumpago;
	}

	public void setInnumpago(Integer innumpago) {
		this.innumpago = innumpago;
	}

	public List<DatoPago> getListaDatoPago() {
		return listaDatoPago;
	}

	public void setListaDatoPago(List<DatoPago> listaDatoPago) {
		this.listaDatoPago = listaDatoPago;
	}

	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}

}