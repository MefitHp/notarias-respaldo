package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Rol;

public interface ElementoCatalogoBo extends GenericBo<ElementoCatalogo>{
	
	public List<ElementoCatalogo> listarProcesosPorRol(Rol rol) throws NotariaException;
	
	public List<ElementoCatalogo> listarPorCatalogo(Catalogo catalogo)throws NotariaException;

	ElementoCatalogo buscarXcodigo(String codigo) throws NotariaException;

}
