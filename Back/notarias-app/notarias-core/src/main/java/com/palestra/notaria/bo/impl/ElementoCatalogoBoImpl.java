package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ElementoCatalogoBo;
import com.palestra.notaria.dao.ElementoCatalogoDao;
import com.palestra.notaria.dao.impl.ElementoCatalogoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Catalogo;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Rol;

public class ElementoCatalogoBoImpl extends GenericBoImpl<ElementoCatalogo> implements ElementoCatalogoBo{

	private ElementoCatalogoDao elementoCatalogoDao;
	
	public ElementoCatalogoBoImpl() {
		this.elementoCatalogoDao = new ElementoCatalogoDaoImpl();
		super.dao = this.elementoCatalogoDao;
	}

	@Override
	public List<ElementoCatalogo> listarProcesosPorRol(Rol rol) throws NotariaException{
		return this.elementoCatalogoDao.listarProcesosPorRol(rol);
	}

	@Override
	public List<ElementoCatalogo> listarPorCatalogo(Catalogo catalogo) throws NotariaException {
		return this.elementoCatalogoDao.listarPorCatalogo(catalogo);
	}
	
	@Override
	public ElementoCatalogo buscarXcodigo(String codigo) throws NotariaException{
		return this.elementoCatalogoDao.buscarXcodigo(codigo);
	}

}
