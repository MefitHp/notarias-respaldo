package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.palestra.notaria.enums.EnumStatusPago;

@Entity
@Table(name="tbbsnm75")
public class Pago implements Serializable {

	private static final long serialVersionUID = -8118370976778028043L;
	
	@Id
	private String idpago;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idactodocumento")
	private ActoDocumento actodocumento;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idexpediente")
	private Expediente expediente;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idescritura")
	private Escritura escritura;
	
	@Enumerated(EnumType.STRING)
	private EnumStatusPago statuspago;
	
	@Column(name="docacusepago")
	private String acusepago;
	@Column(name="dochojaamarilla")
	private String hojaamarilla;
	@Column(name="docdatosfactura")
	private String datosfactura;
	
	private Double montoporpagar;
	
	private Double montopagado;
	
	public Double getMontopagado() {
		return montopagado;
	}
	public void setMontopagado(Double montopagado) {
		this.montopagado = montopagado;
	}
	@Column(columnDefinition="INT")
	private Boolean isliquidado;
	
	private Timestamp tmstmp;
	private String idsesion;
	public String getIdpago() {
		return idpago;
	}
	public void setIdpago(String idpago) {
		this.idpago = idpago;
	}
	public ActoDocumento getActodocumento() {
		return actodocumento;
	}
	public void setActodocumento(ActoDocumento actodocumento) {
		this.actodocumento = actodocumento;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	public EnumStatusPago getStatuspago() {
		return statuspago;
	}
	public void setStatuspago(EnumStatusPago statuspago) {
		this.statuspago = statuspago;
	}
	public String getAcusepago() {
		return acusepago;
	}
	public void setAcusepago(String acusepago) {
		this.acusepago = acusepago;
	}
	public String getHojaamarilla() {
		return hojaamarilla;
	}
	public void setHojaamarilla(String hojaamarilla) {
		this.hojaamarilla = hojaamarilla;
	}
	public String getDatosfactura() {
		return datosfactura;
	}
	public void setDatosfactura(String datosfactura) {
		this.datosfactura = datosfactura;
	}
	
	
	public Boolean isIsliquidado() {
		return isliquidado;
	}
	public void setIsliquidado(Boolean isliquidado) {
		this.isliquidado = isliquidado;
	}
	
	
	public Timestamp getTmstmp() {
		return tmstmp;
	}
	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	public String getIdsesion() {
		return idsesion;
	}
	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}
	public Double getMontoporpagar() {
		return montoporpagar;
	}
	public void setMontoporpagar(Double montoporpagar) {
		this.montoporpagar = montoporpagar;
	}
	
	
	
	
	
	
	
	

}
