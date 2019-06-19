package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm08a")
@NamedQueries({
	@NamedQuery(name="PlantillaDocumentoNotarialSubOperacion.findByPlantillaSubOperacion", query="SELECT ps FROM PlantillaDocumentoNotarialSubOperacion ps "
			+ "WHERE ps.suboperacion.idsuboperacion = :idsuboperacion AND ps.plantillaDocumentoNotarial.id.iddocnot = :iddocumentonotarial AND ps.plantillaDocumentoNotarial.id.inversion = :inversion"),
	@NamedQuery(name="PlantillaDocumentoNotarialSubOperacion.findByPlantilla", query="SELECT ps FROM PlantillaDocumentoNotarialSubOperacion ps "
			+ "WHERE ps.plantillaDocumentoNotarial.id.iddocnot = :iddocumentonotarial AND ps.plantillaDocumentoNotarial.id.inversion = :inversion")
})
public class PlantillaDocumentoNotarialSubOperacion implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="idacdono")
	private String plantillaDocumentoNotarialSubOperacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idsuboperacion")
    private Suboperacion suboperacion;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="iddocnot", referencedColumnName="iddocnot"),
		@JoinColumn(name="inversion", referencedColumnName="inversion")
		})
	private PlantillaDocumentoNotarial plantillaDocumentoNotarial;
	
	@Column(name="idsesion")
	private String sesion;
	
	@Column(name="tmstmp")
	private Timestamp fechaHora;

	public String getPlantillaDocumentoNotarialSubOperacion() {
		return plantillaDocumentoNotarialSubOperacion;
	}

	public void setPlantillaDocumentoNotarialSubOperacion(
			String plantillaDocumentoNotarialSubOperacion) {
		this.plantillaDocumentoNotarialSubOperacion = plantillaDocumentoNotarialSubOperacion;
		setFechaHora(new Timestamp(Calendar.getInstance().getTimeInMillis()));
	}

	public Suboperacion getSuboperacion() {
		return suboperacion;
	}

	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}

	public PlantillaDocumentoNotarial getPlantillaDocumentoNotarial() {
		return plantillaDocumentoNotarial;
	}

	public void setPlantillaDocumentoNotarial(
			PlantillaDocumentoNotarial plantillaDocumentoNotarial) {
		this.plantillaDocumentoNotarial = plantillaDocumentoNotarial;
	}

	public Timestamp getFechaHora() {
		return fechaHora;
	}
	
	protected void setFechaHora(Timestamp fechaHora){
		this .fechaHora = new Timestamp(Calendar.getInstance().getTimeInMillis());
	}

	public String getSesion() {
		return sesion;
	}

	public void setSesion(String sesion) {
		this.sesion = sesion;
	}
	
}
