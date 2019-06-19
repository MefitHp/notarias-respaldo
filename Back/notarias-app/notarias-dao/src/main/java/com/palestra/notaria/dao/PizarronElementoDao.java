package com.palestra.notaria.dao;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;

public interface PizarronElementoDao extends GenericDao<PizarronElemento, Integer> {

	void borrar(String idelementopizarron) throws NotariaException;

	List<PizarronElemento> findAll(Integer inicio, Integer total) throws NotariaException;

	PizarronElemento save(PizarronElemento pizarron, ControlFolios newControlFolios) throws NotariaException;

	Date getFechaMenor() throws NotariaException;

	List<PizarronElemento> buscarPendientes(String idabogado) throws NotariaException;

	Long getUltimaEscritura() throws NotariaException;

	PizarronElemento getXLibro(Libro libro) throws NotariaException;

	PizarronElemento buscarXescritura(Long numesc) throws NotariaException;

	List<PizarronElemento> buscarMayorXEscritura(Long numesc, String propiedad) throws NotariaException;

	void update(List<PizarronElemento> pizarrones, ControlFolios controlfolios) throws NotariaException;

	List<PizarronElemento> obtenerAnteriores(Long numesc) throws NotariaException;

	Boolean isPizarronEditable(Integer numesc) throws NotariaException;

	Boolean existenPosterioresPasados(Long numesc) throws NotariaException;

	void update(List<PizarronElemento> pizarrones) throws NotariaException;

	PizarronElemento obtenerSiguientePasada(Long numesc) throws NotariaException;

	List<PizarronElemento> getPendientesAntesDeSiguientePasada(Long numesc, boolean hasPosteriores) throws NotariaException;

	PizarronElemento obtenerAnteriorPasada(Long numesc) throws NotariaException;

	List<PizarronElemento> getPendientesAntesDeAnteriorPasada(Long numesc) throws NotariaException;


}

