package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Suboperacion;

public interface OperacionBo extends GenericBo<Operacion> {
	
	public List<Suboperacion> OperacionesPorOperacion(String idoperacion) throws NotariaException;

}
