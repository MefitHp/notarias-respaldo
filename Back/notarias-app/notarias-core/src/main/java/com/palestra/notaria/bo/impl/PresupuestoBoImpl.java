package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.PresupuestoBo;
import com.palestra.notaria.dao.PresupuestoDao;
import com.palestra.notaria.dao.impl.PresupuestoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Presupuesto;

public class PresupuestoBoImpl extends GenericBoImpl<Presupuesto> implements PresupuestoBo{
	
	private PresupuestoDao presupuestoDao;
	
	public PresupuestoBoImpl() {
		this.presupuestoDao = new PresupuestoDaoImpl();
		super.dao = this.presupuestoDao;
	}

	@Override
	public List<Presupuesto> buscarPresupuestos(String idexpediente,
			String idacto) throws NotariaException{
		return this.presupuestoDao.buscarPresupuestos(idexpediente, idacto);
	}

	@Override
	public Presupuesto buscarPorIdCompleto(String idpresupuesto) throws NotariaException{
		return this.presupuestoDao.buscarPorIdCompleto(idpresupuesto);
	}

	@Override
	public boolean elimiarPresupuestosPorActo(String idacto, String idusuario) throws NotariaException{
		return this.presupuestoDao.elimiarPresupuestosPorActo(idacto, idusuario);
	}

}
