package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.DevolucionRechazadaFolio;

public interface DevolucionRechazadaFolioBo extends
		GenericBo<DevolucionRechazadaFolio> {

	public List<DevolucionRechazadaFolio> listarRechazados() throws NotariaException;

}
