package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;

public interface ControlFoliosDao extends GenericDao<ControlFolios, Integer> {

	public ControlFolios getUltimoFolio() throws NotariaException;
	
	
}
