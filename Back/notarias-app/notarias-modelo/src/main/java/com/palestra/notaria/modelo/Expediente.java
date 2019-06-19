package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm32 database table.
 * 
 */
@Entity
@Table(name="tbbsnm32")
public class Expediente implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idexpediente;

	private Double credito;

	private String dsdescripcion;

	private String dsreferencia;

	private String dsstatus;
    
	@Column(columnDefinition="INT")
	private Boolean tienecomments;
    
    public Boolean getTienecomments() {
		return tienecomments;
	}

	public void setTienecomments(Boolean tienecomments) {
		this.tienecomments = tienecomments;
	}

	@Temporal( TemporalType.DATE)
	private Date fechafinal;

    @Temporal( TemporalType.DATE)
	private Date fechainicial;


    
	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean iscotejo;

	@Column(unique=true)
	private String numexpediente;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Usuario
	/*@OneToMany(mappedBy="expediente")
	private List<Comentario> listaComentarios;*/

	//bi-directional many-to-one association to Tramite
	@ManyToOne
	@JoinColumn(name="idtramite")
	private Tramite tramite;

	//bi-directional many-to-one association to Usuario
	@ManyToOne
	@JoinColumn(name="idabogado")
	private Usuario abogado;

	@Enumerated(value=EnumType.STRING)
	@Column(columnDefinition="ENUM",name="enumestatus")
	private EnumEstatus estatus;
	
	private String dsmotivocierre;
	
	@Transient
	List<Compareciente> comparecientesList;
	
	@Transient
	String fechainicialstr;
	
	@Transient
	String fechafinalstr;
	
	@Transient
	List<String> numerosescrituras=null;

    public Expediente() {
    }
    
    public String getFechafinalstr() {
		return fechafinalstr;
	}
    public void setFechafinalstr(String fechafinalstr) {
		this.fechafinalstr = fechafinalstr;
	}
    public String getFechainicialstr() {
		return fechainicialstr;
	}
    public void setFechainicialstr(String fechainicialstr) {
		this.fechainicialstr = fechainicialstr;
	}
    public List<Compareciente> getComparecientesList() {
		return comparecientesList;
	}
    public void setComparecientesList(List<Compareciente> comparecientesList) {
		this.comparecientesList = comparecientesList;
	}
	public String getIdexpediente() {
		return this.idexpediente;
	}

	public void setIdexpediente(String idexpediente) {
		this.idexpediente = idexpediente;
	}

	public Double getCredito() {
		return this.credito;
	}

	public void setCredito(Double credito) {
		this.credito = credito;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsreferencia() {
		return this.dsreferencia;
	}

	public void setDsreferencia(String dsreferencia) {
		this.dsreferencia = dsreferencia;
	}

	public String getDsstatus() {
		return this.dsstatus;
	}

	public void setDsstatus(String dsstatus) {
		this.dsstatus = dsstatus;
	}

	public Date getFechafinal() {
		return this.fechafinal;
	}

	public void setFechafinal(Date fechafinal) {
		this.fechafinal = fechafinal;
	}

	public Date getFechainicial() {
		return this.fechainicial;
	}

	public void setFechainicial(Date fechainicial) {
		this.fechainicial = fechainicial;
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

	public String getNumexpediente() {
		return numexpediente;
	}

	public void setNumexpediente(String numexpediente) {
		this.numexpediente = numexpediente;
	}

	public Tramite getTramite() {
		return tramite;
	}

	public void setTramite(Tramite tramite) {
		this.tramite = tramite;
	}

	public Usuario getAbogado() {
		return abogado;
	}

	public void setAbogado(Usuario abogado) {
		this.abogado = abogado;
	}




	public Boolean getIscotejo() {
		return iscotejo;
	}

	public void setIscotejo(Boolean iscotejo) {
		this.iscotejo = iscotejo;
	}

	/*public List<Comentario> getListaComentarios() {
		return listaComentarios;
	}

	public void setListaComentarios(List<Comentario> listaComentarios) {
		this.listaComentarios = listaComentarios;
	}*/

	public EnumEstatus getEstatus() {
		return estatus;
	}
	
	public void setEstatus(EnumEstatus estatus) {
		this.estatus = estatus;
	}
	
	public String getDsmotivocierre() {
		return dsmotivocierre;
	}
	
	public void setDsmotivocierre(String dsmotivocierre) {
		this.dsmotivocierre = dsmotivocierre;
	}

	public List<String> getNumerosescrituras() {
		return numerosescrituras;
	}

	public void setNumerosescrituras(List<String> numerosescrituras) {
		this.numerosescrituras = numerosescrituras;
	}
}