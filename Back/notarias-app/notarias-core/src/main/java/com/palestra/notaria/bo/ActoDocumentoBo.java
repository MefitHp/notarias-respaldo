package com.palestra.notaria.bo;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.DatoDocumentoTarjeton;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Usuario;

import pojos.pojos.BonitaCommonBean;

public interface ActoDocumentoBo extends GenericBo<ActoDocumento>{
	
	public List<DatoActoDocumento> obtenerPrevios(String idexpediente,
			String idacto) throws NotariaException;
	
	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente,
			String idacto) throws NotariaException;
	public boolean tieneDocActo(String idacto)throws NotariaException;
	
	public String buscarArchivoPorId(String iddocumento) throws NotariaException;
	
	public boolean actualizarRutaArchivo(String iddocumento, String ruta) throws NotariaException;
	
	public boolean actualizarRutaArchivoFormato(String iddocumento, String ruta) throws NotariaException;
	
	public boolean marcarDocumentoAprovado(String iddocumento, boolean marcar) throws NotariaException;
	
	public boolean marcarDocumentoEntregado(String iddocumento, boolean marcar) throws NotariaException;
	
	public String buscarIdExpPorDocumento(String iddocumento) throws NotariaException;
	
	public ActoDocumento obtenerCompletoPorId(String idactodoc) throws NotariaException;
	
	public boolean actualizaTexto(String idactodoc, String txtFormato) throws NotariaException;
	
	public String obtenerIdTramitePorDocumento(String idactodoc) throws NotariaException;

	public Usuario switchNotario(String idusuario) throws NotariaException;
	
	public Usuario getNotario() throws NotariaException;

	public void borrar(ActoDocumento actoDoc) throws NotariaException;

	List<DatoDocumentoTarjeton> obtenerXidActo(Acto acto) throws NotariaException;


	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente)throws NotariaException;

	ArrayList<DatoActoDocumento> obtenerDocXnombre(String nombreDoc, String idexpediente, String idacto)
			throws NotariaException;

	void startBonitaJudicialesFlow(BonitaCommonBean datos) throws NotariaException;

	// 20170521 Victor: Se comenta por que ahora se asocia bonita desde el acto
	/*void saveDocProcess(Escritura escritura, DatoActoDocumento act, Usuario usr) throws NotariaException;*/
}
