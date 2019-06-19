package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.DevolucionRechazadaFolioBo;
import com.palestra.notaria.dao.DevolucionRechazadaFolioDao;
import com.palestra.notaria.dao.impl.DevolucionRechazadaFolioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DevolucionRechazadaFolio;

public class DevolucionRechazadaFolioBoImpl extends
		GenericBoImpl<DevolucionRechazadaFolio> implements DevolucionRechazadaFolioBo {
	
	DevolucionRechazadaFolioDao devolucionRecDao;
	
	public DevolucionRechazadaFolioBoImpl(){
		devolucionRecDao = new DevolucionRechazadaFolioDaoImpl();
		super.dao = devolucionRecDao;
	}

	@Override
	public List<DevolucionRechazadaFolio> listarRechazados() throws NotariaException {
		return devolucionRecDao.listarRechazados();
	}

	
}
