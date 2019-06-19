package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Contacto;

public interface ContactoBo extends GenericBo<Contacto> {
	
	public Contacto buscarXpersona(String idpersona) throws NotariaException;

}
