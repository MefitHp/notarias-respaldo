package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public interface DocumentoAnexoDao extends GenericDao<DocumentoAnexo, Integer>{
	
	List<DocumentoNotarialMaster> obtenMasterAgendados(CalendarioCita cc) throws NotariaException;
	
	List<DocumentoNotarialMaster> obtenMasterDisponibles(String id) throws NotariaException;
}
