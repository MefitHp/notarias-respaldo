package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.BitacoraEscrituraBo;
import com.palestra.notaria.dao.BitacoraEscrituraDao;
import com.palestra.notaria.dao.impl.BitacoraEscrituraDaoImp;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;


public class BitacoraEscrituraBoImp extends GenericBoImpl<BitacoraEscritura> implements BitacoraEscrituraBo {

	private BitacoraEscrituraDao beDao;
	
	public BitacoraEscrituraBoImp() {
		this.beDao = new BitacoraEscrituraDaoImp();
		this.dao = this.beDao;
	}
	
	@Override
	public BitacoraEscritura save(Boolean terminaproceso,Escritura escritura,Usuario usr,String bitacora) throws NotariaException {
		
		BitacoraEscritura be = new BitacoraEscritura();
		be.setBitacora(bitacora);
		be.setEscritura(escritura);
		be.setIdsesion(usr.getIdsesionactual());
		be.setIdbitacoraescritura(GeneradorId.generaId(be));
		be.setTerminado(terminaproceso);
		this.dao.save(be);
		
		return be;
	};
	
	@Override
	public BitacoraEscritura obtenerUltimaBitacoraXEscritura(Escritura escritura)throws NotariaException{
		return this.beDao.obtenerUltimaBitacoraXEscritura(escritura);
	}

}
