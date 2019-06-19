package com.palestra.notaria.util;

import java.util.List;

import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.MesaCtrlBoImpl;
import com.palestra.notaria.bo.impl.PizarronElementoBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.PizarronElemento;

public class VerificaEscritura {

	private static EscrituraBo esbo = new EscrituraBoImpl();

	
	public VerificaEscritura() {
		// TODO Auto-generated constructor stub
		
	}
	
	public VerificaEscritura(String idescritura){
	}
	
	public static void nopaso(String idescritura) throws NotariaException{
		Escritura escritura = esbo.findById(idescritura);
		if (escritura != null){
			if(escritura.getNopaso()){
				throw new NotariaException("La escritura se encuentra en no paso y no es posible actualizarla");
			}
		}
		
		MesaCtrlBo mesaBo = new MesaCtrlBoImpl();
		List<MesaCtrl> mesactrls = mesaBo.findByEscritura(escritura);
		
		if(mesactrls.size()>0){
			throw new NotariaException("La escritura "+escritura.getDsnumescritura()+" ya tiene documentos solicitados en mesa de control y no es posible actualizarla");
		}
		
	}
	

}
