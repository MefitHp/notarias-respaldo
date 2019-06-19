package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.CalendarioCitaPK;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;

public interface CalendarioCitaDao extends GenericDao<CalendarioCita, Integer> {

	CalendarioCita buscarCalendarioCitaCompleto(CalendarioCitaPK id) throws NotariaException;

	List<CalendarioCita> findCitas(CalendarioCita calendarioCita) throws NotariaException;
	
	List<CalendarioCita> findCitasByInvitado(String nombre) throws NotariaException;
	
	Boolean registrarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException;
	
	Boolean cambiarEstatusCitaPorId(CalendarioCitaPK id, String status) throws NotariaException;
	
	Boolean actualizarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo) throws NotariaException;
	
	Boolean reprogramarCita(CalendarioCita cc,
			List<InvitadoCalendarioCita> lsInvCita,
			List<DocumentoAnexo> lsDocAnexo, CalendarioCitaPK ccPK) throws NotariaException;
	
	Integer obtenerUltimaVersion(String idcita) throws NotariaException;
	

}
