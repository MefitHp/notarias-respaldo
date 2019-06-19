package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public interface FormatoPDFDao extends GenericDao<FormatoPDF, Integer> {

	List<FormatoPDFDetalle> formatoDetalle(String identificador)throws NotariaException;
	void eliminaDetalles(String identificador)throws NotariaException;
	public FormatoPDF buscarXNombre(String nombre) throws NotariaException;
}
