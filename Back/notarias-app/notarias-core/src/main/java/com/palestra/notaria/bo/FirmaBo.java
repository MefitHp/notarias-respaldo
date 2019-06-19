package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Firma;

public interface FirmaBo extends GenericBo<Firma>{

	public void borrar(Firma firma) throws NotariaException;

	Firma obtenerFirmaPorCompareciente(Compareciente compareciente) throws NotariaException;

	Firma save(Firma firma) throws NotariaException;

	public boolean checkcompletas(String idescritura)throws NotariaException;
	
	

}
