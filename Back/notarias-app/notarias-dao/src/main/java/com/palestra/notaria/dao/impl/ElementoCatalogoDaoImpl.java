package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.ElementoCatalogoDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Rol;

public class ElementoCatalogoDaoImpl extends GenericDaoImpl<ElementoCatalogo, Integer> implements ElementoCatalogoDao{

	public ElementoCatalogoDaoImpl() {
		super(ElementoCatalogo.class);
	}

	@Override
	public List<ElementoCatalogo> listarProcesosPorRol(Rol rol) throws NotariaException {
			return executeQuery("SELECT r FROM ElementoCatalogo r WHERE r.rol = ?1",rol);
	}

	@Override
	public List<ElementoCatalogo> listarPorCatalogo(Catalogo catalogo)throws NotariaException {
		List<ElementoCatalogo> elementosList;
		try {
			elementosList = executeQuery("SELECT c FROM ElementoCatalogo c WHERE c.catalogo.dsnombre = ?1", catalogo.getDsnombre());
			if(elementosList != null && !elementosList.isEmpty()){
				for(ElementoCatalogo elementoCat:elementosList){
					elementoCat.setCatalogo(null);
				}
			}else{
				throw new NotariaException("El tipo de catalogo no existe o hubo un error al recuperar");
			}
		}catch (Exception e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}
		return elementosList;
	}

	@Override
	public ElementoCatalogo buscarXcodigo(String codigo) throws NotariaException {
		List<ElementoCatalogo> listaElemento = executeQuery("SELECT r FROM ElementoCatalogo r WHERE r.dscodigo = ?1",codigo);
		if(listaElemento.size()>0){
			return listaElemento.get(0);
		}
		return null;
	}
	
}
