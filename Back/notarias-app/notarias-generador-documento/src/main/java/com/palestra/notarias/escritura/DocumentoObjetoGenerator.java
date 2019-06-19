package com.palestra.notarias.escritura;

import java.io.Reader;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.impl.FormularioBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Formulario;

/**
 * Clase que reemplaza las variables de tipo '$(tbl:definicion)' y
 * ${frm:definicion} en plantillas documento objeto, por valores de formularios
 * dinamicos .
 * 
 * @author sofia
 * 
 */
public class DocumentoObjetoGenerator {

	static Logger logger = Logger.getLogger(DocumentoObjetoGenerator.class);
	/** Llamadas Bo **/
	FormularioBo formularioBo = new FormularioBoImpl();
	
	FillTemplateWithData fillTemplate = new FillTemplateWithData();

	/**
	 * Reemplaza las variables de tipo tbl y frm, de formularios dinamicos.
	 * @param idacto
	 * @param plantilla
	 * @return
	 * @throws NotariaException 
	 */
	public String reemplazaVariablesFormularios(String idacto, String plantilla) throws NotariaException {
		logger.info("====> reemplazaVariablesFormularios()");
		List<Formulario> lista = formularioBo.findByActoId(idacto);
		if (lista == null) {
			logger.info("====>  " + Constantes.MENSAJE_DocObj1);
			return Constantes.DocObj1;
		}

		//Nota: es importante el orden de remplazo. 
		/** 1) Reemplazar primero las tablas de los formulario **/
		String textoNoTablas = this.reemplazaTablas(lista, plantilla);
		if (textoNoTablas == null) {
			return null;
		}
		/** 2) Reemplazar segundo las variables frm **/
		String textoNoFrm = this.reemplazaFrmTokens(lista, textoNoTablas);
		
		//--> cafaray 110814 Manejo de referencias a variables
		//ubicar el idicador de area de referencias para eliminarlo de la plantilla
		int iniciaReferencias = textoNoFrm.indexOf("REFERENCIAS{");		
		textoNoFrm = textoNoFrm.substring(0,iniciaReferencias>0?iniciaReferencias:textoNoFrm.length());
		//<-- cafaray 110814
		
		return textoNoFrm;
	}

	/**
	 * Primeramente se sustituyen los tokens '$(tbl:definicion)' de tablas.
	 * 
	 * @param datoTemplate
	 * @param texto
	 * @return
	 */
	private String reemplazaTablas(List<Formulario> listaForms, String htmlText) {
		try {
			/** Se sustituyen subformulario(tablas) '$(tbl:definicion)' **/
			Reader readerTokensTbl = fillTemplate.replaceTableTokenInTemplate(
					listaForms, htmlText);
			if (readerTokensTbl == null) {
				return null;
			}
			return IOUtils.toString(readerTokensTbl);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Reemplazar frmTokens sencillos en la plantilla. '${frm:nombreCortoFrm[nombreVar]}'
	 * 
	 * @param listaForms
	 * @param htmlText
	 * @return
	 */
	private String reemplazaFrmTokens(List<Formulario> listaForms,
			String htmlText) {
		logger.info("====> reemplazaFrmTokens(): ");
		try {
			/** Se sustituyen frmTokens '${frm: ]}' **/
			Reader readerTokensFrm = fillTemplate.replaceFormTokenInTemplate(
					listaForms, htmlText);
			if (readerTokensFrm == null) {
				/** si no se pudo trasformar regresar la misma cadena que se recibio**/
				return htmlText;
			}
			return IOUtils.toString(readerTokensFrm);
		} catch (Exception e) {
			e.printStackTrace(System.out);
			return htmlText;
		}

	}

}
