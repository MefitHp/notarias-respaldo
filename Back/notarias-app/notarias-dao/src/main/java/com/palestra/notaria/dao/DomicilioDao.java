package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Domicilio;

public interface DomicilioDao extends GenericDao<Domicilio, Integer> {

	List<Domicilio> listarDomiciliosDeActo(String idacto)throws NotariaException;

}
