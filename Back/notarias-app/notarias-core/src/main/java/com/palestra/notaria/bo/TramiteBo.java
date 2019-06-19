package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;

public interface TramiteBo extends GenericBo<Tramite>{
	
	public Tramite buscarPorIdCompleto(Tramite tramite) throws NotariaException;
	
	public Long buscarPosicionTramitePorAbogado(String idusuario) throws NotariaException;
	
	public List<Tramite> buscarTramitesPorCliente(String idCliente) throws NotariaException;
	
	public Expediente buscarIdExpediente(String idtramite) throws NotariaException;
	
	@Override
	public List<Tramite> findAll() throws NotariaException;

	public List<Tramite> findByAbogado(String idusuario)throws NotariaException;
	
	public Tramite buscarPorExpediente(Expediente expediente) throws NotariaException;
}
