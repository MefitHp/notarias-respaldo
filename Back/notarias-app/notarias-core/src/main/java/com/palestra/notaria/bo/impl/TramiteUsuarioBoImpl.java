package com.palestra.notaria.bo.impl;

import com.palestra.notaria.util.GeneradorId;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.TramiteUsuarioBo;
import com.palestra.notaria.dao.TramiteUsuarioDao;
import com.palestra.notaria.dao.impl.TramiteUsuarioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.TramiteUsuario;
import com.palestra.notaria.modelo.Usuario;

public class TramiteUsuarioBoImpl implements TramiteUsuarioBo{


//	private PersonaDao personaDao;
	
	public TramiteUsuarioBoImpl(){}
	
	public ArrayList<Expediente> buscarExpedientesXTramiteUsuario(Usuario usuario) throws NotariaException {
		
		TramiteUsuarioDao tramUsuDao = new TramiteUsuarioDaoImpl();
		TramiteBo tramBo = new TramiteBoImpl();
		ArrayList<Expediente> expedientes = new ArrayList<Expediente>();
		
		try {
			ArrayList<Tramite> tramites = tramUsuDao.findByUsuario(usuario.getIdusuario());
			for(Tramite t:tramites){
				Expediente exp = tramBo.buscarIdExpediente(t.getIdtramite());
				if(exp!= null){
					//exp.setListaComentarios(null);
	//				exp.setTramite(null);
					exp.getTramite().getCliente().setNacionalidad(null);
					exp.getTramite().getCliente().setTipopersona(null);
					expedientes.add(exp);
				}
				
				
			}
						
			
		} catch (NotariaException e) {
			throw new NotariaException("Algo malo ocurrio al buscar el trámites del usuario",e);
		}
		
		return expedientes;
	}
	
	@Override
	public List<Usuario> buscarUsuariosXtramite(String idTramite) throws NotariaException{
		
		TramiteUsuarioDao tramUsuDao = new TramiteUsuarioDaoImpl();
		List<Usuario> usuarios = new ArrayList<Usuario>();
		try {
			usuarios = tramUsuDao.findUsuariosByTramite(idTramite);
		} catch (Exception e) {
			throw new NotariaException("Algo malo ocurrio al obtener los usuarios asociados al tramite",e);
		}
		
		return usuarios;
	}
	
	@Override
	public TramiteUsuario saveTramiteUsuario(Usuario u,Tramite t,String sesionActual)throws NotariaException{
		
		
		TramiteUsuarioDao tramUsuDao = new TramiteUsuarioDaoImpl();
		TramiteUsuario tramUsurio = new TramiteUsuario(u,t);
		
		try {
			tramUsurio.setIdtramiteusuario(GeneradorId.generaId(tramUsurio));
			tramUsurio.setIdsesion(sesionActual);
			tramUsurio = tramUsuDao.save(tramUsurio);
		} catch (NotariaException e) {
			throw new NotariaException("Algo malo ocurrio al guardar el trámite usuario",e);
		}
		
		return tramUsurio;
	}
	
	@Override
	public void deleteTramiteUsuario(Usuario u,Tramite t,String sesionActual)throws NotariaException{
		
		TramiteUsuarioDao tramUsuDao = new TramiteUsuarioDaoImpl();
		
		try {
			
			TramiteUsuario tramiteFound = tramUsuDao.findByUsuarioTramite(u.getIdusuario(),t.getIdtramite());
			if(tramiteFound != null){
				tramUsuDao.delete(tramiteFound);
			}
			
		} catch (NotariaException e) {
			throw new NotariaException("Algo malo ocurrio al guardar el trámite usuario",e);
		}
		
	}
	
}
