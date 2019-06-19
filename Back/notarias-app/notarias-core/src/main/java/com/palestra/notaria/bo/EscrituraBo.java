package com.palestra.notaria.bo;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.Usuario;

import pojos.pojos.BonitaCommonBean;

public interface EscrituraBo extends GenericBo<Escritura>{
	
	List<Escritura> findByExpedienteId(String id)throws NotariaException;
	
	String obtenNumEscritura(String id)throws NotariaException;
	
	Escritura buscarPorIdCompleto(Escritura escritura)throws NotariaException;
	
	Boolean registrarEscritura(Escritura escritura, List<EscrituraActo> actosDeEscritura)throws NotariaException;
	
	Boolean actualizarActosNotario(Escritura escritura, List<EscrituraActo> actosDeEscritura)throws NotariaException;
	
	Boolean actualizarPropsEscritura(Escritura escritura, String idActo, Usuario usuario)throws NotariaException;
	
	String getExpedienteIdByEscrituraId(String id)throws NotariaException;
	
	Boolean setFirmaDittoByEscrituraId(String id, Boolean isfirmaditto)throws NotariaException;
	
	Boolean eliminarEscritura(String id)throws NotariaException;
	
	Boolean tieneNumEscritura(String idEscritura)throws NotariaException;
	
	Boolean switchNotario(Escritura escritura)throws NotariaException;
	
	String obtenerIdNotarioPorEscrituraId(String idEscritura) throws NotariaException;

	Escritura getByNumeroEscritura(String numesc) throws NotariaException;

	void nopaso(Escritura escritura, Usuario usr) throws NotariaException;


	void verificaDimAnexo5(ArrayList<DatoActoMultiSelect> actos) throws NotariaException;

	List<Escritura> getXnumLibroStatus(Long numerolibroInicial, Long numerolibroFinal, Boolean status)
			throws NotariaException;

	void saveNumEscrituraBonita(BonitaCommonBean bonitaBean);
		
}
