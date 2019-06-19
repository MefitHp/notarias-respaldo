package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;

import java.sql.Timestamp;
import java.util.Date;


/**
 * The persistent class for the tbbsnm37 database table.
 * 
 */
@Entity
@Table(name="tbbsnm37")
public class ActoDocumento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idactodoc;

    @Temporal( TemporalType.DATE)
	private Date fechaaprobacion;

    @Temporal( TemporalType.DATE)
	private Date fechaentrega;

    @Temporal( TemporalType.DATE)
	private Date fechasolicitud;
    
    @Temporal( TemporalType.DATE)
   	private Date fechaArchivo;
    
    private String dsruta;
    
    private String dsrutaformato;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean isentregado;

	@Column(columnDefinition="INT")
	private Boolean issolicitado;
	
	@Column(columnDefinition="INT")
	private Boolean isaprovado;
	
	@Column(columnDefinition="INT")
	private Boolean tienecomments;
	
	
	
	public Boolean getTienecomments() {
		return tienecomments;
	}

	public void setTienecomments(Boolean tienecomments) {
		this.tienecomments = tienecomments;
	}

	@Lob()
	private String txtFormato;
	
	private Timestamp tmstmp;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto")
	private Acto acto;

	//bi-directional many-to-one association to Documento
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="iddocumento")
	private Documento documento;

	@ManyToOne
	@JoinColumn(name="idformatopdf")
	private FormatoPDF formatoPdf;
	
	//bi-directional many-to-one association to Usuario
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idnotario")
	private Usuario notario;
	
	@ManyToOne
	@JoinColumn(name="idgestor")
	private Gestor gestor;
	
	@ManyToOne
	@JoinColumn(name="idvaluador")
	private Valuador valuador;

    public ActoDocumento() {
    }
    
    public FormatoPDF getFormatoPdf() {
		return formatoPdf;
	}
    public void setFormatoPdf(FormatoPDF formatoPdf) {
		this.formatoPdf = formatoPdf;
	}

	public String getIdactodoc() {
		return this.idactodoc;
	}

	public void setIdactodoc(String idactodoc) {
		this.idactodoc = idactodoc;
	}

	public Date getFechaaprobacion() {
		return this.fechaaprobacion;
	}

	public void setFechaaprobacion(Date fechaaprobacion) {
		this.fechaaprobacion = fechaaprobacion;
	}

	public Date getFechaentrega() {
		return this.fechaentrega;
	}

	public void setFechaentrega(Date fechaentrega) {
		this.fechaentrega = fechaentrega;
	}

	public Date getFechasolicitud() {
		return this.fechasolicitud;
	}

	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIsentregado() {
		return isentregado;
	}
	
	public void setIsentregado(Boolean isentregado) {
		this.isentregado = isentregado;
	}
	
	public Boolean getIssolicitado() {
		return issolicitado;
	}
	
	public void setIssolicitado(Boolean issolicitado) {
		this.issolicitado = issolicitado;
	}
	
	public Boolean getIsaprovado() {
		return isaprovado;
	}
	
	public void setIsaprovado(Boolean isaprovado) {
		this.isaprovado = isaprovado;
	}
	
	public String getTxtFormato() {
		return txtFormato;
	}
	
	public void setTxtFormato(String txtFormato) {
		this.txtFormato = txtFormato;
	}
	
	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Acto getActo() {
		return acto;
	}

	public void setActo(Acto acto) {
		this.acto = acto;
	}

	public Documento getDocumento() {
		return documento;
	}

	public void setDocumento(Documento documento) {
		this.documento = documento;
	}

	public Usuario getNotario() {
		return notario;
	}

	public void setNotario(Usuario notario) {
		this.notario = notario;
	}
	
	public Gestor getGestor(){
		return this.gestor;		
	}
	
	public void setGestor(Gestor gestor){
		this.gestor = gestor;
	}

	public Valuador getValuador() {
		return valuador;
	}

	public void setValuador(Valuador valuador) {
		this.valuador = valuador;
	}

	public String getDsruta() {
		return dsruta;
	}

	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}
	
	public String getDsrutaformato() {
		return dsrutaformato;
	}
	
	public void setDsrutaformato(String dsrutaformato) {
		this.dsrutaformato = dsrutaformato;
	}

	public Date getFechaArchivo() {
		return fechaArchivo;
	}

	public void setFechaArchivo(Date fechaArchivo) {
		this.fechaArchivo = fechaArchivo;
	}
	

}