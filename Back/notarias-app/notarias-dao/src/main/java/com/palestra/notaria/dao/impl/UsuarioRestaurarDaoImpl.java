package com.palestra.notaria.dao.impl;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.UsuarioRestaurarDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;

public class UsuarioRestaurarDaoImpl extends GenericDaoImpl<UsuarioRestaurar, String> implements
		UsuarioRestaurarDao, Serializable {

	private static final long serialVersionUID = 1L;

	public UsuarioRestaurarDaoImpl() {
		super(UsuarioRestaurar.class);		
	}

	@Override
	public UsuarioRestaurar ultimaPeticionActiva(Usuario usuario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<UsuarioRestaurar> query = em.createNamedQuery("UsuarioRestaurar.encuentraPeticionActivaUsuario", UsuarioRestaurar.class);
			query.setParameter("usuario", usuario);
			List<UsuarioRestaurar> peticiones = query.getResultList();
			if(peticiones!=null && peticiones.size()>0){
				return peticiones.get(0);
			}else{
				throw new NotariaException("No se localizo una peticion vigente.");
			}
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
		
	}
	
}
