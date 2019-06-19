package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.CamposTarjetaAmarilla;

public interface CamposTarjetaAmarillaDao extends GenericDao<CamposTarjetaAmarilla, Integer> {

	public List<CamposTarjetaAmarilla> getCamposPorOperacion(String idacto) throws NotariaException;

}
