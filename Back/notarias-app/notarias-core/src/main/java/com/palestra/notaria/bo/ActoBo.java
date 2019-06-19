package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ProcessActo;

import pojos.pojos.BonitaCommonBean;
import pojos.pojos.TareaBean;

public interface ActoBo extends GenericBo<Acto>{
	
	List<Acto> filterActoByIdExpediente(String idExpediente)throws NotariaException;
	
	String getExpedienteIdByActoId(String idActo)throws NotariaException;
	
	Acto buscarPorIdCompleto(String id)throws NotariaException;

	public Acto desactivarActo(String idacto)throws NotariaException;

	Boolean validaDimAnexo5(String idacto) throws NotariaException;

	ProcessActo saveBonitaProcess(BonitaCommonBean bonitaBean) throws NotariaException;

	void saveBonitaTask(TareaBean tareaBean) throws NotariaException;
}
