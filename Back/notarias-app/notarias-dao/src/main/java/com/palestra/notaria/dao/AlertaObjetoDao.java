package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AlertaObjeto;

public interface AlertaObjetoDao  extends GenericDao<AlertaObjeto, Integer>{

	AlertaObjeto findByAlerta(String idalerta)throws NotariaException;

	AlertaObjeto findByObjeto(String idalertaobjeto);

	
}
