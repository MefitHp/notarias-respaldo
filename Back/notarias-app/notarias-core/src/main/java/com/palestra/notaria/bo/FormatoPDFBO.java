package com.palestra.notaria.bo;


import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDF;


public interface FormatoPDFBO extends GenericBo<FormatoPDF> {

	FormatoPDF buscarXNombre(String nombre) throws NotariaException;

//	List<FormatoPDFDetalle> listarFormatoDetalle(String identificador) throws NotariaException;
//
//	void eliminaDetalles(String identificador) throws NotariaException;
//
//	List<FormatoPDFDetalle> agregarDetalle(List<FormatoPDFDetalle> detalles)
//			throws NotariaException;
//
//	List<FormatoPDFDetalle> quitarDetalle(FormatoPDFDetalle detalle)
//			throws NotariaException;
//
//	List<FormatoPDFDetalle> agregarDetalle(FormatoPDFDetalle detalle)
//			throws NotariaException;
	
}
