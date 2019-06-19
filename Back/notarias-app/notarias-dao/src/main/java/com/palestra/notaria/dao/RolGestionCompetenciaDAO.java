package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.RolGestionCompetencia;

public interface RolGestionCompetenciaDAO extends
		GenericDao<RolGestionCompetencia, String> {

	RolGestionCompetencia agregar(RolGestionCompetencia competencia) throws NotariaException;
	RolGestionCompetencia actualizarModo(String identificador, int modo) throws NotariaException;
	void quitarCompetencia(String identificador) throws NotariaException;
	List<RolGestionCompetencia> competencias(String idrol) throws NotariaException;
	int compentenciaPantalla(String idrol, String pantalla) throws NotariaException;
	
}
