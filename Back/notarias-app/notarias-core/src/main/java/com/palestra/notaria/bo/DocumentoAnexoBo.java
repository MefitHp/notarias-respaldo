package com.palestra.notaria.bo;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CalendarioCita;
import com.palestra.notaria.modelo.DocumentoAnexo;
import com.palestra.notaria.modelo.DocumentoNotarialMaster;


public interface DocumentoAnexoBo extends GenericBo<DocumentoAnexo>{
	
	List<DocumentoNotarialMaster> obtenMasterAgendados(CalendarioCita cc) throws NotariaException;
	
	List<DocumentoNotarialMaster> obtenMasterDisponibles(String id)throws NotariaException;

}
