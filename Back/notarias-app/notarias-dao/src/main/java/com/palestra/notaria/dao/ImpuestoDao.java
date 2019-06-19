package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Impuesto;

public interface ImpuestoDao extends GenericDao<Impuesto, Integer>{

	public Impuesto obtenerImpuestoById(String idimpuesto, String sigla) throws NotariaException;
}
