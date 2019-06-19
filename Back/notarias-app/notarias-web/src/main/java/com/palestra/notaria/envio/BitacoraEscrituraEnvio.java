package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;

public class BitacoraEscrituraEnvio extends GenericEnvio implements Serializable {

	private BitacoraEscritura ultimaBitacora = null;
	private List<BitacoraEscritura> bitacoras = null;
	private Escritura escritura =null;
	
	private static final long serialVersionUID = -6347697295994748284L;

	public BitacoraEscritura getUltimaBitacora() {
		return ultimaBitacora;
	}

	public void setUltimaBitacora(BitacoraEscritura ultimaBitacora) {
		this.ultimaBitacora = ultimaBitacora;
	}

	public List<BitacoraEscritura> getBitacoras() {
		return bitacoras;
	}

	public void setBitacoras(List<BitacoraEscritura> bitacoras) {
		this.bitacoras = bitacoras;
	}

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

	

}
