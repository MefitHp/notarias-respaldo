package com.palestra.notaria.dao;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoOperacion;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;

public interface ExpedienteDao extends GenericDao<Expediente, Integer> {
	
	public Expediente buscarPorIdCompleto(Expediente expediente) throws NotariaException;
	
	public ArrayList<DatoOperacion> buscarOperaciones(String idExpediente, String idOperacion) throws Exception;
	
	public boolean eliminarOperacion(String idExpediente, String idOperacion, String idusuario) throws NotariaException;
	
	public boolean guardarOperacion(DatoOperacion datoOperacion, String idExpediente, String idSesion, Timestamp tstmp, String idusuario) throws NotariaException;
	
	public boolean actualizarOperacion(DatoOperacion datoOperacion, String idexpediente, String idsesion, Timestamp tstmp, String idusuario) throws NotariaException;
	
	public boolean esoperacionUnica(String idExpediente, String idOperacion) throws NotariaException;
	
	public String obtenerNumExpedientePorId(String id) throws NotariaException;
	
	public List<Persona> obtenerClientesDeExpediente() throws NotariaException;
	
	public String obtenerTramitePorExpedienteId(String idexpediente) throws NotariaException;
	
	public boolean exiteTramiteRegistrado(String idtramite) throws NotariaException;

	List<Expediente> listaExpedientes() throws NotariaException;

	public List<Expediente> obtenerExpedientes(Expediente expediente,Compareciente... compareciente) throws NotariaException;

	public List<Expediente> obtenerExpedienteXAbogado(String idabogado) throws NotariaException;

	List<Expediente> obtenerExpedienteXAbogado(String idabogado, String year) throws NotariaException;
	
}
