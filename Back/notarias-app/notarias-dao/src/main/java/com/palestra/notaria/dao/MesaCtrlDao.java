package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.enums.EnumStatusPago;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.MesaCtrl;

//import alertas.AlertaDto;

public interface MesaCtrlDao extends GenericDao<MesaCtrl, Integer> {

	void borrar(MesaCtrl mesa)throws NotariaException;

	List<MesaCtrl> findByEstatusPago(EnumStatusPago enumPago)throws NotariaException;

	MesaCtrl findByActoDocumento(String actodocumento)throws NotariaException;

	MesaCtrl findNoPaso(Escritura esc) throws NotariaException;

	List<MesaCtrl> findByEscritura(Escritura escritura)throws NotariaException;

	//AlertaDto askAlerta(AlertaDto alertaDto, String url) throws NotariaException;

	
	
}
