package com.palestra.notaria.bo;


import java.util.List;

import com.palestra.notaria.enums.EnumStatusPago;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Usuario;

public interface MesaCtrlBo extends GenericBo<MesaCtrl>{

		@Override
		public MesaCtrl save(MesaCtrl mesactrl)throws NotariaException;
	
		
		public void borrar(MesaCtrl mesactrl)throws NotariaException;



		public List<MesaCtrl> findByEstatusPago(EnumStatusPago estatusPago) throws NotariaException;


		public MesaCtrl findByActoDocumento(String actodocumento) throws NotariaException;


		MesaCtrl update(MesaCtrl mesa, Usuario usuario) throws NotariaException;


		MesaCtrl findNoPaso(Escritura esc) throws NotariaException;


		public List<MesaCtrl> findByEscritura(Escritura escritura)throws NotariaException;

}
