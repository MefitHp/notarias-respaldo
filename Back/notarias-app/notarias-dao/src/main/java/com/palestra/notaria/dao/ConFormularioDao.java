package com.palestra.notaria.dao;

import java.util.List;
import java.util.Map;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ElementoCatalogo;

public interface ConFormularioDao extends GenericDao<ConFormulario, Integer>{

	ConFormulario buscarFormularioCompleto(ConFormularioPK id) throws NotariaException;
	
	Integer buscarUltimaVersion(String idconformulario) throws NotariaException;
	
	boolean publicarFormulario(String idformulario, Integer version) throws NotariaException;
	
	boolean elimninarFormulario(String idformulario, Integer version) throws NotariaException;
	
	List<ActoFormulario> formulariosPorExpediente(String idexpediente, String idacto) throws NotariaException;
	
	List<String> obtieneListado(String consulta) throws NotariaException;

	public List<ConFormulario> listarPorActo(String idacto) throws NotariaException;

	List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String tipoForm) throws NotariaException;

	public ConFormulario findByName(String nombrecorto)throws NotariaException;

	Map<String, String> recuperaListaPredeterminada(String hql, String acto)
			throws NotariaException;

	ActoFormulario findActoFormularioById(String identificador)
			throws NotariaException;

	int removeAllActoFormulario(ConFormularioPK idConFormulario) throws NotariaException;

	int removeActoFormularioById(String identificador) throws NotariaException;

	List<ActoFormulario> findAllActoFormulario(ConFormularioPK idConFormulario)
			throws NotariaException;
	
	ActoFormulario saveActoFormulario(ActoFormulario actoFormulario) throws NotariaException;

	List<ConFormulario> localizaPorNombreCorto(String nombreCorto)
			throws NotariaException;

	List<ConFormulario> localizaDuplicado(String nombreCorto,
			ElementoCatalogo locacion) throws NotariaException;
	
}
