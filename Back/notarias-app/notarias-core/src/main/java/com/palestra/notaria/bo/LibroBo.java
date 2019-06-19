package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;

public interface LibroBo extends GenericBo<Libro>{
	
	public Libro obtenUltimoLibro() throws NotariaException;

	public Libro obtenLibroXnumero(Long numero) throws NotariaException;

	boolean validaFoliosLibro(Long cantidadFolios, PizarronElemento pizarron) throws NotariaException;

}
