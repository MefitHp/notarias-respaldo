package com.palestra.notaria.dato;

public class DatoVariableFormulario {

	private String nombre;
	private String desc;
	private String data;
	private String identificador;
	private String nombreFormulario;
	private Integer versionFormulario;
	
	public String getNombreFormulario() {
		return nombreFormulario;
	}
	public void setNombreFormulario(String nombreFormulario) {
		this.nombreFormulario = nombreFormulario;
	}
	public Integer getVersionFormulario() {
		return versionFormulario;
	}
	public void setVersionFormulario(Integer integer) {
		this.versionFormulario = integer;
	}
	
	public String getIdentificador() {
		return identificador;
	}
	public void setIdentificador(String identificador) {
		this.identificador = identificador;
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
