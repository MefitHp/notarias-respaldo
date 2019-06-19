package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.ProcesoRol;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.RolGestionCompetencia;

public class RolEnvio extends GenericEnvio {
	
	private ArrayList<Rol> rolesList=null;
	
	private ArrayList<ProcesoRol> procesoList=null;
	
	private List<RolGestionCompetencia> competencias;
	
	private String competenciaPantalla;
	
	private Rol rol = null;
	
	public Rol getRol() {
		return rol;
	}
	public void setRol(Rol rol) {
		this.rol = rol;
	}
	
	public ArrayList<ProcesoRol> getProcesoList() {
		return procesoList;
	}
	
	public void setProcesoList(ArrayList<ProcesoRol> procesoList) {
		this.procesoList = procesoList;
	}
	
//	public ArrayList<ElementoCatalogo> getProcesoList() {
//		return procesoList;
//	}
//	
//	public void setProcesoList(ArrayList<ElementoCatalogo> procesoList) {
//		this.procesoList = procesoList;
//	}
	
	public ArrayList<Rol> getRolesList() {
		return rolesList;
	}
	
	public void setRolesList(ArrayList<Rol> rolesList) {
		this.rolesList = rolesList;
	}
	public List<RolGestionCompetencia> getCompetencias() {
		return competencias;
	}
	public void setCompetencias(List<RolGestionCompetencia> competencias) {
		this.competencias = competencias;
	}
	public String getCompetenciaPantalla() {
		return competenciaPantalla;
	}
	public void setCompetenciaPantalla(String competenciaPantalla) {
		this.competenciaPantalla = competenciaPantalla;
	}

}
