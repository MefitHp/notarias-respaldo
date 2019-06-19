package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.RolGestionCompetencia;

public interface RolBo extends GenericBo<Rol> {

	List<RolGestionCompetencia> competencias(Rol rol) throws NotariaException;
	int registraCompetencias(List<RolGestionCompetencia> competencias) throws NotariaException;
	int actualizaModoCompetencia(String identificador, int modo) throws NotariaException;
	int validaCompetencia(Rol rol, String pantalla) throws NotariaException;
	Rol rolByPrefijo(String prefijo) throws NotariaException;
	
}
