package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.bo.AlertaObjetoBo;
import com.palestra.notaria.dao.AlertaObjetoDao;
import com.palestra.notaria.dao.impl.AlertaObjetoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AlertaObjeto;
import com.palestra.notaria.util.GeneradorId;

public class AlertaObjetoBoImpl extends GenericBoImpl<AlertaObjeto> implements AlertaObjetoBo {
	
	private AlertaObjetoDao alertaObjetoDao;
	
	public AlertaObjetoBoImpl() {
		this.alertaObjetoDao = new AlertaObjetoDaoImpl();
		super.dao = this.alertaObjetoDao;
	}
	
	
	
	@Override
	public AlertaObjeto save(AlertaObjeto alertaObjeto) throws NotariaException{
		
		alertaObjeto.setUpdated(new Timestamp(new Date().getTime()));
		
		if(alertaObjeto.getIdalertaobjeto()!= null){
			//alertaObjeto.setIdalerta(GeneradorId.generaId(alertaObjeto));
			
			super.save(alertaObjeto);
		}else{
			super.update(alertaObjeto);
		}
		return alertaObjeto;
	}



	@Override
	public AlertaObjeto obtenerPorAlerta(AlertaObjeto ao) throws NotariaException {
		return alertaObjetoDao.findByAlerta(ao.getIdalertaobjeto());
	}



	@Override
	public AlertaObjeto getByObjeto(String idalertaobjeto) {
		// TODO Auto-generated method stub
		return alertaObjetoDao.findByObjeto(idalertaobjeto);
	}

}
