package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Notaria;

public interface NotariaBo extends GenericBo<Notaria> {
	
	String obtenerNumNotariaByInicialesNotario(String inicialesNotario) throws NotariaException;

}
