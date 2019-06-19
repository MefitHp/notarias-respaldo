package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ProcessActo;

public interface ProcessActoDao extends GenericDao<ProcessActo, Integer>{

	ProcessActo findByActo(Acto acto) throws NotariaException;
}
