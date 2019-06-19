package com.palestra.notaria.dao;

import java.sql.Timestamp;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;


public interface BitacoraFoliosDao extends GenericDao<BitacoraFolios, Integer>{

	List<BitacoraFolios> listarDevueltosPorAbogado(String idusuario)
			throws NotariaException;

	public List<BitacoraFolios> listarDevueltos() throws NotariaException;

	public List<BitacoraFolios> listarXusuario(Usuario usu) throws NotariaException;

	public List<BitacoraFolios> listarXRangoFecha(Timestamp inicio, Timestamp fin)
			throws NotariaException;

	
	public List<BitacoraFolios> listarXnumEsc(String numesc)throws NotariaException;

	// public List<SolicitudPrestamoFolios> listarDevueltos()throws NotariaException;

	// public List<DevolucionFolios> listarDevueltosPorAbogado(String idusuario)
	//		throws NotariaException;

}
