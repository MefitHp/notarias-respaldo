package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraVersionesEscritura;

public interface BitacoraVersionesEscrituraDao extends
		GenericDao<BitacoraVersionesEscritura, Integer> {
	
	public List<BitacoraVersionesEscritura> findByEscrituraId(String idEscritura) throws NotariaException;
	
	public int deleteByEscrituraId(String idEscritura) throws NotariaException;
	
}
