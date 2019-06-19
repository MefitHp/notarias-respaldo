package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialPK;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialSubOperacion;
import com.palestra.notaria.modelo.Suboperacion;

public interface PlantillaDocumentoNotarialDao extends GenericDao<PlantillaDocumentoNotarial, Integer> {
	
	PlantillaDocumentoNotarial getPublishBySubOperacionId(String id) throws NotariaException;

	PlantillaDocumentoNotarial getPublishBySubOperacionId(String id, ElementoCatalogo locacion) throws NotariaException;
	
	public PlantillaDocumentoNotarial getLastVersion(String idplantilla) throws NotariaException;

	public List<PlantillaDocumentoNotarial> getNoPublicados() throws NotariaException;

	public List<PlantillaDocumentoNotarial> getPublicados() throws NotariaException;

	public PlantillaDocumentoNotarial findById(PlantillaDocumentoNotarialPK id) throws NotariaException;
	
	public Integer findMaxVersion(String iddocnot) throws NotariaException;
	
	public PlantillaDocumentoNotarial findDocumentoPublicado(String iddocnot) throws NotariaException;
	
	public PlantillaDocumentoNotarial buscarPorIdCompleto(String iddocnot,Integer inversion) throws NotariaException;

	PlantillaDocumentoNotarial buscarPorSuboperacionLocacion(List<PlantillaDocumentoNotarialSubOperacion> operaciones, ElementoCatalogo locacion)throws NotariaException;
	
}
