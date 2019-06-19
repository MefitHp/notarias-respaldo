package com.palestra.notaria.dao;

import java.util.Date;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FechasBPM;

public interface BonitaBPMDao extends GenericDao {

	List<Date> obtenerFechasInhabiles(Date cal) throws NotariaException;


}
