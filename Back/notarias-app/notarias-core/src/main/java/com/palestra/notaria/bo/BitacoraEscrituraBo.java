package com.palestra.notaria.bo;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Usuario;

public interface BitacoraEscrituraBo extends GenericBo<BitacoraEscritura> {

	BitacoraEscritura obtenerUltimaBitacoraXEscritura(Escritura escritura) throws NotariaException;

	BitacoraEscritura save(Boolean terminaproceso, Escritura escritura, Usuario usr, String bitacora)
			throws NotariaException;

}
