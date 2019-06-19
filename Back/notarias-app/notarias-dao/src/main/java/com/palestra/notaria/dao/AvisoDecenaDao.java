package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;

public interface AvisoDecenaDao  extends GenericDao<AvisoDecena, Integer>{

	AvisoDecena obtenerXnumeroLibro(Long numeroLibro)throws NotariaException;

	void cierraDecena(Long numeroLibro)throws NotariaException;

	List<AvisoDecena> findAll()throws NotariaException;
	
}
