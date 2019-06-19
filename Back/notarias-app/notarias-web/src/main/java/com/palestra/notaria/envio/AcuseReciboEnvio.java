package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.modelo.AcuseReciboEscritura;
import com.palestra.notaria.modelo.AcuseReciboPersonas;

public class AcuseReciboEnvio extends GenericEnvio {

	private AcuseReciboEscritura acuseReciboEscritura=null;
	
	private ArrayList<AcuseReciboPersonas> acuseReciboPersonas=null;
	
	public AcuseReciboEscritura getAcuseReciboEscritura() {
		return acuseReciboEscritura;
	}
	
	public void setAcuseReciboEscritura(
			AcuseReciboEscritura acuseReciboEscritura) {
		this.acuseReciboEscritura = acuseReciboEscritura;
	}
	
	public ArrayList<AcuseReciboPersonas> getAcuseReciboPersonas() {
		return acuseReciboPersonas;
	}
	
	public void setAcuseReciboPersonas(
			ArrayList<AcuseReciboPersonas> acuseReciboPersonas) {
		this.acuseReciboPersonas = acuseReciboPersonas;
	}
}
