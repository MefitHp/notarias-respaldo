package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;

public interface EscrituraDao extends GenericDao<Escritura, Integer> {
	
	List<Escritura> findByExpedienteId(String id) throws NotariaException;
	
	String obtenNumEscritura(String id) throws NotariaException;
	
	Escritura buscarPorIdCompleto(Escritura escritura) throws NotariaException;
	
	Boolean registrarEscritura(Escritura escritura, List<EscrituraActo> actosDeEscritura) throws NotariaException;
	
	Boolean actualizarActosNotario(Escritura escritura, List<EscrituraActo> actosDeEscritura) throws NotariaException;
	
	Boolean actualizarPropsEscritura(Escritura escritura) throws NotariaException;
	
	String getExpedienteIdByEscrituraId(String id) throws NotariaException;
	
	Boolean setFirmaDittoByEscrituraId(String id, Boolean isfirmaditto) throws NotariaException;
	
	Boolean eliminarEscritura(String id) throws NotariaException;
	
	Boolean tieneNumEscritura(String idEscritura) throws NotariaException;
	
	Boolean switchNotario(Escritura escritura) throws NotariaException;
	
	String obtenerIdNotarioPorEscrituraId(String idEscritura) throws NotariaException;

	public Escritura getByNumeroEscritura(String numescritura) throws NotariaException;

	List<Escritura> getXnumLibroStatus(Long numerolibroInicial, Long numerolibroFinal, Boolean nopaso)
			throws NotariaException;

	Boolean actualizarPropsEscrituraPizarron(Escritura escritura, PizarronElemento pizarron) throws NotariaException;


	
}
