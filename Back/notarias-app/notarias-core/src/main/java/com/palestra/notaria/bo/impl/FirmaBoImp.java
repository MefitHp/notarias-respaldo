package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.FirmaBo;
import com.palestra.notaria.dao.FirmaDao;
import com.palestra.notaria.dao.impl.FirmaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Firma;

public class FirmaBoImp extends GenericBoImpl<Firma> implements FirmaBo{
	
	private FirmaDao firmaDao;
	
	public FirmaBoImp(){
		firmaDao = new FirmaDaoImpl();
		super.dao = firmaDao;
	}
	
	@Override
	public Firma save(Firma firma)throws NotariaException{
		
		return this.firmaDao.save(firma);
	} 
	
	@Override
	public void borrar(Firma firma)throws NotariaException{
		this.firmaDao.borrar(firma);
	}
	
	@Override
	public Firma obtenerFirmaPorCompareciente(Compareciente compareciente)throws NotariaException{
		return this.firmaDao.obtenerFirmaPorCompareciente(compareciente);
	}

	@Override
	public boolean checkcompletas(String idescritura) throws NotariaException {
		EscrituraActoBo escbo = new EscrituraActoBoImpl();
		List<EscrituraActo> lista = escbo.findByEscrituraId(idescritura);
		ComparecienteBo compbo = new ComparecienteBoImpl();
		for(EscrituraActo escact : lista){
			List<Compareciente> listcomptmp = compbo.listadoComparecientesByActo(escact.getActo().getIdacto());
			if(listcomptmp.size()< 1) return false;
			for(Compareciente com:listcomptmp){
				if(com.getFirma()==null ){
					return false;
				}
			}
		}
		return true;
	}
	
}
