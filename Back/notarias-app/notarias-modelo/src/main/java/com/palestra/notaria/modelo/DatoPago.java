package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm56 database table.
 * 
 */
@Entity
@Table(name="tbbsnm56")
public class DatoPago implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddatopago;

	private String facturafolio;

	private String facturaserie;

	@Temporal(TemporalType.DATE)
	private Date fechapago;

	private String idsesion;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioelaboro")
	private Usuario usuarioelaboro;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idmediopago")
	private ElementoCatalogo mediopago;


	@Column(columnDefinition="INT")
	private Boolean isenviacorreo;

	private Timestamp tmstmp;

	@Lob
	private String txtnota;
	
	private String rutaarchivoxml;

	//bi-directional many-to-one association to DatoFiscalPago
	@ManyToOne(fetch= FetchType.LAZY)
	@JoinColumn(name="iddatofiscapago")
	private DatoFiscalPago datofiscapago;
	
	private Double importepago;

	public DatoPago() {
	}

	public String getIddatopago() {
		return this.iddatopago;
	}

	public void setIddatopago(String iddatopago) {
		this.iddatopago = iddatopago;
	}

	public String getFacturafolio() {
		return this.facturafolio;
	}

	public void setFacturafolio(String facturafolio) {
		this.facturafolio = facturafolio;
	}

	public String getFacturaserie() {
		return this.facturaserie;
	}

	public void setFacturaserie(String facturaserie) {
		this.facturaserie = facturaserie;
	}

	public Date getFechapago() {
		return this.fechapago;
	}

	public void setFechapago(Date fechapago) {
		this.fechapago = fechapago;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Usuario getUsuarioelaboro() {
		return usuarioelaboro;
	}

	public void setUsuarioelaboro(Usuario usuarioelaboro) {
		this.usuarioelaboro = usuarioelaboro;
	}

	public Boolean getIsenviacorreo() {
		return isenviacorreo;
	}

	public void setIsenviacorreo(Boolean isenviacorreo) {
		this.isenviacorreo = isenviacorreo;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getTxtnota() {
		return this.txtnota;
	}

	public void setTxtnota(String txtnota) {
		this.txtnota = txtnota;
	}

	public DatoFiscalPago getDatofiscapago() {
		return datofiscapago;
	}

	public void setDatofiscapago(DatoFiscalPago datofiscapago) {
		this.datofiscapago = datofiscapago;
	}

	public ElementoCatalogo getMediopago() {
		return mediopago;
	}

	public void setMediopago(ElementoCatalogo mediopago) {
		this.mediopago = mediopago;
	}

	public String getRutaarchivoxml() {
		return rutaarchivoxml;
	}

	public void setRutaarchivoxml(String rutaarchivoxml) {
		this.rutaarchivoxml = rutaarchivoxml;
	}
	
	public Double getImportepago() {
		return importepago;
	}

	public void setImportepago(Double importepago) {
		this.importepago = importepago;
	}


}