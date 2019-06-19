package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ControlFoliosBo;
import com.palestra.notaria.bo.EscrituraExternaBo;
import com.palestra.notaria.dao.EscrituraExternaDao;
import com.palestra.notaria.dao.impl.EscrituraExternaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.EscrituraExterna;

public class EscrituraExternaBoImpl extends GenericBoImpl<EscrituraExterna>
		implements EscrituraExternaBo {

	private EscrituraExternaDao escrituraDao;

	public EscrituraExternaBoImpl() {
		escrituraDao = new EscrituraExternaDaoImpl();
		super.dao = escrituraDao;
	}

	@Override
	public EscrituraExterna guardarEscrituraExterna(EscrituraExterna escritura)
			throws NotariaException {

		ControlFoliosBo cfBo = new ControlFoliosBoImpl();
		ControlFolios cf = cfBo.getFolios();
		
		
		
		boolean excep = false;
		String msj = "";
		if (escritura.getFolioini() > escritura.getFoliofin()) {
			excep = true;
			msj = "El folio inicial no puede ser menor al folio final";
		} else if (escritura.getFolioini() <= cf.getFolioActual()) {
			excep = true;
			msj = "El folio que intentas registrar es menor al actual";
		} else if (cf.getFolioActual() + 1 != escritura.getFolioini()) {
			excep = true;
			msj = "El folio que intentas registrar no es un consecutivo a los folios existentes";
		} else if((escritura.getFoliofin()-escritura.getFolioini())>=(cf.getFoliosDisponibles()-cf.getFolioActual())){
			excep = true;
			msj = "No hay folios suficientes para registrar la escritura, solicite más folios";
		}
		
		if (excep) {
			throw new NotariaException(msj);
		} else {
			
			EscrituraExterna escrituraValida = escrituraDao.findByNumero(escritura.getDsnumescritura());
			
			if(escrituraValida !=null){
				throw new NotariaException("El número de escritura ya se encuentra registrado en el sistema");
			}
			
			escrituraDao.save(escritura);
			cf.setFolioActual(escritura.getFoliofin());
			cfBo.update(cf);
			
			return escritura;
		}
	}
}
