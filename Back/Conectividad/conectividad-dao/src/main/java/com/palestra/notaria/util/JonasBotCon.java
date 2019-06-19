package com.palestra.notaria.util;
import java.util.Timer;
import java.util.TimerTask;
import com.connect.dao.ConsultaDao;
import com.connect.dao.exception.ConectividadDaoException;
import com.connect.dao.impl.ConsultaDaoImpl;


/*
 * @autor: VÍCTOR ESPINOSA
 * @descripcion: CLASE DEMONIO ENCARGADA DE REALIZAR UNA PETICIÓN CADA 6 HORAS, PARA EVITAR QUE SE CIERRE LA CONEXIÓN.
 * 
 * 
 * */



public class JonasBotCon extends TimerTask{
	/*
	 * VICTOR ESPINOSA
	 * 21600000 EQUIVALE A 6 HORAS, 
	 */
	private int tiempo = 21600000;
	
	
	
	public  JonasBotCon() {
		Timer timer = new Timer();
		timer.schedule(this,0,tiempo);
	}
	
	@Override
	public void run() {
			ConsultaDao msbo = new ConsultaDaoImpl();
			int consulta;
			try {
				consulta = msbo.totalPrestados();
				System.out.println("Consultas totales :"+consulta);
			} catch (ConectividadDaoException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
	}
	
	
}
