package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm10 database table.
 * 
 */
@Entity
@Table(name="tbbsnm10")
public class TarjetaAmarilla implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtrjamarilla;

	private Double adquisicionlocal;

	private Double erogacion;

	private Double honorario;

	private Double ide;

	private Double ideerog;

	private Double isr;

	private Double iva;

	private String dsnomcliente;

	private Double porcentajeIVA;

	private String dsrfc;

	private Double rpp;

	private Double subhiva;

	private Double trasdominio;

	private Double total;

	private String idsesion;

	private String numesc;

	private Timestamp tmstmp;
	
	private Date fecha;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	//bi-directional many-to-one association to Persona
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idpersona")
	private Persona persona;

	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idusuarioelab")
	private Usuario usuarioelab;

    public TarjetaAmarilla() {
    }

	public String getIdtrjamarilla() {
		return this.idtrjamarilla;
	}

	public void setIdtrjamarilla(String idtrjamarilla) {
		this.idtrjamarilla = idtrjamarilla;
	}
	
	public String getDsnomcliente() {
		return this.dsnomcliente;
	}

	public void setDsnomcliente(String dsnomcliente) {
		this.dsnomcliente = dsnomcliente;
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
	
	public Double getAdquisicionlocal() {
		return adquisicionlocal;
	}
	
	public void setAdquisicionlocal(Double adquisicionlocal) {
		this.adquisicionlocal = adquisicionlocal;
	}

	public Double getErogacion() {
		return erogacion;
	}

	public void setErogacion(Double erogacion) {
		this.erogacion = erogacion;
	}

	public Double getHonorario() {
		return honorario;
	}

	public void setHonorario(Double honorario) {
		this.honorario = honorario;
	}

	public Double getIde() {
		return ide;
	}

	public void setIde(Double ide) {
		this.ide = ide;
	}

	public Double getIdeerog() {
		return ideerog;
	}

	public void setIdeerog(Double ideerog) {
		this.ideerog = ideerog;
	}

	public Double getIsr() {
		return isr;
	}

	public void setIsr(Double isr) {
		this.isr = isr;
	}

	public Double getIva() {
		return iva;
	}

	public void setIva(Double iva) {
		this.iva = iva;
	}

	public Double getPorcentajeIVA() {
		return porcentajeIVA;
	}

	public void setPorcentajeIVA(Double porcentajeIVA) {
		this.porcentajeIVA = porcentajeIVA;
	}

	public Double getRpp() {
		return rpp;
	}

	public void setRpp(Double rpp) {
		this.rpp = rpp;
	}

	public Double getSubhiva() {
		return subhiva;
	}

	public void setSubhiva(Double subhiva) {
		this.subhiva = subhiva;
	}

	public Double getTrasdominio() {
		return trasdominio;
	}

	public void setTrasdominio(Double trasdominio) {
		this.trasdominio = trasdominio;
	}

	public Double getTotal() {
		return total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	public String getNumesc() {
		return numesc;
	}
	
	public void setNumesc(String numesc) {
		this.numesc = numesc;
	}
	
	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public Date getFecha() {
		return fecha;
	}
	
	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Acto getActo() {
		return acto;
	}
	
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public Usuario getUsuarioelab() {
		return usuarioelab;
	}
	
	public void setUsuarioelab(Usuario usuarioelab) {
		this.usuarioelab = usuarioelab;
	}
}