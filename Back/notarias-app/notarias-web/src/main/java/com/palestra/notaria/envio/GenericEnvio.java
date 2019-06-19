package com.palestra.notaria.envio;

import java.util.List;

import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.modelo.Usuario;

public class GenericEnvio {

	protected Usuario usuario;
	protected String estatus;
	protected boolean exito = true;
	protected List<CodigoError> errores;

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getEstatus() {
		return estatus;
	}

	public void setEstatus(String estatus) {
		this.estatus = estatus;
	}

	public boolean isExito() {
		return exito;
	}

	public void setExito(boolean exito) {
		this.exito = exito;
	}

	public List<CodigoError> getErrores() {
		return errores;
	}

	public void setErrores(List<CodigoError> errores) {
		this.errores = errores;
	}

}
