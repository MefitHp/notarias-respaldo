package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expresion;

public interface ExpresionDao extends GenericDao<Expresion, Integer> {

	public List<Expresion> listarPorVariable(String idvariable)throws NotariaException;

	public List<Expresion> getByComponente(String idcomponente)throws NotariaException;

	public Expresion getExpresionByIdComp(String idcomp) throws NotariaException;

}
