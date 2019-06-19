package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.dao.TramiteDao;
import com.palestra.notaria.dao.impl.TramiteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;

public class TramiteBoImpl extends GenericBoImpl<Tramite> implements TramiteBo{

	private TramiteDao tramiteDao;
//	private PersonaDao personaDao;
	
	public TramiteBoImpl() {
		this.tramiteDao = new TramiteDaoImpl();
		super.dao = this.tramiteDao;
	}

	@Override
	public Tramite buscarPorIdCompleto(Tramite tramite) throws NotariaException {
//		this.personaDao = new PersonaDaoImpl();
//		Persona cliente = new Persona();
		Tramite t = this.tramiteDao.buscarPorIdCompleto(tramite);
//		cliente.setIdpersona(t.getCliente().getIdpersona());
//		cliente = this.personaDao.buscarPorIdCompleto(cliente);
//		t.setCliente(cliente);
		return t;
	}

	

	
	@Override
	public List<Tramite> buscarTramitesPorCliente(String idCliente) throws NotariaException {
		return this.tramiteDao.buscarTramitesPorCliente(idCliente);
	}

	@Override
	public Expediente buscarIdExpediente(String idtramite) throws NotariaException {
		return this.tramiteDao.buscarIdExpediente(idtramite);
	}
	
	@Override
	public List<Tramite> findAll() throws NotariaException {
		return this.tramiteDao.findAll();
	}

	@Override
	public List<Tramite> findByAbogado(String idusuario)throws NotariaException {
		return this.tramiteDao.findByAbogado(idusuario);
	}

	@Override
	public Tramite buscarPorExpediente(Expediente expediente)
			throws NotariaException {
		try{
			Tramite tramite = this.tramiteDao.findByExpediente(expediente);
			if(tramite!=null){
				return tramite;
			}else{
				throw new NotariaException(String.format("El tramite solicitado no se encontro [param expediente: %s]",expediente.getIdexpediente()));
			}
		}catch(NullPointerException e){
			throw new NotariaException("No se localizo un parametro que se esperaba para ejecutar la funcion. Falta expediente [NULL]");
		}
	}

	@Override
	public Long buscarPosicionTramitePorAbogado(String idusuario)
			throws NotariaException {
		// TODO Auto-generated method stub
		return this.tramiteDao.buscarPosicionTramitePorAbogado(idusuario);
	}
	
}
