package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public interface FormatoPDFDetalleDao extends GenericDao<FormatoPDFDetalle, Integer> {

	FormatoPDFDetalle findById(Integer id) throws NotariaException;
	
}
