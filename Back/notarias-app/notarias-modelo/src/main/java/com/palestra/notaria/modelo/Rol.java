package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the tbcfgm07 database table.
 * 
 */
@Entity
@Table(name="tbcfgm07")
@NamedQueries(value={
		@NamedQuery(name="Rol.findById", query="SELECT r FROM Rol r WHERE r.idrol = :idrol"),
		@NamedQuery(name="Rol.findByPrefijo", query = "SELECT r FROM Rol r WHERE r.dsprefijo = :prefijo")
})
public class Rol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idrol;

	private String dsdescripcion;

	private String dsnombre;
	
	private String dsprefijo;

	private String idsesion;

	private Timestamp tmstmp;
	
	//bi-directional many-to-one association to Usuario
//	@OneToMany(mappedBy="tbcfgm07")
//	private List<Usuario> tbcfgm03s;

//	//bi-directional many-to-many association to ElementoCatalogo
//	@ManyToMany(mappedBy="tbcfgm07s")
//	private List<ElementoCatalogo> tbcfgm91s;

    public Rol() {
    }

	public String getIdrol() {
		return this.idrol;
	}

	public void setIdrol(String idrol) {
		this.idrol = idrol;
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
	

	public String getDsprefijo() {
		return dsprefijo;
	}
	
	public void setDsprefijo(String dsprefijo) {
		this.dsprefijo = dsprefijo;
	}
	
//	public List<Usuario> getTbcfgm03s() {
//		return this.tbcfgm03s;
//	}
//
//	public void setTbcfgm03s(List<Usuario> tbcfgm03s) {
//		this.tbcfgm03s = tbcfgm03s;
//	}
	
//	public List<ElementoCatalogo> getTbcfgm91s() {
//		return this.tbcfgm91s;
//	}
//
//	public void setTbcfgm91s(List<ElementoCatalogo> tbcfgm91s) {
//		this.tbcfgm91s = tbcfgm91s;
//	}
	
}