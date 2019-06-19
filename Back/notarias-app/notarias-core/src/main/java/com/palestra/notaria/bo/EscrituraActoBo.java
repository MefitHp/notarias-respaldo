package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;

public interface EscrituraActoBo extends GenericBo<EscrituraActo>{
	
	public List<EscrituraActo> findByEscrituraId(String id)throws NotariaException;
	
	public List<DatoActoMultiSelect> obtenActoNombrePorEscrituraId(String id) throws NotariaException;
	
	public String obtenIdEscrituraPorActoId(String id)throws NotariaException;
	
	public List<EscrituraActo> buscarPorEscrituraIdCompleto(EscrituraActo escrituraActo)throws NotariaException;
	
	public List<DatoActoMultiSelect> buscarActosDisponibleParaEscritura(String idEscritura, String idExpediente)throws NotariaException;
	
	public List<DatoActoMultiSelect> buscarActosDisponibles(String idexpediente)throws NotariaException;
	
	public Boolean isValidAddActos(String idActos)throws NotariaException;
	
	public int deleteByEscrituraId(String idEscritura)throws NotariaException;

	public String obtenNotarioDatoByEscrituraId(String id)throws NotariaException;

	public String validaCertificado(Escritura escritura)throws NotariaException;
}
