package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.PDNBloqueTexto;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;

public interface PDNBloqueTextoDao extends GenericDao<PDNBloqueTexto, Integer> {

	List<PDNBloqueTexto> texto(PlantillaDocumentoNotarial plantilla)
			throws NotariaException;
	
}
