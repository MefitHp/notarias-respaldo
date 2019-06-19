package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.EscrituraExterna;

public interface EscrituraExternaDao extends
		GenericDao<EscrituraExterna, Integer> {

	public EscrituraExterna guardarEscrituraExterna(EscrituraExterna escritura) throws NotariaException;

	public EscrituraExterna findByNumero(String numero) throws NotariaException;
}
