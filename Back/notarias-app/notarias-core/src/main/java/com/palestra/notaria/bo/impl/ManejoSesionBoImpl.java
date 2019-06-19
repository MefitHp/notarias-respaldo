package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ManejoSesionBo;
import com.palestra.notaria.dao.ManejoSesionDao;
import com.palestra.notaria.dao.impl.ManejoSesionDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ManejoSesion;

public class ManejoSesionBoImpl extends GenericBoImpl<ManejoSesion> implements
		ManejoSesionBo {

	ManejoSesionDao manejoSesionDao;
	
	public ManejoSesionBoImpl(){
		this.manejoSesionDao = new ManejoSesionDaoImpl();
		super.dao = this.manejoSesionDao;
	}
	
	@Override
	public List<ManejoSesion> findSesionByUser(String idusuario)throws NotariaException{
		return manejoSesionDao.findSesionByUser(idusuario);
	}
	
	

	
	@Override
	public int totalSesiones()throws NotariaException{
		return manejoSesionDao.totalSesiones();
		
	}
	
	@Override
	public boolean borrar(ManejoSesion t) throws NotariaException {
		
		return manejoSesionDao.borrar(t);
		
	};
	
	@Override
	public ManejoSesion findBySesionAndUser(String idsesion, String... idusuario)throws NotariaException{
//		try{
			return manejoSesionDao.findBySesionAndUser(idsesion, idusuario);
//		}catch(Exception e){
//			e.printStackTrace();
//			return null;
//		}
	}
}
