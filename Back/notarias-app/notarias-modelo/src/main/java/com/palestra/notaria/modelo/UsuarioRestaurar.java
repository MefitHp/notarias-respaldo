package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm04")
@NamedQueries(value={
		@NamedQuery(name="UsuarioRestaurar.encuentraPeticionesUsuario", query="SELECT ur FROM UsuarioRestaurar ur WHERE ur.usuario = :usuario"),
		@NamedQuery(name="UsuarioRestaurar.encuentraPeticionActivaUsuario", query="SELECT ur FROM UsuarioRestaurar ur WHERE ur.usuario = :usuario AND ur.estatus = 'P' ORDER BY ur.vencimiento DESC"),		
		@NamedQuery(name="UsuarioRestaurar.encuentraPeticionCorreo", query="SELECT ur FROM UsuarioRestaurar ur WHERE ur.usuario.dscorreo = :correo AND ur.estatus = 'P'")
})
public class UsuarioRestaurar implements Serializable{
	private static final long serialVersionUID = 1L;

	@Id
	private String idpeticion;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idusuario")
	private Usuario usuario;
	
	@Column(name="dtfecha")
	private Timestamp fecha;
	
	@Column(name="inestatus")
	private String estatus;
	
	@Column(name="dtvencimiento")
	private Timestamp vencimiento; 
	
	@Column(name="dsnewpasswd")
	private String nuevaContrasenia;
	
	private Timestamp tmstmp;
	
	public UsuarioRestaurar() {
		
	}

	public String getIdpeticion() {
		return idpeticion;
	}

	public void setIdpeticion(String idpeticion) {
		this.idpeticion = idpeticion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public Timestamp getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Timestamp vencimiento) {
		this.vencimiento = vencimiento;
	}

	public String getNuevaContrasenia() {
		return nuevaContrasenia;
	}

	public void setNuevaContrasenia(String nuevaContrasenia) {
		this.nuevaContrasenia = nuevaContrasenia;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

}
