package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public interface DocumentoNotarialMasterDao extends
		GenericDao<DocumentoNotarialMaster, Integer> {

	DocumentoNotarialMaster findByEscrituraId(String id) throws NotariaException;
	
	Boolean existeMasterDeEscritura(String id) throws NotariaException;


}
