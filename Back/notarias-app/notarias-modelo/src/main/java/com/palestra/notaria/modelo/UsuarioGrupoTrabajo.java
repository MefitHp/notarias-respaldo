package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.*;


/**
 * The persistent class for the tbcfgm11 database table.
 * 
 */
@Entity
@Table(name="tbcfgm13")
public class UsuarioGrupoTrabajo implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idusuariogrupotrabajo;
	
	@ManyToOne
	@JoinColumn(name="idgrupotrabajo")
	private GrupoTrabajo grupoTrabajo;
	
	@ManyToOne
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	public String getIdusuariogrupotrabajo() {
		return idusuariogrupotrabajo;
	}

	public void setIdusuariogrupotrabajo(String idusuariogrupotrabajo) {
		this.idusuariogrupotrabajo = idusuariogrupotrabajo;
	}

	public GrupoTrabajo getGrupoTrabajo() {
		return grupoTrabajo;
	}

	public void setGrupoTrabajo(GrupoTrabajo grupoTrabajo) {
		this.grupoTrabajo = grupoTrabajo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
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

	
	
	private String idsesion;
	private Timestamp tmstmp;

    public UsuarioGrupoTrabajo() {
    }

	
}