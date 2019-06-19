package com.palestra.notaria.uif.core.dao;

import java.util.List;

import com.palestra.notaria.uif.core.models.Uif;
import com.palestra.notaria.uif.exceptions.NotariaException;

public interface UifDao extends GenericDao<Uif, Integer>{
	
	public Long getTotalUifs() throws NotariaException;
	public Long getTotalPage() throws NotariaException;
	public Uif findByNombrePersona(String nombrecompleto) throws NotariaException;
	public List<Uif> buscarXEscritura(Long escritura) throws NotariaException;
	public List<Uif> buscarXPagina(Long pagina) throws NotariaException;
	public void borrar(Uif uif) throws NotariaException;
	public List<Uif> buscar(String tipo,String param)throws NotariaException;
	
}
