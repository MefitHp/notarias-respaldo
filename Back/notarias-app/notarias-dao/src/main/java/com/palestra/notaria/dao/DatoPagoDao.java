package com.palestra.notaria.dao;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;

public interface DatoPagoDao extends GenericDao<DatoPago, Integer>{
	
	Boolean guardarDatoPago(DatoPago datoPago, String idDatoFisca, ElementoCatalogo status) throws NotariaException;
	
	Double sumaImportePagoPorSoliciutd(String idSolPago) throws NotariaException;

}
