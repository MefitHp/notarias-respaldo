package com.palestra.notaria.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import com.palestra.notaria.dao.BonitaBPMDao;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FechasBPM;

public class BonitaBPMDaoImpl extends GenericDaoImpl implements BonitaBPMDao {


	public BonitaBPMDaoImpl(Class clase) {
		super(clase);
	}

	@Override
	public List<Date> obtenerFechasInhabiles(Date cal) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Date> fechas = new ArrayList<>();
		try{
			TypedQuery<FechasBPM> query = em.createQuery("SELECT a FROM FechasBPM a WHERE a.fecha >= :fecha", FechasBPM.class);
			query.setParameter("fecha", cal);
			
			for(FechasBPM fecha:query.getResultList()){
				fechas.add(fecha.getFecha());
			}
		}catch(PersistenceException e){
			e.printStackTrace();
			throw new NotariaException(e.getMessage());
		}
		return fechas;
	}

}
