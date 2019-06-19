package alertas;

import java.io.Serializable;

public class StepDto extends GenericDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1718789399189330538L;
	
	private String etiqueta;
	private String estadoactual;
	private int frecuencia;
	private String mensaje;
	private int tiempo;
	private String tipoalerta;
	private Boolean isfinal;
	
	
	public String getEtiqueta() {
		return etiqueta;
	}


	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}


	public String getEstadoactual() {
		return estadoactual;
	}


	public void setEstadoactual(String estadoactual) {
		this.estadoactual = estadoactual;
	}


	public int getFrecuencia() {
		return frecuencia;
	}


	public void setFrecuencia(int frecuencia) {
		this.frecuencia = frecuencia;
	}


	public String getMensaje() {
		return mensaje;
	}


	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}


	public int getTiempo() {
		return tiempo;
	}


	public void setTiempo(int tiempo) {
		this.tiempo = tiempo;
	}


	public String getTipoalerta() {
		return tipoalerta;
	}


	public void setTipoalerta(String tipoalerta) {
		this.tipoalerta = tipoalerta;
	}


	public Boolean getIsfinal() {
		return isfinal;
	}


	public void setIsfinal(Boolean isfinal) {
		this.isfinal = isfinal;
	}


	public StepDto() {
		// TODO Auto-generated constructor stub
	}

}
