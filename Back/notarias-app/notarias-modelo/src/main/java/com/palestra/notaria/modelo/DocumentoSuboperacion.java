package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm64")
public class DocumentoSuboperacion implements Serializable {

	private static final long serialVersionUID = -7250455041875610075L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer identificador;
	
	@ManyToOne
	@JoinColumn(name="iddocumento")
	private Documento documento;
	
	@ManyToOne
	@JoinColumn(name="idsuboperacion")
	private Suboperacion suboperacion; 
	
	@ManyToOne
	@JoinColumn(name="idlocalidad",referencedColumnName="idelemento")
	private ElementoCatalogo localidad;
	
	@ManyToOne
	@JoinColumn(name="idformatopdf",referencedColumnName="identificador")
	private FormatoPDF formatopdf;
	
	private Integer inorden;
	
	private String isgestionado;
	
	public String getIsgestionado() {
		return isgestionado;
	}
	public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
	
	public Integer getInorden() {
		return inorden;
	}
	public void setInorden(Integer inorden) {
		this.inorden = inorden;
	}
	
	public FormatoPDF getFormatopdf() {
		return formatopdf;
	}
	public void setFormatopdf(FormatoPDF formatopdf) {
		this.formatopdf = formatopdf;
	}
	public Integer getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Integer identificador) {
		this.identificador = identificador;
	}
	
	public ElementoCatalogo getLocalidad() {
		return localidad;
	}
	
	public void setLocalidad(ElementoCatalogo localidad) {
		this.localidad = localidad;
	}
	
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
	}
	public Suboperacion getSuboperacion() {
		return suboperacion;
	}
	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((documento == null) ? 0 : documento.hashCode());
		result = prime * result
				+ ((suboperacion == null) ? 0 : suboperacion.hashCode());
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
		DocumentoSuboperacion other = (DocumentoSuboperacion) obj;
		if (documento == null) {
			if (other.documento != null)
				return false;
		} else if (!documento.equals(other.documento))
			return false;
		if (suboperacion == null) {
			if (other.suboperacion != null)
				return false;
		} else if (!suboperacion.equals(other.suboperacion))
			return false;
		return true;
	}
	
	
}
