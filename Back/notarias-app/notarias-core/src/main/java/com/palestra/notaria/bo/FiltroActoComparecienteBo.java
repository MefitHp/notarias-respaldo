package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FiltroActoCompareciente;
import com.palestra.notaria.modelo.Operacion;


public interface FiltroActoComparecienteBo extends GenericBo<FiltroActoCompareciente> {
	public List<FiltroActoCompareciente> comparecientesXSubop(Operacion sop)throws NotariaException;

	public void borrar(FiltroActoCompareciente filtro)throws NotariaException;


}
