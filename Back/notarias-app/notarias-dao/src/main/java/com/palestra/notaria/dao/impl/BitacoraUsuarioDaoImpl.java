package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.BitacoraUsuarioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;

public class BitacoraUsuarioDaoImpl extends GenericDaoImpl<BitacoraUsuario, Integer>
		implements BitacoraUsuarioDao{

	public BitacoraUsuarioDaoImpl() {
		super(BitacoraUsuario.class);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<BitacoraUsuario> listarByGrupo(String idgrupo) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<BitacoraUsuario> queryResult =  em.createNamedQuery("BitacoraUsuario.listarXgrupo",BitacoraUsuario.class);
		queryResult.setParameter("idgrupotrabajo",idgrupo);
		try {
			List<BitacoraUsuario> resultado = queryResult.getResultList();
			return resultado;
		} catch (NoResultException e) {
			return null;
		}finally {
			em.close();
		}
	}

	@Override
	public BitacoraUsuario buscarXtarea(String idtarea) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<BitacoraUsuario> queryResult =  em.createNamedQuery("BitacoraUsuario.buscarXtarea",BitacoraUsuario.class);
		queryResult.setParameter("idtarea",idtarea);
		try {
			List<BitacoraUsuario> resultado = queryResult.getResultList();
			if(resultado.size()>0){
				return resultado.get(0);
			}else{
				return null;
			}
		} catch (NoResultException e) {
			return null;
		}finally {
			em.close();
		}
	}

	

	
	
	

}
