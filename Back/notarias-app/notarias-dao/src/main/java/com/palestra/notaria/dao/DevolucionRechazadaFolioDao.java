package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DevolucionRechazadaFolio;

public interface DevolucionRechazadaFolioDao extends
		GenericDao<DevolucionRechazadaFolio, Integer> {

	public List<DevolucionRechazadaFolio> listarRechazados() throws NotariaException;

}
