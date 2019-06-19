package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoBusquedaCompareciente;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.Escritura;

public interface ComparecienteBo extends GenericBo<Compareciente>{
	
	public List<Compareciente> listadoComparecientes(Compareciente compareciente,String... tipoComp)throws NotariaException;;
	
	public List<DatoBusquedaCompareciente> findByActoId(String id)throws NotariaException;;
	
	public Compareciente buscarPorIdCompleto(Compareciente compareciente)throws NotariaException;;
	
	Boolean registrarCompareciente(Compareciente compareciente, List<ComparecienteRepresentante> cr)throws NotariaException;;
	
	Boolean actualizarRepresentantes(Compareciente compareciente, Compareciente representante, List<ComparecienteRepresentante> cr) throws NotariaException;
	
	Boolean eliminarCompareciente(Compareciente compareciente)throws NotariaException;;
	
	Boolean actualizarCompareciente(Compareciente compareciente, List<ComparecienteRepresentante> cr)throws NotariaException;;
	
	List<Compareciente> disponiblesPorActoEscritura(String id)throws NotariaException;;
	
	Boolean actualizaRegistroRi(String idcompareciente) throws NotariaException;

	List<Compareciente> listadoComparecientesByActo(String acto,
			String tipoCompareciente)throws NotariaException;

	List<Compareciente> getByIdPersona(String idpersona) throws NotariaException;

	List<Compareciente> listadoComparecientesByActo(String acto)
			throws NotariaException;

	Escritura obtieneEscritura(String idacto ) throws NotariaException;

	boolean escNoPaso(Compareciente compareciente) throws NotariaException;

}
