package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Rol;

public interface ElementoCatalogoDao extends GenericDao<ElementoCatalogo, Integer>{
	
	public List<ElementoCatalogo> listarProcesosPorRol(Rol rol) throws NotariaException;
	
	public List<ElementoCatalogo> listarPorCatalogo(Catalogo catalogo)throws NotariaException;

	public ElementoCatalogo buscarXcodigo(String codigo) throws NotariaException;
	
}
