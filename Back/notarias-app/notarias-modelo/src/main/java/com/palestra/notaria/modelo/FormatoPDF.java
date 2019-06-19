package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm20")

//@NamedQueries({
//	@NamedQuery(name="FormatoPDF.findAll", query="SELECT f FROM FormatoPDF f"),
//	@NamedQuery(name="FormatoPDF.findById", query="SELECT f FROM FormatoPDF f WHERE f.identificador = :identificador"),
//	@NamedQuery(name="FormatoPDF.findDetalle", query="SELECT fd FROM FormatoPDFDetalle fd WHERE fd.idftopdf = :identificador")
//})

public class FormatoPDF implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String identificador;
	private String dstitulo;
	private String dsdescripcion;
	private String dsruta;
	@Column(columnDefinition="INT")
	private Boolean isonline;
	private String idsesion;
	
	@OneToMany(mappedBy="idftopdf",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<FormatoPDFDetalle> detalleList = new ArrayList<FormatoPDFDetalle>();
	
	@ManyToOne
	@JoinColumn(name="idtipodoc")
	private ElementoCatalogo tipodoc;
	
	private String isgestionado;
	
	@Column(columnDefinition="INT")
	private Boolean ispagorequire;
	
	private String tipoalerta;
	
	private Timestamp tmstmp;
	
	public FormatoPDF() {}

	public String getIsgestionado() {
		return isgestionado;
	}
	public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
	public FormatoPDF(String idpdf){
		this.identificador = idpdf;
	}
	
	public ElementoCatalogo getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(ElementoCatalogo tipodoc) {
		this.tipodoc = tipodoc;
	}
	
	public List<FormatoPDFDetalle> getDetalleList() {
		return detalleList;
	}
	public void setDetalleList(List<FormatoPDFDetalle> detalleList) {
		this.detalleList = detalleList;
	}

	public String getIdentificador() {
		return identificador;
	}

	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}

	public String getDstitulo() {
		return dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}
	public String getDsdescripcion() {
		return dsdescripcion;
	}
	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsruta() {
		return dsruta;
	}

	public void setDsruta(String dsruta) {
		this.dsruta = dsruta;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Boolean getIspagorequire() {
		return ispagorequire;
	}

	public void setIspagorequire(Boolean ispagorequire) {
		this.ispagorequire = ispagorequire;
	}

	public String getTipoalerta() {
		return tipoalerta;
	}

	public void setTipoalerta(String tipoalerta) {
		this.tipoalerta = tipoalerta;
	}

	public Boolean getIsonline() {
		return isonline;
	}

	public void setIsonline(Boolean isonline) {
		this.isonline = isonline;
	}
	
	

}
