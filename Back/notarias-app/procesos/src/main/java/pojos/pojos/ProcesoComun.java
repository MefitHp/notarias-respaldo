package pojos.pojos;

import java.io.Serializable;
import java.util.List;

public class ProcesoComun implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5615227687337873647L;
	String idexpediente;
	String idusuario;
	String idsesionactual;
	String expediente;
	String escritura;
	String idescritura;
	String referencia;	
	List<Impuesto> impuestosList;
	
	public List<Impuesto> getImpuestosList() {
		return impuestosList;
	}
	public void setImpuestosList(List<Impuesto> impuestosList) {
		this.impuestosList = impuestosList;
	}
	
	public String getIdexpediente() {
		return idexpediente;
	}


	public void setIdexpediente(String idexpediente) {
		this.idexpediente = idexpediente;
	}


	public String getIdusuario() {
		return idusuario;
	}


	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}


	public String getExpediente() {
		return expediente;
	}


	public void setExpediente(String expediente) {
		this.expediente = expediente;
	}


	public String getEscritura() {
		return escritura;
	}


	public void setEscritura(String escritura) {
		this.escritura = escritura;
	}


	public String getReferencia() {
		return referencia;
	}


	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}


	public ProcesoComun() {
		// TODO Auto-generated constructor stub
	}
	
	public String getIdsesionactual() {
		return idsesionactual;
	}


	public void setIdsesionactual(String idsesionactual) {
		this.idsesionactual = idsesionactual;
	}


	public String getIdescritura() {
		return idescritura;
	}


	public void setIdescritura(String idescritura) {
		this.idescritura = idescritura;
	}

}
