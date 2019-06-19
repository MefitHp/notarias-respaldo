package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm29 database table.
 * 
 */
@Entity
@Table(name="tbbsnm29")
public class Presupuesto implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idpresupuesto;

	@Temporal(TemporalType.DATE)
	private Date fechaCreacion;

	@Temporal(TemporalType.DATE)
	private Date fechaPago;

	private String idsesion;

	private Double importe;

	@Column(columnDefinition="INT")
	private Boolean isaplicaiva;

	@Column(columnDefinition="INT")
	private Boolean ispagado;

	private Double iva;

	private Double subtotal;
	
	@Lob()
	private String comentario;

	private Timestamp tmstmp;

	private Double total;

	//bi-directional many-to-one association to Tbcfgm91
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="conceptoPago")
	private ElementoCatalogo conceptoPago;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	public Presupuesto() {
	}

	public String getIdpresupuesto() {
		return this.idpresupuesto;
	}

	public void setIdpresupuesto(String idpresupuesto) {
		this.idpresupuesto = idpresupuesto;
	}

	public Date getFechaCreacion() {
		return this.fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public Date getFechaPago() {
		return this.fechaPago;
	}

	public void setFechaPago(Date fechaPago) {
		this.fechaPago = fechaPago;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Double getImporte() {
		return importe;
	}

	public void setImporte(Double importe) {
		this.importe = importe;
	}

	public Boolean getIsaplicaiva() {
		return isaplicaiva;
	}

	public void setIsaplicaiva(Boolean isaplicaiva) {
		this.isaplicaiva = isaplicaiva;
	}

	public Boolean getIspagado() {
		return ispagado;
	}

	public void setIspagado(Boolean ispagado) {
		this.ispagado = ispagado;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(Double subtotal) {
		this.subtotal = subtotal;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public ElementoCatalogo getConceptoPago() {
		return conceptoPago;
	}

	public void setConceptoPago(ElementoCatalogo conceptoPago) {
		this.conceptoPago = conceptoPago;
	}

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	

}