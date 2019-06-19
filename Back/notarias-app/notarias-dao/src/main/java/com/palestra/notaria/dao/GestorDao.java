package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Gestor;

public interface GestorDao extends GenericDao<Gestor, Integer> {

	List<Gestor> findByLocacion(ElementoCatalogo elemento)
			throws NotariaException;

}
