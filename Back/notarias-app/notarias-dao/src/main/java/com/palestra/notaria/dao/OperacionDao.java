package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;

public interface OperacionDao extends GenericDao<Operacion, Integer> {

	public List<Suboperacion> OperacionesPorOperacion(String idoperacion) throws NotariaException;
}
