package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.BitacoraUsuarioBo;
import com.palestra.notaria.bo.GrupoTrabajoBo;
import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.dao.BitacoraUsuarioDao;
import com.palestra.notaria.dao.impl.BitacoraUsuarioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;

public class BitacoraUsuarioBoImpl extends GenericBoImpl<BitacoraUsuario> implements BitacoraUsuarioBo {

	private BitacoraUsuarioDao bitUsuDao;

	public BitacoraUsuarioBoImpl()  {
		bitUsuDao = new BitacoraUsuarioDaoImpl();
		this.dao = bitUsuDao;
	}
	
	@Override
	public BitacoraUsuario buscarXTarea(String idtarea)throws NotariaException{
		return getBitUsuDao().buscarXtarea(idtarea);
	}
	
	@Override
	public List<BitacoraUsuario> listarByGrupo(Usuario usu)throws NotariaException{
		StringBuilder idgroup = getGrupo(usu);
		return getBitUsuDao().listarByGrupo(idgroup.toString());
	}
	
	
	public BitacoraUsuarioDao getBitUsuDao() {
		return bitUsuDao;
	}
	
	private StringBuilder getGrupo(Usuario usu) throws NotariaException{
		StringBuilder idgroup = new StringBuilder();
		//Victor; verifico si es abogado ya que es responsable y no esta asociado a ningun usuariogrupo 

		if(usu.getRol().getDsprefijo().equals("abog")){
			GrupoTrabajoBo gpBo = new GrupoTrabajoBoImp();
			List<GrupoTrabajo> usugp = gpBo.buscarXresponsable(usu);
			idgroup.append(usugp.get(0).getIdgrupotrabajo());
		}else{
			UsuarioGrupoTrabajoBo usugpbo = new UsuarioGrupoTrabajoBoImp();
			UsuarioGrupoTrabajo usugp = usugpbo.buscarXusuario(usu.getIdusuario());
			if(usugp!=null && usugp.getGrupoTrabajo()!=null){
				idgroup.append(usugp.getGrupoTrabajo().getIdgrupotrabajo());
			}
		}
		return idgroup; 
	}
	
	
}
