package com.palestra.notaria.dao;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Usuario;

public interface ActoDocumentoDao extends GenericDao<ActoDocumento, Integer>{

	public List<DatoActoDocumento> obtenerPrevios(String idexpediente, String idacto) throws NotariaException;
	
	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente, String idacto) throws NotariaException;
	
	public String buscarArchivoPorId(String iddocumento) throws NotariaException;
	
	public boolean actualizarRutaArchivo(String iddocumento, String ruta) throws NotariaException;
	
	public boolean actualizarRutaArchivoFormato(String iddocumento, String ruta) throws NotariaException;
	
	public boolean marcarDocumentoAprovado(String iddocumento, boolean b) throws NotariaException;
	
	public boolean marcarDocumentoEntregado(String iddocumento, boolean b) throws NotariaException;
	
	public String buscarIdExpPorDocumento(String iddocumento) throws NotariaException;
	
	public ActoDocumento obtenerCompletoPorId(String idactodoc) throws NotariaException;
	
	public boolean actualizaTexto(String idactodoc, String txtFormato) throws NotariaException;
	
	public String obtenerIdTramitePorDocumento(String idactodoc) throws NotariaException;

	public Usuario switchNotario(String idusuario) throws NotariaException;

	Usuario getNotario() throws NotariaException;

	public boolean tieneDocActo(String idacto)throws NotariaException;

	public void borrar(ActoDocumento actoDoc)throws NotariaException;

	public List<DatoActoDocumento> obtenerXidActo(Acto acto) throws NotariaException;

	public ArrayList<DatoActoDocumento> obtenerDocXnombre(String nombreDoc, String idexpediente, String idacto)throws NotariaException;
}
