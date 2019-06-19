package com.palestra.notaria.dao;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFirma;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Usuario;

public interface BitacoraFirmaDao extends GenericDao<BitacoraFirma, Integer> {

	List<BitacoraFirma> obtenerListaBitacoraFirma(BitacoraFirma bitacoraFirma) throws NotariaException;

	Boolean saveUpdateBitacora(ArrayList<Compareciente> comparecientes,
			Usuario usuario, String idescritura, String idexpediente) throws NotariaException;

}
