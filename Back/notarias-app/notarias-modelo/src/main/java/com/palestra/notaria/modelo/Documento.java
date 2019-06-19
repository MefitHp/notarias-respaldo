package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * The persistent class for the tbbsnm22 database table.
 * 
 */
@Entity
@Table(name="tbbsnm22")
public class Documento implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String iddocumento;

	private String dsdescripcion;

	private String dstitulo;

	private Integer version;

    @Temporal(TemporalType.DATE)
	private Date fecha;

	private String idsesion;

	@Column(columnDefinition="INT")
	private Boolean ispublicado;

	@Column(columnDefinition="INT")
	private Boolean isrequerido;

	private Integer numdiasgestion;

	private Timestamp tmstmp;
	
	@Transient
	private String creador;

    @Lob()
	private String txplantilla;

	//bi-directional many-to-one association to ElementoCatalogo
	@ManyToOne
	@JoinColumn(name="idtipodoc")
	private ElementoCatalogo tipodoc;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	private String isgestionado;

    public Documento() {
    }

    public String getIsgestionado() {
		return isgestionado;
	}
    public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
  
    
    public Documento(String iddoc){
    	this.iddocumento = iddoc;
    }

	public String getIddocumento() {
		return this.iddocumento;
	}

	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDstitulo() {
		return this.dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}

	
	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return version;
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getIdsesion() {
		return this.idsesion;
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
		return this.numdiasgestion;
	}

	public void setNumdiasgestion(Integer numdiasgestion) {
		this.numdiasgestion = numdiasgestion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getTxplantilla() {
		return this.txplantilla;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dsdescripcion == null) ? 0 : dsdescripcion.hashCode());
		result = prime * result
				+ ((dstitulo == null) ? 0 : dstitulo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Documento other = (Documento) obj;
		if (dsdescripcion == null) {
			if (other.dsdescripcion != null)
				return false;
		} else if (!dsdescripcion.equals(other.dsdescripcion))
			return false;
		if (dstitulo == null) {
			if (other.dstitulo != null)
				return false;
		} else if (!dstitulo.equals(other.dstitulo))
			return false;
		return true;
	}
	

}