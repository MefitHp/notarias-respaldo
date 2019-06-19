package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name="tbbsnm48")	
@NamedQueries({
	@NamedQuery(name="PermisoRol.findById", query="SELECT pr FROM PermisoRol pr WHERE pr.idpermisorol = :identificador"),
	@NamedQuery(name="PermisoRol.deleteById", query="DELETE FROM PermisoRol WHERE idpermisorol = :identificador"),
	@NamedQuery(name="PermisoRol.findAll", query="SELECT pr FROM PermisoRol pr WHERE pr.conFormulario.id.idconFormulario = :idconformulario AND pr.conFormulario.id.version = :version"),
	@NamedQuery(name="PermisoRol.deleteAll", query="DELETE FROM PermisoRol WHERE conFormulario.id.idconFormulario = :idconformulario AND conFormulario.id.version = :version"),
})
public class PermisoRol implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idpermisorol;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Rol
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="idrol")
	private Rol rol;

	//bi-directional many-to-one association to ConFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idconformulario", referencedColumnName="idconFormulario"),
		@JoinColumn(name="versionform", referencedColumnName="version")
		})
	private ConFormulario conFormulario;
	
	// la logica de implementacion es la misma que la de unix
	// binarios 001 010 100 011 101 111 
	private int inpermiso;
	
	public PermisoRol() {
	}

	public String getIdpermisorol() {
		return this.idpermisorol;
	}

	public void setIdpermisorol(String idpermisorol) {
		this.idpermisorol = idpermisorol;
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

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public ConFormulario getConFormulario() {
		return conFormulario;
	}

	public void setConFormulario(ConFormulario conFormulario) {
		this.conFormulario = conFormulario;
	}

	public int getInpermiso() {
		return inpermiso;
	}

	public void setInpermiso(int inpermiso) {
		this.inpermiso = inpermiso;
	}

}