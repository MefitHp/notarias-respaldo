package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoInvitadoCita;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.InvitadoCalendarioCita;

public interface InvitadoCalendarioCitaBo extends
		GenericBo<InvitadoCalendarioCita> {

	List<DatoInvitadoCita> findInvitadosAgendados(CalendarioCita cc) throws NotariaException;

	List<DatoInvitadoCita> findInvitadosDisponibles(String idExpediente)throws NotariaException;

}
