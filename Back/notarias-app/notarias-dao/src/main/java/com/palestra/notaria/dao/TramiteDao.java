package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;

public interface TramiteDao extends GenericDao<Tramite, Integer>{
	
	Tramite buscarPorIdCompleto(Tramite tramite) throws NotariaException;

	Long buscarPosicionTramitePorAbogado(String idusuario) throws NotariaException;
	
	List<Tramite> buscarTramitesPorCliente(String idCliente) throws NotariaException;
	
	Expediente buscarIdExpediente(String idtramite) throws NotariaException;

	@Override
	List<Tramite> findAll() throws NotariaException;

	List<Tramite> findByAbogado(String idusuario)throws NotariaException;

	Tramite findByExpediente(Expediente expediente) throws NotariaException;
	
}
