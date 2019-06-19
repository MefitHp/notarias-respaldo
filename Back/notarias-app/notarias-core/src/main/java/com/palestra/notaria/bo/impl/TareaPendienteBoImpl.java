package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.TareaPendienteBo;
import com.palestra.notaria.dao.TareaPendienteDao;
import com.palestra.notaria.dao.impl.TareaPendienteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.modelo.Usuario;

public class TareaPendienteBoImpl extends GenericBoImpl<TareaPendiente> implements TareaPendienteBo{

	private TareaPendienteDao tareaPendienteDao;
//	private TramiteBo tramiteBo = new TramiteBoImpl();
//	private ExpedienteBo expedienteBo = new ExpedienteBoImpl();
	
	public TareaPendienteBoImpl() {
		this.tareaPendienteDao = new TareaPendienteDaoImpl();
		super.dao = this.tareaPendienteDao;
	}

	@Override
	public List<TareaPendiente> obtenerListaCompleta(Usuario usuario)throws NotariaException {
		return this.tareaPendienteDao.obtenerListaCompleta(usuario);
	}

	@Override
	public TareaPendiente buscarPorIdCompleto(TareaPendiente tareaPendiente) throws NotariaException{
		
		TareaPendiente tp = this.tareaPendienteDao.buscarPorIdCompleto(tareaPendiente);
		
		return tp;
	}
	
	

}
