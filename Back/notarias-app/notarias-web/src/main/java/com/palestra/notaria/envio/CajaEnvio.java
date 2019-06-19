package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.List;

import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Pago;

public class CajaEnvio extends GenericEnvio implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5142660181332460657L;
	private Pago pago;
	private List<MesaCtrl> pagos;
	private String idtarea;

	
	
	public List<MesaCtrl> getPagos() {
		return pagos;
	}
	public void setPagos(List<MesaCtrl> pagos) {
		this.pagos = pagos;
	}
	public Pago getPago() {
		return pago;
	}
	public void setPago(Pago pago) {
		this.pago = pago;
	}
	public String getIdtarea() {
		return idtarea;
	}
	public void setIdtarea(String idtarea) {
		this.idtarea = idtarea;
	}
	
	

}
