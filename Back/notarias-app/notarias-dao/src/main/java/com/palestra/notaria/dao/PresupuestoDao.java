package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Presupuesto;

public interface PresupuestoDao extends GenericDao<Presupuesto, Integer> {

	public List<Presupuesto> buscarPresupuestos(String idexpediente, String idacto) throws NotariaException;
	
	public Presupuesto buscarPorIdCompleto(String idpresupuesto) throws NotariaException;
	
	public boolean elimiarPresupuestosPorActo(String idacto, String idusuario) throws NotariaException;
}
