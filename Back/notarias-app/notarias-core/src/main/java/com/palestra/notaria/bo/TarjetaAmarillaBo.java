
package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.dato.DatoActoDeTarjeta;
import com.palestra.notaria.dato.DatoTarjetaAmarilla;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.TarjetaAmarilla;

public interface TarjetaAmarillaBo extends GenericBo<TarjetaAmarilla>{

	DatoActoDeTarjeta obtenDataFromActo(String id) throws NotariaException;
	
	TarjetaAmarilla buscarPorIdCompleto(TarjetaAmarilla tarjetaAmarilla)throws NotariaException;
	
	List<DatoTarjetaAmarilla> obtenListaTarjetas(TarjetaAmarilla tarjetaAmarilla)throws NotariaException;
}
