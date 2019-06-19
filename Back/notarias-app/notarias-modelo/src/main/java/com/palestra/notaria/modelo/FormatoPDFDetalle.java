package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm21")
public class FormatoPDFDetalle implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int identificador;
	
//	@ManyToOne(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST,
//			CascadeType.MERGE, CascadeType.REMOVE })
//	@JoinColumn(name = "idftopdf")
//	private FormatoPDF idftopdf;
	
	@ManyToOne
	@JoinColumn(name="idftopdf",referencedColumnName="identificador")
	private FormatoPDF idftopdf;
	
	private String dscampo;
	private String dsvariable;
	
	@ManyToOne
	@JoinColumn(name="idsuboperacion")
	private Suboperacion suboperacion;
	
	@Column(name="aplicatodassuboperaciones",columnDefinition="INT")
	private Boolean aplicaTodasSuboperaciones;
	
	public Boolean getAplicaTodasSuboperaciones() {
		return aplicaTodasSuboperaciones;
	}
	public void setAplicaTodasSuboperaciones(Boolean aplicaTodasSuboperaciones) {
		this.aplicaTodasSuboperaciones = aplicaTodasSuboperaciones;
	}
	
	public FormatoPDFDetalle() {}
	
	public Suboperacion getSuboperacion() {
		return suboperacion;
	}
	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}
	public FormatoPDF getIdftopdf() {
		return idftopdf;
	}
	public void setIdftopdf(FormatoPDF idftopdf) {
		this.idftopdf = idftopdf;
	}
	public int getIdentificador() {
		return identificador;
	}

	public void setIdentificador(int identificador) {
		this.identificador = identificador;
	}

//	public FormatoPDF getIdftopdf() {
//		return idftopdf;
//	}
//
//	public void setIdftopdf(FormatoPDF idftopdf) {
//		this.idftopdf = idftopdf;
//	}

	public String getDscampo() {
		return dscampo;
	}

	public void setDscampo(String dscampo) {
		this.dscampo = dscampo;
	}

	public String getDsvariable() {
		return dsvariable;
	}

	public void setDsvariable(String dsvariable) {
		this.dsvariable = dsvariable;
	}
	
	

}
