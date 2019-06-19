package pojos.pojos;

import java.io.Serializable;

public class TareaBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7217351196055881854L;
	
	String idproceso;
	String idtarea;
	String nombretarea;
	String idacto;
	String idsesionactual;
	String idusuario;
	
	public String getIdsesionactual() {
		return idsesionactual;
	}
	public void setIdsesionactual(String idsesionactual) {
		this.idsesionactual = idsesionactual;
	}
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(String idproceso) {
		this.idproceso = idproceso;
	}
	public String getIdtarea() {
		return idtarea;
	}
	public void setIdtarea(String idtarea) {
		this.idtarea = idtarea;
	}
	public String getNombretarea() {
		return nombretarea;
	}
	public void setNombretarea(String nombretarea) {
		this.nombretarea = nombretarea;
	}
	
	
	
}
