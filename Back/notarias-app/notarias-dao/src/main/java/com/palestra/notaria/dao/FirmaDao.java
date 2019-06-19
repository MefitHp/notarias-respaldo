package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Firma;

public interface FirmaDao extends GenericDao<Firma, Integer>{
	
	public void borrar(Firma firma) throws NotariaException;

	public Firma obtenerFirmaPorCompareciente(Compareciente compareciente)throws NotariaException;

	public Firma save(Firma firma)throws NotariaException;

}
