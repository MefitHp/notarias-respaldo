package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm77")
@NamedQueries(value={
		@NamedQuery(name="BitacoraUsuario.listarXgrupo", query="SELECT bu FROM BitacoraUsuario bu WHERE bu.idgrupotrabajo = :idgrupotrabajo AND active = 1 ORDER BY bu.tmstmp DESC"),
		@NamedQuery(name="BitacoraUsuario.buscarXtarea", query="SELECT bu FROM BitacoraUsuario bu WHERE bu.idtarea = :idtarea AND active = 1 ORDER BY bu.tmstmp DESC")
})
public class BitacoraUsuario implements Serializable{

	private static final long serialVersionUID = 7520432154058615879L;

	@Id
	private String idbitusu;
	private String idacto;
	private String idexpediente;
	private String idtarea;
	private String numexp;
	private String idgrupotrabajo;
	private String idobjeto;
	private String tipo;
	private String texto;
	private String status;
	private Timestamp tmstmp;
	@Column(columnDefinition="INT")
	private Boolean active;
	
	
	public String getIdbitusu() {
		return idbitusu;
	}
	public void setIdbitusu(String idbitusu) {
		this.idbitusu = idbitusu;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getIdgrupotrabajo() {
		return idgrupotrabajo;
	}
	public void setIdgrupotrabajo(String idgrupotrabajo) {
		this.idgrupotrabajo = idgrupotrabajo;
	}
	public String getIdobjeto() {
		return idobjeto;
	}
	public void setIdobjeto(String idobjeto) {
		this.idobjeto = idobjeto;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getTexto() {
		return texto;
	}
	public void setTexto(String texto) {
		this.texto = texto;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Timestamp getTmstmp() {
		return tmstmp;
	}
	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public String getNumexp() {
		return numexp;
	}
	public void setNumexp(String numexp) {
		this.numexp = numexp;
	}
	public String getIdexpediente() {
		return idexpediente;
	}
	public void setIdexpediente(String idexpediente) {
		this.idexpediente = idexpediente;
	}
	public String getIdtarea() {
		return idtarea;
	}
	public void setIdtarea(String idtarea) {
		this.idtarea = idtarea;
	}
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}	
	
}
