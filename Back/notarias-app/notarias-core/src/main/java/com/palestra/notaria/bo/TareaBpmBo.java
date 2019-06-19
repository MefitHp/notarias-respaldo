package com.palestra.notaria.bo;
import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.palestra.notaria.exceptions.NotariaException;

import pojos.pojos.Escritura;
import pojos.pojos.Tarea;

//import pojos.Escritura;
//import pojos.Tarea;

public interface TareaBpmBo {

	public List<Tarea> getTareasAsignadas() throws NotariaException;
	public void newFlowEscritura(Escritura es)throws NotariaException;
	public void executeTarea(Long idtask,Map<String, Serializable> inputs)throws NotariaException;

}
