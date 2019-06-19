package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;

public interface DocumentoNotarialMasterBo extends
		GenericBo<DocumentoNotarialMaster> {

	DocumentoNotarialMaster findByEscrituraId(String id)throws NotariaException;
	
	Boolean existeMasterDeEscritura(String id) throws NotariaException;
	
}
