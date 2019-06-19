package com.palestra.notaria.bo;

import java.util.List;
import java.util.Stack;

import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expresion;

public interface ExpresionBo extends GenericBo<Expresion> {

	public List<Expresion> listarPorVariable(String idvariable)throws NotariaException;

	public List<Expresion> getByComponente(String idcomponente)throws NotariaException;

	public Expresion getExpresionByIdComp(String idcomp) throws NotariaException;

	public List<CodigoError> validaFunciones(String expresion);

	public List<CodigoError> validaComponentes(String expresion);

	public List<CodigoError> existenGrupos(String expresion);

	public List<CodigoError> existenVariables(String expresion);

	public List<CodigoError> isSimetrica(String expresion);

	public void cerrarElemento(Stack<Character> stack, Character currentElement,
			List<CodigoError> errores);

}
