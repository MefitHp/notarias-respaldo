package com.palestra.notaria.bo;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.palestra.notaria.dato.DatoElementoPizarron;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.Usuario;

public interface PizarronElementoBo extends GenericBo<PizarronElemento> {

	public void borrar(String idpizarronelemento) throws NotariaException;

	List<DatoElementoPizarron> buscartodos() throws NotariaException;

	List<DatoElementoPizarron> buscartodos(Integer inicio, Integer total) throws NotariaException;	

	Integer calculadecena(ArrayList<DatoElementoPizarron> elementos) throws NotariaException;

	public void validaFecha(Timestamp fecha) throws NotariaException;

	Long getUltimaEscritura() throws NotariaException;

	Date getUltomaFecha() throws NotariaException;

	public PizarronElemento getXLibro(Libro libro)throws NotariaException;

	ArrayList<DatoElementoPizarron> buscarpendientes(String idabogado) throws NotariaException;

	public PizarronElemento buscarXescritura(String dsnumescritura) throws NotariaException;

	void borrar(String idpizarronelemento, Long numeroescritura) throws NotariaException;
//	TODO: omar 28nov17 comento la validacion para poder modificar pizarrones aunque este cerrado el libro
//    y aunque haya un elemento posterior
//	boolean validamodificacion(Long numeroescritura) throws NotariaException;

	DatoElementoPizarron save(DatoElementoPizarron pizarrondato, Boolean actualizatodo, Usuario usuario)
			throws NotariaException;
}
