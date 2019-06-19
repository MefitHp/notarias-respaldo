package com.palestra.notaria.dato;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Ya que los invitados de las citas pueden ser comparecientes o usuario, se 
 * opto por unar DatoInvitadoCita como dto para guardar informacion de 
 * usuario y compareciente englobados en esta entidad. 
 * @author sofia
 *
 */
public class DatoInvitadoCita implements Serializable{

	private static final long serialVersionUID = -1429859219028715549L;
	
	/** invitado **/
	private String id;
	
	/** Si es 'compareciente' o 'usuario '**/
	private String tipo;
	
	private String dsnombre;
	
	private String dspaterno;
	
	private String dsmaterno;
	
	private String dscorreoelectronico;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDspaterno() {
		return dspaterno;
	}

	public void setDspaterno(String dspaterno) {
		this.dspaterno = dspaterno;
	}

	public String getDsmaterno() {
		return dsmaterno;
	}

	public void setDsmaterno(String dsmaterno) {
		this.dsmaterno = dsmaterno;
	}

	public String getDscorreoelectronico() {
		return dscorreoelectronico;
	}

	public void setDscorreoelectronico(String dscorreoelectronico) {
		this.dscorreoelectronico = dscorreoelectronico;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("id", this.getId())
		        .append("tipo", this.getTipo())
		        .append("dsnombre", this.getDsnombre())
		        .append("dspaterno", this.getDspaterno())
		        .append("dsmaterno", this.getDsmaterno())
		        .append("dscorreoelectronico", this.getDscorreoelectronico())
                .toString();
	}
}
