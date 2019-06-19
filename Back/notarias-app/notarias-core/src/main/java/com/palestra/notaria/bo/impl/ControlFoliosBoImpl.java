package com.palestra.notaria.bo.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.palestra.notaria.bo.ControlFoliosBo;
import com.palestra.notaria.dao.ControlFoliosDao;
import com.palestra.notaria.dao.impl.ControlFoliosDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;

public class ControlFoliosBoImpl extends GenericBoImpl<ControlFolios> implements
		ControlFoliosBo {
	
	ControlFoliosDao controlFoliosDao;
	
	public ControlFoliosBoImpl(){
		controlFoliosDao = new ControlFoliosDaoImpl();
		super.dao = controlFoliosDao;
	}
	
	
	@Override
	public ControlFolios getFolios() throws NotariaException{
		/*ControlFolios controlFolio = new ControlFolios(); 
		try {
			List<ControlFolios> folios = controlFoliosDao.findAll();
			if(folios!=null && folios.size()<1){
				return controlFolio;
			}else{
				Collections.reverse(folios);
				controlFolio = folios.get(0);
			}
		} catch (NotariaException e) {
			throw new NotariaException("Ocurrio un error en la busqueda de folios",e);
		}
		return controlFolio;*/
		return this.controlFoliosDao.getUltimoFolio();
	}


	@Override
	public ControlFolios getUltimo() throws NotariaException {
		return this.controlFoliosDao.getUltimoFolio();
	}
	
	
	

}
