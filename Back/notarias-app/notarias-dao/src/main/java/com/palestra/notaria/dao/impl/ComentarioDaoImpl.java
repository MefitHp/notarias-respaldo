package com.palestra.notaria.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.ComentarioDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.VariableGrupo;

public class ComentarioDaoImpl extends GenericDaoImpl<Comentario, Integer> implements ComentarioDao {

	public ComentarioDaoImpl(){
		super(Comentario.class);
	}

	@Override
	public List<Comentario> buscarPorObjeto(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();

		try {
			Query query = em
					.createQuery("SELECT com FROM Comentario com WHERE com.idobjeto =:id ORDER BY com.tmstmp DESC");
			query.setParameter("id", id);

			List<Comentario> resultados = query.getResultList();
			if (resultados.size() == 0) {
				return null;
			}

			return resultados;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}
	
	@Override
	public List<Comentario> getByDatesAndObject(String id, Date inicio, Date fin) throws NotariaException{
		EntityManager em = factory.createEntityManager();

		try {
			Query query = em
					.createQuery("SELECT com FROM Comentario com WHERE com.idobjeto = :id AND "+
							"com.tmstmp BETWEEN :inicio AND :fin ORDER BY com.tmstmp DESC");
			query.setParameter("id", id);
			query.setParameter("inicio", inicio);
			query.setParameter("fin", fin);
			List<Comentario> resultados = query.getResultList();
			if(resultados.size() == 0){
				return null;
			}
			
			return resultados;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	public static void main(String[]args){
		ComentarioDaoImpl coment = new ComentarioDaoImpl();
		try {
		Date inicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-09-24 17:36:10");
		Date fin;
		
			fin = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2018-09-24 17:44:12");
			List<Comentario> coments = coment.getByDatesAndObject("6327c45652638f5822133b9b4d39f53d", inicio, fin);
			
			for(Comentario comentario:coments){
				System.out.println(comentario.getDstexto());
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
}
