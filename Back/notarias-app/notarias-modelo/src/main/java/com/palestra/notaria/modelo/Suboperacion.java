package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.palestra.notaria.dato.FormatoWrapper;


/**
 * The persistent class for the tbbsnm17 database table.
 * 
 */
@Entity
@Table(name="tbbsnm17")
public class Suboperacion implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idsuboperacion;

	private String dsdescripcion;

	private String dsnombre;

	private String idsesion;

	private Timestamp tmstmp;
	
	@Transient
	private List<FormatoWrapper> previosList;
	
	@Transient
	private List<FormatoWrapper> posterioresList;

	//bi-directional many-to-one association to PlantillaDocumentoNotarial
//	@ManyToOne
//	@JoinColumn(name="iddocnot")
//	private PlantillaDocumentoNotarial docNot;

	//bi-directional many-to-one association to Operacion
	@ManyToOne
	@JoinColumn(name="idoperacion")
	private Operacion operacion;

    public Suboperacion() {
    }
    
    public List<FormatoWrapper> getPreviosList() {
		return previosList;
	}
    public void setPreviosList(List<FormatoWrapper> previosList) {
		this.previosList = previosList;
	}
    public List<FormatoWrapper> getPosterioresList() {
		return posterioresList;
	}
    public void setPosterioresList(List<FormatoWrapper> posterioresList) {
		this.posterioresList = posterioresList;
	}

	public String getIdsuboperacion() {
		return this.idsuboperacion;
	}

	public void setIdsuboperacion(String idsuboperacion) {
		this.idsuboperacion = idsuboperacion;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDsnombre() {
		return this.dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
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

//	public PlantillaDocumentoNotarial getDocNot() {
//		return docNot;
//	}
//	
//	public void setDocNot(PlantillaDocumentoNotarial docNot) {
//		this.docNot = docNot;
//	}

	public Operacion getOperacion() {
		return operacion;
	}

	public void setOperacion(Operacion operacion) {
		this.operacion = operacion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dsnombre == null) ? 0 : dsnombre.hashCode());
		result = prime * result
				+ ((idsesion == null) ? 0 : idsesion.hashCode());
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
		Suboperacion other = (Suboperacion) obj;
		if (dsnombre == null) {
			if (other.dsnombre != null)
				return false;
		} else if (!dsnombre.equals(other.dsnombre))
			return false;
		if (idsesion == null) {
			if (other.idsesion != null)
				return false;
		} else if (!idsesion.equals(other.idsesion))
			return false;
		return true;
	}
	
}