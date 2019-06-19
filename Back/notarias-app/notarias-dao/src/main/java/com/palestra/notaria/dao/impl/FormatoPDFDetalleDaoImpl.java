package com.palestra.notaria.dao.impl;


import java.util.List;

import com.palestra.notaria.dao.FormatoPDFDetalleDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDFDetalle;

public class FormatoPDFDetalleDaoImpl extends GenericDaoImpl<FormatoPDFDetalle, Integer> implements
		FormatoPDFDetalleDao {

	public FormatoPDFDetalleDaoImpl() {
		super(FormatoPDFDetalle.class);
	}

	
	@Override
	public FormatoPDFDetalle findById(Integer id) throws NotariaException{
		List<FormatoPDFDetalle> detalles = executeQuery("SELECT fd FROM FormatoPDFDetalle fd WHERE fd.identificador = ?1", id);
		if(detalles!=null && detalles.size()>0){
			return detalles.get(0);
		}else{
			return null;
		}
	}
}
