package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.ElementoCatalogo;

public interface DatoPagoBo extends GenericBo<DatoPago>{
	
	Boolean guardarDatoPago(DatoPago datoPago, String idDatoFisca, ElementoCatalogo status) throws NotariaException;
	
	Double sumaImportePagoPorSoliciutd(String idSolPago)throws NotariaException;

}
