package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm33c")
@NamedQueries({
	@NamedQuery(name="DocumentoNotarialParte.findById", query="SELECT dnp FROM DocumentoNotarialParte dnp WHERE dnp.identificador = :identificador"),
	@NamedQuery(name="DocumentoNotarialParte.findByIdDocumento", query="SELECT dnp FROM DocumentoNotarialParte dnp WHERE dnp.documento = :documento ORDER BY dnp.orden"),
	@NamedQuery(name="DocumentoNotarialParte.findByReferencia", query="SELECT dnp FROM DocumentoNotarialParte dnp WHERE dnp.documento =:documento AND dnp.referencia = :referencia"),
	@NamedQuery(name="DocumentoNotarialParte.findByOrden", query="SELECT dnp FROM DocumentoNotarialParte dnp WHERE dnp.documento =:documento AND dnp.orden = :orden")
})
public class DocumentoNotarialParte implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="iddocpar")
	private String identificador;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="iddocnotpar", referencedColumnName="iddocnotpar")
	private DocumentoNotarialParcial documento;
	
	@Column(name="inorden")
	private int orden;
	
	@Column(name="dsrefere", columnDefinition="text")
	private String referencia;
	
	@Column(name="dsvalref", columnDefinition="text")
	private String valor;
	
	@Column(name="tmstmp")
	private Timestamp fechaHora;
	
	public DocumentoNotarialParte(){
		this.setFechaHora(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public DocumentoNotarialParcial getDocumento() {
		return documento;
	}

	public void setDocumento(DocumentoNotarialParcial documento) {
		this.documento = documento;
	}

	public int getOrden() {
		return orden;
	}

	public void setOrden(int orden) {
		this.orden = orden;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}

	private void setFechaHora(Timestamp fechaHora) {
		this.fechaHora = fechaHora;
	}
	
}
