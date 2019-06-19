package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.CatalogoDao;
import com.palestra.notaria.modelo.Catalogo;

public class CatalogoDaoImpl extends GenericDaoImpl<Catalogo, Integer> implements CatalogoDao {

	public CatalogoDaoImpl(){
		super(Catalogo.class);
	}
}
