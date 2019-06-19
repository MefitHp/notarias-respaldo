package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm17")
public class ProcesoRol implements Serializable{

	private static final long serialVersionUID = 677963422678978704L;

	@EmbeddedId
	ProcesoRolPK id;

	@ManyToOne
	@JoinColumn(name = "idrol", referencedColumnName = "idrol", insertable = false, updatable = false)
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name = "idproceso", referencedColumnName = "idelemento", insertable = false, updatable = false)
	private ElementoCatalogo proceso;

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public ElementoCatalogo getProceso() {
		return proceso;
	}

	public void setProceso(ElementoCatalogo proceso) {
		this.proceso = proceso;
	}

	public ProcesoRolPK getId() {
		return id;
	}

	public void setId(ProcesoRolPK id) {
		this.id = id;
	}
}
