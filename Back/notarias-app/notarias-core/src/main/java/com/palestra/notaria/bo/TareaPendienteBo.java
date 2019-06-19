package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TareaPendiente;
import com.palestra.notaria.modelo.Usuario;

public interface TareaPendienteBo extends GenericBo<TareaPendiente>{
	
	public List<TareaPendiente> obtenerListaCompleta(Usuario usuario)throws NotariaException;
	
	public TareaPendiente buscarPorIdCompleto(TareaPendiente tareaPendiente) throws NotariaException;

}
