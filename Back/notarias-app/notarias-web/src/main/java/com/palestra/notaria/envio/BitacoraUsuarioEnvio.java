package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.modelo.BitacoraUsuario;

public class BitacoraUsuarioEnvio extends GenericEnvio {

	
	private String idusuario;
	private String idgrupotrabajo;
	private BitacoraUsuario bitacorausuario;
	private List<BitacoraUsuario> bitacoras;
	private Integer totalpendientes;
	
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public String getIdgrupotrabajo() {
		return idgrupotrabajo;
	}
	public void setIdgrupotrabajo(String idgrupotrabajo) {
		this.idgrupotrabajo = idgrupotrabajo;
	}
	public List<BitacoraUsuario> getBitacoras() {
		return bitacoras;
	}
	public void setBitacoras(List<BitacoraUsuario> bitacoras) {
		this.bitacoras = bitacoras;
	}
	public BitacoraUsuario getBitacorausuario() {
		return bitacorausuario;
	}
	public void setBitacorausuario(BitacoraUsuario bitacorausuario) {
		this.bitacorausuario = bitacorausuario;
	}
	
	public Integer getTotalpendientes() {
		return totalpendientes;
	}
	public void setTotalpendientes(Integer totalpendientes) {
		this.totalpendientes = totalpendientes;
	}
	




}
