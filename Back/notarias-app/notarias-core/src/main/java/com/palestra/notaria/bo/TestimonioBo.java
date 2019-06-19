package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoTestimonio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Testimonio;

public interface TestimonioBo extends GenericBo<Testimonio>{
	
public boolean registrarTestimonio(Testimonio test, String idexpediente, String idusuario, String idsesion)throws NotariaException;
	
	public List<Testimonio> obtenerTestimoniosPorExp(String idexpediente)throws NotariaException;
	
	public DatoTestimonio obtenerPorIdCompleto(String idtestimonio)throws NotariaException;
	
	public boolean elimiarTestimonio(String idtestimonio, String idusuario)throws NotariaException;
	
	public boolean aprobarEtapa(String idreletapatesti, String idusuario) throws NotariaException;

}
