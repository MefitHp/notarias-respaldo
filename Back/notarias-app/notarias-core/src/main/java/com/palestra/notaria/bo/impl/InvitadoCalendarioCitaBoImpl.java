package com.palestra.notaria.bo.impl;
import java.util.List;

import com.palestra.notaria.bo.InvitadoCalendarioCitaBo;
import com.palestra.notaria.dao.InvitadoCalendarioCitaDao;
import com.palestra.notaria.dao.impl.InvitadoCalendarioCitaDaoImpl;
import com.palestra.notaria.dato.DatoInvitadoCita;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;

public class InvitadoCalendarioCitaBoImpl extends
		GenericBoImpl<InvitadoCalendarioCita> implements
		InvitadoCalendarioCitaBo {

	private InvitadoCalendarioCitaDao invitadoCalendarioCitaDao;

	public InvitadoCalendarioCitaBoImpl() {
		this.invitadoCalendarioCitaDao = new InvitadoCalendarioCitaDaoImpl();
		super.dao = this.invitadoCalendarioCitaDao;
	}
	
	@Override
	public List<DatoInvitadoCita> findInvitadosAgendados(CalendarioCita cc)throws NotariaException {
		return this.invitadoCalendarioCitaDao.findInvitadosAgendados(cc);
	}
	
	@Override
	public List<DatoInvitadoCita> findInvitadosDisponibles(String idExpediente) throws NotariaException{
		return this.invitadoCalendarioCitaDao.findInvitadosDisponibles(idExpediente);
	}
}
