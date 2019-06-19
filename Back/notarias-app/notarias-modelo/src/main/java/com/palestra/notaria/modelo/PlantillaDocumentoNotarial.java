package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbbsnm08")
@NamedQueries({
	@NamedQuery(name="PlantillaDocumentoNotarial.findByLocacion", query="SELECT p FROM PlantillaDocumentoNotarial p "
			+ "WHERE p.locacion.idelemento = :idlocacion AND p.ispublicado = :publicado") 
})
public class PlantillaDocumentoNotarial implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private PlantillaDocumentoNotarialPK id;

	private String dssesion;

	private String dstitulo;

	private String idsesion;

	@Column(columnDefinition = "INT")
	private Boolean ispublicado;

	private Timestamp tmstmp;

	@Lob()
	private String txplantilla;

	@ManyToOne
	@JoinColumn(name = "idlocacion")
	private ElementoCatalogo locacion;

	@OneToMany(mappedBy="plantillaDocumentoNotarial", cascade = CascadeType.ALL)
	private List<PlantillaDocumentoNotarialSubOperacion> listaPlantillaDocumentoNotarialSubOperacion;
	
//	@ManyToOne
//	@JoinColumn(name = "idsuboperacion")
//	private Suboperacion suboperacion;

	public PlantillaDocumentoNotarial() {
	}

	public PlantillaDocumentoNotarialPK getId() {
		return id;
	}

	public void setId(PlantillaDocumentoNotarialPK id) {
		this.id = id;
	}

	public String getDssesion() {
		return this.dssesion;
	}

	public void setDssesion(String dssesion) {
		this.dssesion = dssesion;
	}

	public String getDstitulo() {
		return this.dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
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

//	public Suboperacion getSuboperacion() {
//		return suboperacion;
//	}

//	public void setSuboperacion(Suboperacion suboperacion) {
//		this.suboperacion = suboperacion;
//	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ElementoCatalogo getLocacion() {
		return locacion;
	}

	public void setLocacion(ElementoCatalogo locacion) {
		this.locacion = locacion;
	}

	public List<PlantillaDocumentoNotarialSubOperacion> getListaPlantillaDocumentoNotarialSubOperacion() {
		return listaPlantillaDocumentoNotarialSubOperacion;
	}

	public void setListaPlantillaDocumentoNotarialSubOperacion(
			List<PlantillaDocumentoNotarialSubOperacion> listaPlantillaDocumentoNotarialSubOperacion) {
		this.listaPlantillaDocumentoNotarialSubOperacion = listaPlantillaDocumentoNotarialSubOperacion;
	}

}