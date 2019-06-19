package com.palestra.notaria.servicios;

import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.impl.EscrituraActoBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;

public class EscrituraTest {

	public EscrituraTest() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) throws NotariaException {
		
		EscrituraActoBo esactoBo = new EscrituraActoBoImpl();
		Escritura escritura = new Escritura();
		escritura.setIdescritura("ca0bc4693ca14b52f4d81385fbb9f390");
		String dato = esactoBo.validaCertificado(escritura);
		System.out.println("Fecha:"+dato);
	}

}
