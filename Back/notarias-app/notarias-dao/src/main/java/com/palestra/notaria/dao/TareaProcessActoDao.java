package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.TareaProcessActo;

public interface TareaProcessActoDao extends GenericDao<TareaProcessActo, Integer> {

	TareaProcessActo getActiveByActo(Acto acto) throws NotariaException;
}
