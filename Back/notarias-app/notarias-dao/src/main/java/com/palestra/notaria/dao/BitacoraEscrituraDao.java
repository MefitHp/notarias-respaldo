package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;

public interface BitacoraEscrituraDao extends GenericDao<BitacoraEscritura, Integer>{

	public BitacoraEscritura obtenerUltimaBitacoraXEscritura(Escritura escritura) throws NotariaException;
	
}
