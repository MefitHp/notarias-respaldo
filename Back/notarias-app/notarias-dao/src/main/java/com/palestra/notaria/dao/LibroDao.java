package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Libro;

public interface LibroDao extends GenericDao<Libro, Integer>{
	
	public Libro obtenUltimoLibro() throws NotariaException;

	public Libro obtenLibroXnumero(Long numero) throws NotariaException;
	
	public Libro findById(String id);

}
