package com.palestra.notaria.bo;

import java.sql.Timestamp;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public interface BitacoraFoliosBo extends GenericBo<BitacoraFolios> {

	public List<BitacoraFolios> listarDevueltos() throws NotariaException;

	public List<BitacoraFolios> listarDevueltosPorAbogado(String idusuario)
			throws NotariaException;

	public List<BitacoraFolios> listar_x_numeroescritura(String numeroescritura)throws NotariaException;

	public List<BitacoraFolios> listar_x_usuario(Usuario usuario)throws NotariaException;

	public List<BitacoraFolios> listar_x_fechas(Timestamp tmpInicio,
			Timestamp tmpFin)throws NotariaException;

}
