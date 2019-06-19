package com.palestra.notaria.dato;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Suboperacion;

public class DatoActo implements java.io.Serializable{

	private static final long serialVersionUID = 1245698465644683684L;

	private String idacto;

	private String dsnombre;
	
	private String idSuboperacion;
	
	private Integer numacto;
	
	private Suboperacion suboperacion = null;
	
	private ArrayList<DatoActoDocumento> previos;
	
	private ArrayList<DatoActoDocumento> posteriores;
	
	private String status = "X";
	
	private Boolean hasProceso;
	
	public Boolean getHasProceso() {
		return hasProceso;
	}
	
	public void setHasProceso(Boolean hasProceso) {
		this.hasProceso = hasProceso;
	}
	
	public String getIdacto() {
		return idacto;
	}

	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public ArrayList<DatoActoDocumento> getPrevios() {
		return previos;
	}

	public void setPrevios(ArrayList<DatoActoDocumento> previos) {
		this.previos = previos;
	}

	public ArrayList<DatoActoDocumento> getPosteriores() {
		return posteriores;
	}

	public void setPosteriores(ArrayList<DatoActoDocumento> posteriores) {
		this.posteriores = posteriores;
	}

	public String getIdSuboperacion() {
		return idSuboperacion;
	}

	public void setIdSuboperacion(String idSuboperacion) {
		this.idSuboperacion = idSuboperacion;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Integer getNumacto() {
		return numacto;
	}

	public void setNumacto(Integer numacto) {
		this.numacto = numacto;
	}

	public Suboperacion getSuboperacion() {
		return suboperacion;
	}

	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
		if(suboperacion!=null){
			this.idSuboperacion = this.suboperacion.getIdsuboperacion();
		}
	}
	
	

}
