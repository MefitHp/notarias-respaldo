package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Domicilio;

public interface DomicilioBo extends GenericBo<Domicilio> {

	public List<Domicilio> listarDomiciliosDeActo(String idacto) throws NotariaException;
}
