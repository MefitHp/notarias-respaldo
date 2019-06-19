package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm40 database table.
 * 
 */
@Entity
@Table(name="tbbsnm40")
public class Tramite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idtramite;

	private String idsesion;

	private Timestamp tmstmp;

    @Lob()
	private String txtproposito;
    
    private String dsdirectorio;

	//bi-directional many-to-one association to Persona
	@ManyToOne
	@JoinColumn(name="idcliente")
	private Persona cliente;

	//bi-directional many-to-one association to EtapaTramite
	@ManyToOne
	@JoinColumn(name="idstatus")
	private EtapaTramite status;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idabogado")
	private Usuario abogado;

	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne
	@JoinColumn(name="idlocacion")
	private ElementoCatalogo locacion;

    public Tramite() {
    }
    
    public Tramite(String idtramite){
    	this.idtramite = idtramite;
    }

	public String getIdtramite() {
		return this.idtramite;
	}

	public void setIdtramite(String idtramite) {
		this.idtramite = idtramite;
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

	public String getTxtproposito() {
		return this.txtproposito;
	}

	public void setTxtproposito(String txtproposito) {
		this.txtproposito = txtproposito;
	}

	public Persona getCliente() {
		return cliente;
	}

	public void setCliente(Persona cliente) {
		this.cliente = cliente;
	}

	public EtapaTramite getStatus() {
		return status;
	}

	public void setStatus(EtapaTramite status) {
		this.status = status;
	}

	public Usuario getAbogado() {
		return abogado;
	}

	public void setAbogado(Usuario abogado) {
		this.abogado = abogado;
	}

	public ElementoCatalogo getLocacion() {
		return locacion;
	}

	public void setLocacion(ElementoCatalogo locacion) {
		this.locacion = locacion;
	}

	public String getDsdirectorio() {
		return dsdirectorio;
	}

	public void setDsdirectorio(String dsdirectorio) {
		this.dsdirectorio = dsdirectorio;
	}
	
}