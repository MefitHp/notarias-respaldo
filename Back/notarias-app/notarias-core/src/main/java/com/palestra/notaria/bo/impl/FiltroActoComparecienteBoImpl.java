package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.FiltroActoComparecienteBo;
import com.palestra.notaria.dao.FiltroActoComparecienteDao;
import com.palestra.notaria.dao.impl.FiltroActoComparecienteDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FiltroActoCompareciente;
import com.palestra.notaria.modelo.Operacion;

	public class FiltroActoComparecienteBoImpl extends GenericBoImpl<FiltroActoCompareciente> implements FiltroActoComparecienteBo{

		private FiltroActoComparecienteDao filtroActoComparecienteDao;
		
		public FiltroActoComparecienteBoImpl() {
			this.filtroActoComparecienteDao = new FiltroActoComparecienteDaoImpl();
			super.dao = this.filtroActoComparecienteDao;
		}
		

		@Override
		public List<FiltroActoCompareciente> comparecientesXSubop(Operacion sop)
				throws NotariaException{
			
			return this.filtroActoComparecienteDao.comparecientesXSubop(sop);
		};
		
		@Override
		public void borrar(FiltroActoCompareciente filtro)throws NotariaException{
			filtroActoComparecienteDao.borrar(filtro);
		}


		
}
