package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="tbbsnm22b")
public class DocumentoVersiones implements Serializable {

	private static final long serialVersionUID = 2304493546443509149L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer identificador;
	
	private String iddocumento;

	private String dsdescripcion;

	private String dstitulo;
	
	//private String dsruta;

	private Integer version;
	
	private Integer versionbase;

    @Temporal(TemporalType.DATE)
	private Date fecha;

	private String idsesion;
	
	@Transient
	private String creador;

	@Column(columnDefinition="INT")
	private Boolean ispublicado;

	@Column(columnDefinition="INT")
	private Boolean isrequerido;

	private Integer numdiasgestion;
	
	private String isgestionado;

	private Timestamp tmstmp;

    @Lob()
	private String txplantilla;

	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne
	@JoinColumn(name="idtipodoc")
	private ElementoCatalogo tipodoc;

	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	public String getIsgestionado() {
		return isgestionado;
	}
	public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
	
	public Integer getIdentificador() {
		return identificador;
	}

	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}

	public String getIddocumento() {
		return iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getDsdescripcion() {
		return dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDstitulo() {
		return dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}

	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersionbase() {
		return versionbase;
	}

	public void setVersionbase(Integer versionbase) {
		this.versionbase = versionbase;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIspublicado() {
		return ispublicado;
	}

	public void setIspublicado(Boolean ispublicado) {
		this.ispublicado = ispublicado;
	}

	public Boolean getIsrequerido() {
		return isrequerido;
	}

	public void setIsrequerido(Boolean isrequerido) {
		this.isrequerido = isrequerido;
	}

	public Integer getNumdiasgestion() {
		return numdiasgestion;
	}

	public void setNumdiasgestion(Integer numdiasgestion) {
		this.numdiasgestion = numdiasgestion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getTxplantilla() {
		return txplantilla;
	}

	public void setTxplantilla(String txplantilla) {
		this.txplantilla = txplantilla;
	}

	public ElementoCatalogo getTipodoc() {
		return tipodoc;
	}

	public void setTipodoc(ElementoCatalogo tipodoc) {
		this.tipodoc = tipodoc;
	}
	
	public Boolean getIsactivo() {
		return isactivo;
	}
	
	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}
	
	public String getCreador() {
		return creador;
	}
	
	public void setCreador(String creador) {
		this.creador = creador;
	}
	/*
	public String getDsruta() {
		return dsruta;
	}
	
	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}
	*/
}
