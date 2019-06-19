package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraVersionesEscritura;


public interface BitacoraVersionesEscrituraBo extends GenericBo<BitacoraVersionesEscritura>{
	
	public List<BitacoraVersionesEscritura> findByEscrituraId(String escrituraId) throws NotariaException;
	
	public int deleteByEscrituraId(String idEscritura) throws NotariaException;
	
}
