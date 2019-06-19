package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.CodigoError;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarial;
import com.palestra.notaria.modelo.PlantillaDocumentoNotarialPK;
import com.palestra.notaria.modelo.Suboperacion;

public interface PlantillaDocumentoNotarialBo extends GenericBo<PlantillaDocumentoNotarial>{
	
	PlantillaDocumentoNotarial getPublishBySubOperacionId(String id)throws NotariaException;
	
	PlantillaDocumentoNotarial getPublishBySubOperacionId(String id, ElementoCatalogo locacion)throws NotariaException;

	public PlantillaDocumentoNotarial getLastVersion(String idplantilla) throws NotariaException;

	public List<PlantillaDocumentoNotarial> getNoPublicados() throws NotariaException;

	public List<PlantillaDocumentoNotarial> getPublicados() throws NotariaException;

	public PlantillaDocumentoNotarial findById(PlantillaDocumentoNotarialPK id) throws NotariaException;

	public Integer findMaxVersion(String iddocnot) throws NotariaException;

	public PlantillaDocumentoNotarial findDocumentoPublicado(String iddocnot) throws NotariaException;

	public PlantillaDocumentoNotarial buscarPorIdCompleto(String iddocnot,Integer inversion) throws NotariaException;

	public List<CodigoError> validarReferencias(String plantilla);

	public List<CodigoError> isSimetrica(String expresion);

	public List<CodigoError> validaVariables(String plantilla);

	PlantillaDocumentoNotarial obtienePublicadaPorSuboperacionLocacion(
			ElementoCatalogo localidad, List<Suboperacion> suboperaciones)
			throws NotariaException;
	
}
