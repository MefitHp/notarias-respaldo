package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.CatalogoBo;
import com.palestra.notaria.dao.CatalogoDao;
import com.palestra.notaria.dao.impl.CatalogoDaoImpl;
import com.palestra.notaria.modelo.Catalogo;

public class CatalogoBoImpl extends GenericBoImpl<Catalogo> implements CatalogoBo{
	
	private CatalogoDao catalogoDao;
	
	public CatalogoBoImpl() {
		this.catalogoDao = new CatalogoDaoImpl();
		super.dao = this.catalogoDao;
	}

}
