package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.PDNToken;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;

public interface PDNTokenDao extends GenericDao<PDNToken, Integer> {

	List<PDNToken> tokens(PlantillaDocumentoNotarial plantilla)
			throws NotariaException;

}
