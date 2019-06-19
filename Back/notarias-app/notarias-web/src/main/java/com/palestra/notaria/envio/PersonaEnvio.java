package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoBusquedaPersona;
import com.palestra.notaria.modelo.Persona;

public class PersonaEnvio extends GenericEnvio {
	
	private Persona persona;
	
	private String nombrePersona;
	private Integer numeroResultados;
	private ArrayList<DatoBusquedaPersona> resultados = null;
	private List<Persona> personaList = new ArrayList<Persona>();
	
	public PersonaEnvio(){}
	
	public List<Persona> getPersonaList() {
		return personaList;
	}
	public void setPersonaList(List<Persona> personaList) {
		this.personaList = personaList;
	}
	
	public Persona getPersona() {
		return persona;
	}
	
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	
	public String getNombrePersona() {
		return nombrePersona;
	}
	
	public void setNombrePersona(String nombrePersona) {
		this.nombrePersona = nombrePersona;
	}

	public Integer getNumeroResultados() {
		return numeroResultados;
	}
	
	public void setNumeroResultados(Integer numeroResultados) {
		this.numeroResultados = numeroResultados;
	}
	
	public ArrayList<DatoBusquedaPersona> getResultados() {
		return resultados;
	}

	public void setResultados(ArrayList<DatoBusquedaPersona> resultados) {
		this.resultados = resultados;
	}
	
	

}
