package com.palestra.notaria.uif.core.models;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;



/**
 * The persistent class for the tbbsnm28 database table.
 * 
 */
@Entity
@Table(name = "personas")

public class Persona implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idpersona;
	
	@OneToMany(cascade=CascadeType.ALL,mappedBy="persona",orphanRemoval=true)
	private List<Uif> uifs  = new ArrayList<Uif>();
	

	private String dsnombre;
	
	private String dsapellidomat;

	private String dsapellidopat;

	private String dscurp;

	private String dsnombrecompleto;

	private String dsrfc;
	
	protected Persona() {
		
	}
	
	public  Persona(String nombre,String paterno,String materno){
		this.dsnombre = nombre;
		this.dsapellidopat = paterno;
		this.dsapellidomat = materno;
		
		StringBuilder nombrecompleto = new StringBuilder(dsnombre.trim());
		if(dsapellidopat!=null){
			nombrecompleto.append(" "+paterno.trim());    
		}
		if(dsapellidomat!=null){
			nombrecompleto.append(" "+materno.trim());
		}
		this.dsnombrecompleto = nombrecompleto.toString();
		
	
	}
	
	
	
    @Temporal(TemporalType.TIMESTAMP)
		private Date created;
		@Temporal(TemporalType.TIMESTAMP)
		private Date updated;

		public String getIdpersona() {
			return idpersona;
		}
		public void setIdpersona(String idpersona) {
			this.idpersona = idpersona;
		}
		public String getDsnombre() {
			return dsnombre;
		}
		public void setDsnombre(String dsnombre) {
			this.dsnombre = dsnombre;
		}
		public String getDsapellidomat() {
			return dsapellidomat;
		}
		public void setDsapellidomat(String dsapellidomat) {
			this.dsapellidomat = dsapellidomat;
		}
		public String getDsapellidopat() {
			return dsapellidopat;
		}
		public void setDsapellidopat(String dsapellidopat) {
			this.dsapellidopat = dsapellidopat;
		}
		public String getDscurp() {
			return dscurp;
		}
		public void setDscurp(String dscurp) {
			this.dscurp = dscurp;
		}
		public String getDsnombrecompleto() {
			return dsnombrecompleto;
		}
		public void setDsnombrecompleto(String dsnombrecompleto) {
			this.dsnombrecompleto = dsnombrecompleto;
		}
		public String getDsrfc() {
			return dsrfc;
		}
		public void setDsrfc(String dsrfc) {
			this.dsrfc = dsrfc;
		}
		public Date getCreated() {
			return created;
		}
		public void setCreated(Date created) {
			this.created = created;
		}
		public Date getUpdated() {
			return updated;
		}
		public void setUpdated(Date updated) {
			this.updated = updated;
		}
		
		
		
		public void addUif(Uif uif) {
	        uifs.add(uif);
	        uif.setIdpersona(this);
	    }

	    public void removeUif(Uif uif) {
	    	uif.setIdpersona(null);
	        this.uifs.remove(uif);
	    }
		
			
}