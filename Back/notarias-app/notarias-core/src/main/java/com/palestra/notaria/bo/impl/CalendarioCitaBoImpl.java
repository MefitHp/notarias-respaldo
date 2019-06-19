package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.CalendarioCitaBo;
import com.palestra.notaria.dao.CalendarioCitaDao;
import com.palestra.notaria.dao.impl.CalendarioCitaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.CalendarioCitaPK;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;

public class CalendarioCitaBoImpl extends GenericBoImpl<CalendarioCita> implements CalendarioCitaBo{
	
	private CalendarioCitaDao calendarioCitaDao;
	
	public CalendarioCitaBoImpl(){
		this.calendarioCitaDao = new CalendarioCitaDaoImpl();
		super.dao = this.calendarioCitaDao;
	}
	
	@Override
	public CalendarioCita buscarCalendarioCitaCompleto(CalendarioCitaPK id) throws NotariaException {
		return this.calendarioCitaDao.buscarCalendarioCitaCompleto(id);
	}
	
	@Override
	public List<CalendarioCita> findCitas(CalendarioCita calendarioCita) throws NotariaException {
		return this.calendarioCitaDao.findCitas(calendarioCita);
	}
	
	@Override
	public List<CalendarioCita> findCitasByInvitado(String nombre) throws NotariaException {
		return this.calendarioCitaDao.findCitasByInvitado(nombre);
	}
	
	@Override
	public Boolean registrarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException {
		return this.calendarioCitaDao.registrarCita(cc, lsInvCita, lsDocAnexo);
	}
	
	@Override
	public Boolean cambiarEstatusCitaPorId(CalendarioCitaPK id, String status) throws NotariaException {
		return this.calendarioCitaDao.cambiarEstatusCitaPorId(id, status);
	}
	
	@Override
	public Boolean actualizarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException {
		return this.calendarioCitaDao.actualizarCita(cc, lsInvCita, lsDocAnexo);
	}
	
	@Override
	public Boolean reprogramarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo, CalendarioCitaPK ccPK) throws NotariaException {
		return this.calendarioCitaDao.reprogramarCita(cc, lsInvCita, lsDocAnexo, ccPK);
	}
	
	@Override
	public Integer obtenerUltimaVersion(String idcita) throws NotariaException {
		return this.calendarioCitaDao.obtenerUltimaVersion(idcita);
	}

}
