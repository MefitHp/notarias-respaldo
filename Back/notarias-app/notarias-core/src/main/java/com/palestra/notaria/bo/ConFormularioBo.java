package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.ElementoCatalogo;

public interface ConFormularioBo extends GenericBo<ConFormulario>{

	ConFormulario buscarFormularioCompleto(ConFormularioPK id) throws NotariaException;
	/***
	 * buscaFormularioCompleto
	 * @param id identificador del formulario a buscar
	 * @param acto objeto acto para buscar los valores de las consultas predeterminadas.
	 * @return un objeto formulario completo.
	 * @throws NotariaException
	 */
	ConFormulario buscarFormularioCompleto(ConFormularioPK id, Acto acto) throws NotariaException;
	
	Integer buscarUltimaVersion(String idconformulario)throws NotariaException;
	
	boolean publicarFormulario(String idformulario, Integer version)throws NotariaException;
	
	boolean elimninarFormulario(String idformulario, Integer version)throws NotariaException;
	
	List<ActoFormulario> formulariosPorExpediente(String idexpediente, String idacto)throws NotariaException;

	List<ConFormulario> listarPorActo(String idacto) throws NotariaException;

	/***
	 * formulariosPorExpediente Método sobrecargado para obtener el listado de formularios asociados a un expediente o acto filtrando por el tipo de formulario solicitado desde la ventana
	 * @param idexpediente identificador del expediente
	 * @param idacto identificador del acto
	 * @param tipoform indicador de tipo de formulario, los valores permitidos son E cuando se requieren los asociados a las escrituras o D para los asociados a los documentos, en caso de que se mande vacio se enviaran todos. Si el valor es nulo se regresa un NotariasException 
	 * @return java.util.List<ActoFormulario> Listado de formularios asociados al expediente o acto. 
	 * @throws NotariaException
	 */
	List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String tipoform)throws NotariaException;
	
	/***
	 * formulariosPorExpediente Método sobrecargado para obtener el listado de formularios asociados a un expediente o acto filtrando por el tipo de formulario solicitado desde la ventana
	 * @param idexpediente identificador del expediente
	 * @param idacto identificador del acto
	 * @param tipoform indicador de tipo de formulario, los valores permitidos son E cuando se requieren los asociados a las escrituras o D para los asociados a los documentos, en caso de que se mande vacio se enviaran todos. Si el valor es nulo se regresa un NotariasException
	 * @param locacion Objeto ElementoCatalogo que contiene la locacion del formulario 
	 * @return java.util.List<ActoFormulario> Listado de formularios asociados al expediente o acto. 
	 * @throws NotariaException
	 */
	List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String tipoform, ElementoCatalogo locacion)throws NotariaException;
	
	List<ConFormulario> listarConFormulario() throws NotariaException;
	List<ConFormulario> listarConFormulario(String idSubOperacion) throws NotariaException;

	public ConFormulario findByName(String nombrecorto) throws NotariaException;
	String testActualizaFormulario(ConFormulario formulario)
			throws NotariaException;
	String actualizaFormulario(ConFormulario formulario)
			throws NotariaException;
	List<ConSubFormulario> listaConSubFormularios(
			ConFormularioPK idConFormulario) throws NotariaException;
}
