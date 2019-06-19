package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.TarjetaAmarillaBo;
import com.palestra.notaria.dao.TarjetaAmarillaDao;
import com.palestra.notaria.dao.impl.TarjetaAmarillaDaoImpl;
import com.palestra.notaria.dato.DatoActoDeTarjeta;
import com.palestra.notaria.dato.DatoTarjetaAmarilla;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TarjetaAmarilla;

public class TarjetaAmarillaBoImpl extends GenericBoImpl<TarjetaAmarilla> implements TarjetaAmarillaBo{

	private TarjetaAmarillaDao tarjetaAmarillaDao;
	
	public TarjetaAmarillaBoImpl(){
		this.tarjetaAmarillaDao = new TarjetaAmarillaDaoImpl();
		super.dao = this.tarjetaAmarillaDao;
	}
	
	@Override
	public DatoActoDeTarjeta obtenDataFromActo(String id) throws NotariaException{
		return this.tarjetaAmarillaDao.obtenDataFromActo(id);
	}
	
	@Override
	public TarjetaAmarilla buscarPorIdCompleto(TarjetaAmarilla tarjetaAmarilla)throws NotariaException {
		return this.tarjetaAmarillaDao.buscarPorIdCompleto(tarjetaAmarilla);
	}
	
	@Override
	public List<DatoTarjetaAmarilla> obtenListaTarjetas(
			TarjetaAmarilla tarjetaAmarilla) throws NotariaException{
		return this.tarjetaAmarillaDao.obtenListaTarjetas(tarjetaAmarilla);
	}

}
