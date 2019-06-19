package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Formulario;

public class FormularioDinamicoEnvio extends GenericEnvio{

	private String idConformulario = null;
	private Integer version = null;
	private String idsubformulario = null;
	private String idsuboperacion = null;
	private ConFormulario conformulario = null;
	private Expediente expediente = null;
	private Acto acto = null;
	private ArrayList<ActoFormulario> listaFormularios = null;
	private ArrayList<Formulario> listaValoresFormularios = null;
	
	private ArrayList<ConFormulario> formularioList = null;
	private ArrayList<Componente> componentesList = null;
	
	
	public ArrayList<ConFormulario> getFormularioList() {
		return formularioList;
	}
	public void setFormularioList(ArrayList<ConFormulario> formularioList) {
		this.formularioList = formularioList;
	}
	public ArrayList<Componente> getComponentesList() {
		return componentesList;
	}
	public void setComponentesList(ArrayList<Componente> componentesList) {
		this.componentesList = componentesList;
	}
	
	public String getIdConformulario() {
		return idConformulario;
	}
	public void setIdConformulario(String idConformulario) {
		this.idConformulario = idConformulario;
	}
	public String getIdsubformulario() {
		return idsubformulario;
	}
	public void setIdsubformulario(String idsubformulario) {
		this.idsubformulario = idsubformulario;
	}
	public String getIdsuboperacion() {
		return idsuboperacion;
	}
	public void setIdsuboperacion(String idsuboperacion) {
		this.idsuboperacion = idsuboperacion;
	}
	public ConFormulario getConformulario() {
		return conformulario;
	}
	public void setConformulario(ConFormulario conformulario) {
		this.conformulario = conformulario;
	}
	public Integer getVersion() {
		return version;
	}
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public Acto getActo() {
		return acto;
	}
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	public ArrayList<ActoFormulario> getListaFormularios() {
		return listaFormularios;
	}
	public void setListaFormularios(ArrayList<ActoFormulario> listaFormularios) {
		this.listaFormularios = listaFormularios;
	}
	public ArrayList<Formulario> getListaValoresFormularios() {
		return listaValoresFormularios;
	}
	public void setListaValoresFormularios(
			ArrayList<Formulario> listaValoresFormularios) {
		this.listaValoresFormularios = listaValoresFormularios;
	}
	
}
