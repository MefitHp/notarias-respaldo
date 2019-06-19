package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.List;

import com.palestra.notaria.bo.BitacoraFoliosBo;
import com.palestra.notaria.dao.BitacoraFoliosDao;
import com.palestra.notaria.dao.impl.BitacoraFoliosDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public class BitacoraFoliosBoImpl extends GenericBoImpl<BitacoraFolios>
		implements BitacoraFoliosBo {

	BitacoraFoliosDao bitFolDao;
	
	public BitacoraFoliosBoImpl(){
		bitFolDao = new BitacoraFoliosDaoImpl();
		super.dao = bitFolDao;
	}

	@Override
	public List<BitacoraFolios> listarDevueltos() throws NotariaException {
		return bitFolDao.listarDevueltos();
	}
	
	@Override
	public List<BitacoraFolios> listarDevueltosPorAbogado(String idusuario) throws NotariaException{
		return bitFolDao.listarDevueltosPorAbogado(idusuario);
	}

	@Override
	public List<BitacoraFolios> listar_x_numeroescritura(String numeroescritura)
			throws NotariaException {
		return bitFolDao.listarXnumEsc(numeroescritura);
	}

	@Override
	public List<BitacoraFolios> listar_x_usuario(Usuario usuario)
			throws NotariaException {
		return bitFolDao.listarXusuario(usuario);
	}

	@Override
	public List<BitacoraFolios> listar_x_fechas(Timestamp tmpInicio,
			Timestamp tmpFin) throws NotariaException {
		
		return bitFolDao.listarXRangoFecha(tmpInicio, tmpFin);
	}
}
