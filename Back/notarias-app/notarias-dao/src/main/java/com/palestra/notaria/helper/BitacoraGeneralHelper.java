package com.palestra.notaria.helper;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.dao.BitacoraGeneralDao;
import com.palestra.notaria.dao.impl.BitacoraGeneralDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.util.GeneradorId;

public class BitacoraGeneralHelper {

	private BitacoraGeneralDao bitacoraGeneralDao;

	public BitacoraGeneralHelper() {
		this.bitacoraGeneralDao = new BitacoraGeneralDaoImpl();
	}
	
	public BitacoraGeneral crearBitacora(String idUsuario, String idExpediente,
			String idTramite, String entidad, String operacion,
			String descripcion){
		
		Date fecha = new Date();
		BitacoraGeneral bg = new BitacoraGeneral();
		Expediente exp = null;
		Tramite tram = null;
		Usuario usuario = new Usuario();
		usuario.setIdusuario(idUsuario);
		if (idExpediente != null && !idExpediente.isEmpty()) {
			exp = new Expediente();
			exp.setIdexpediente(idExpediente);
		}

		if (idTramite != null && !idTramite.isEmpty()) {
			tram = new Tramite();
			tram.setIdtramite(idTramite);
		}
		bg.setDsentidad(entidad);
		bg.setDsoperacion(operacion);
		bg.setFecha(new Timestamp(fecha.getTime()));
		bg.setUsuario(usuario);
		bg.setDsdescripcion(descripcion);
		bg.setTramite(tram);
		bg.setExpediente(exp);
		bg.setIdbitgeneral(GeneradorId.generaId(bg));
		return bg;
	}

	public boolean registrarEnBitacora(String idUsuario, String idExpediente,
			String idTramite, String entidad, String operacion,
			String descripcion) {
		boolean b = false;
		Date fecha = new Date();
		BitacoraGeneral bg = new BitacoraGeneral();
		Expediente exp = null;
		Tramite tram = null;
		Usuario usuario = new Usuario();
		usuario.setIdusuario(idUsuario);
		if (idExpediente != null && !idExpediente.isEmpty()) {
			exp = new Expediente();
			exp.setIdexpediente(idExpediente);
		}

		if (idTramite != null && !idTramite.isEmpty()) {
			tram = new Tramite();
			tram.setIdtramite(idTramite);
		}
		bg.setDsentidad(entidad);
		bg.setDsoperacion(operacion);
		bg.setFecha(new Timestamp(fecha.getTime()));
		bg.setUsuario(usuario);
		bg.setDsdescripcion(descripcion);
		bg.setTramite(tram);
		bg.setExpediente(exp);
		bg.setIdbitgeneral(GeneradorId.generaId(bg));

		try {
			bg = this.bitacoraGeneralDao.save(bg);
			if (bg != null)
				b = true;
		} catch (NotariaException e) {
			e.printStackTrace();
			return false;
		}

		return b;
	}

}
