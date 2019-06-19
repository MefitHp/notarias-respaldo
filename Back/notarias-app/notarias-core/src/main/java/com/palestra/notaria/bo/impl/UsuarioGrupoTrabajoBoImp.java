package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.GrupoTrabajoBo;
import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.dao.UsuarioGrupoTrabajoDao;
import com.palestra.notaria.dao.impl.UsuarioGrupoTrabajoDaoImp;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class UsuarioGrupoTrabajoBoImp extends GenericBoImpl<UsuarioGrupoTrabajo> implements UsuarioGrupoTrabajoBo {

	private UsuarioGrupoTrabajoDao usuarioGrupoTrabajoDao;

	public UsuarioGrupoTrabajoBoImp() {
		this.usuarioGrupoTrabajoDao = new UsuarioGrupoTrabajoDaoImp();
		super.dao = this.usuarioGrupoTrabajoDao;
	}

	@Override
	public boolean borrarUsuarioGrupo(String idUsuarioGrupoTrabajo)throws NotariaException {
		return this.usuarioGrupoTrabajoDao.borrarUsuarioGrupo(idUsuarioGrupoTrabajo);
	}
	
	
	@Override
	public List<UsuarioGrupoTrabajo> findAll() throws NotariaException{
		return this.usuarioGrupoTrabajoDao.findAll();
		
	}


	@Override
	public List<UsuarioGrupoTrabajo> buscarXgrupo(String idgrupo)
			throws NotariaException {
		
		return this.usuarioGrupoTrabajoDao.buscarXgrupo(idgrupo);
	}
	
	@Override
	public UsuarioGrupoTrabajo buscarXusuario(String idusuario)throws NotariaException{
		return this.usuarioGrupoTrabajoDao.buscarXusuario(idusuario);
	}
	
	
	@Override
	public List<UsuarioGrupoTrabajo> buscarXresponsable(Usuario responsable)
			throws NotariaException {
		
		GrupoTrabajoBo gpotrabajoBo = new GrupoTrabajoBoImp();
		List<GrupoTrabajo> gpotrabajo = gpotrabajoBo.buscarXresponsable(responsable);
		List<UsuarioGrupoTrabajo> usrgpoReturn = new ArrayList<UsuarioGrupoTrabajo>();
		if(gpotrabajo.size()>0){
			for(GrupoTrabajo gpo: gpotrabajo){
				List <UsuarioGrupoTrabajo> usrgpolist = buscarXgrupo(gpo.getIdgrupotrabajo());
				if(usrgpolist.size()>0){
					for(UsuarioGrupoTrabajo usrgpo:usrgpolist){
						usrgpoReturn.add(usrgpo);
					}
				}
			}
		}
		
		return usrgpoReturn;
	} 

}
