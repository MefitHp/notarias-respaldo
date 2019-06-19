package com.palestra.notaria.util;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.palestra.notaria.dao.ManejoSesionDao;
import com.palestra.notaria.dao.impl.ManejoSesionDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;

/*
 * @autor: VÍCTOR ESPINOSA
 * @descripcion: CLASE DEMONIO ENCARGADA DE REALIZAR UNA PETICIÓN CADA 6 HORAS, PARA EVITAR QUE SE CIERRE LA CONEXIÓN.
 * 
 * 
 * */




public class JonasBot extends TimerTask{

	//protected static EntityManagerFactory factory = Persistence.createEntityManagerFactory("persistenceUnit");

	Logger logger = Logger.getLogger(JonasBot.class);
	/*
	 * VICTOR ESPINOSA
	 * 21600000 EQUIVALE A 6 HORAS, 
	 */
	private int tiempo = 21600000;
	
	
	
	public  JonasBot() {
		Timer timer = new Timer();
		timer.schedule(this,0,tiempo);
	}
	

	
	@Override
	public void run() {
			ManejoSesionDao msbo = new ManejoSesionDaoImpl();
			int sesionesActuales;
			try {
				sesionesActuales = msbo.totalSesiones();
				logger.info("SESIONES ACTUALES EN LA BASE DE DATOS:"+sesionesActuales);
			} catch (NotariaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
				
	}
	
	
}
