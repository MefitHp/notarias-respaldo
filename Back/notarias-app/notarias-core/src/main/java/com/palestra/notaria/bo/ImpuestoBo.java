package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Impuesto;

public interface ImpuestoBo extends GenericBo<Impuesto>{

	public Impuesto obtenerImpuestoById(String idimpuesto, String sigla) throws NotariaException;
}
