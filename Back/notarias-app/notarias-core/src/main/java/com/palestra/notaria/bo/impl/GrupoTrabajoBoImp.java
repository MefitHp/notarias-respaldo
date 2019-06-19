package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.GrupoTrabajoBo;
import com.palestra.notaria.dao.GrupoTrabajoDao;
import com.palestra.notaria.dao.impl.GrupoTrabajoDaoImp;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;


public class GrupoTrabajoBoImp extends GenericBoImpl<GrupoTrabajo> implements GrupoTrabajoBo {

	private GrupoTrabajoDao grupoTrabajoDao;

	public GrupoTrabajoBoImp() {
		this.grupoTrabajoDao = new GrupoTrabajoDaoImp();
		super.dao = this.grupoTrabajoDao;
	}
	
	@Override
	public boolean delete(String idgrupotrabajo)throws NotariaException{
	
			try {
				if(this.grupoTrabajoDao.borraGrupo(idgrupotrabajo)){
					return true;
				}
			} catch (NotariaException e) {
				throw new NotariaException("No se pudo eliminar el objeto debido a que hay usuarios asociados a este grupo");
			}
			return false;
	}
	
	@Override
	public List<GrupoTrabajo> buscarXresponsable(Usuario responsable) throws NotariaException{
		return this.grupoTrabajoDao.buscarXresponsable(responsable); 
	}
	
	@Override
	public Usuario obtenerResponsable(Usuario miembro)throws NotariaException{
		return this.grupoTrabajoDao.obtenerResponsable(miembro);
	}
}
