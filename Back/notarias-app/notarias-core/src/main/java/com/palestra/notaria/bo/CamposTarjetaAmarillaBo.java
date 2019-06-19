package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;

public interface CamposTarjetaAmarillaBo extends GenericBo<CamposTarjetaAmarilla> {

	public List<CamposTarjetaAmarilla> getCamposPorOperacion(String idacto) throws NotariaException;

}
