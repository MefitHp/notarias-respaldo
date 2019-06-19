package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AlertaObjeto;

public interface AlertaObjetoBo extends GenericBo<AlertaObjeto> {
	
	public AlertaObjeto obtenerPorAlerta(AlertaObjeto ao) throws NotariaException;

	public AlertaObjeto getByObjeto(String idalertaobjeto);
	
}
