package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;

public class UsuarioEnvio extends GenericEnvio {
	
	private ArrayList<Usuario> usuarioList = null;
	
	private String idusuarioactualiza=null;
	
	private Rol rol = null;
	
	private String tokenRestore;
	
	public String getIdusuarioactualiza() {
		return idusuarioactualiza;
	}
	
	public void setIdusuarioactualiza(String idusuarioactualiza) {
		this.idusuarioactualiza = idusuarioactualiza;
	}
	
	public void setUsuarioList(ArrayList<Usuario> usuarioList) {
		this.usuarioList = usuarioList;
	}
	
	public ArrayList<Usuario> getUsuarioList() {
		return usuarioList;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public String getTokenRestore(){
		return this.tokenRestore;
	}

	public void setTokenRestore(String tokenRestore){
		this.tokenRestore = tokenRestore;
	}
	
}
