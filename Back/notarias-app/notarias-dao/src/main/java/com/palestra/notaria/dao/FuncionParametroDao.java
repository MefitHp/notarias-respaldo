package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FuncionParametro;

public interface FuncionParametroDao extends
		GenericDao<FuncionParametro, Integer> {
	FuncionParametro findById(Integer id) throws NotariaException;
}
