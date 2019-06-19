package com.palestra.notaria.dao.impl;

import java.util.List;

import com.palestra.notaria.dao.DevolucionRechazadaFolioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DevolucionRechazadaFolio;

public class DevolucionRechazadaFolioDaoImpl extends
		GenericDaoImpl<DevolucionRechazadaFolio, Integer> implements
		DevolucionRechazadaFolioDao {

	public DevolucionRechazadaFolioDaoImpl(){
		super(DevolucionRechazadaFolio.class);
	}
	
	@Override
	public List<DevolucionRechazadaFolio> listarRechazados() throws NotariaException{
		return executeQuery("SELECT d FROM DevolucionRechazadaFolio d WHERE d.isresuelta = false");
	}
}
