package alertas;

import java.io.Serializable;

public class TipoAlertaDto extends GenericDto implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2064739569392132078L;
	
	private String nombre;
	private String prefijo;
	private String tipoobjeto;
	private String descripcion;
	

	public TipoAlertaDto() {
		// TODO Auto-generated constructor stub
	}
	
	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public String getPrefijo() {
		return prefijo;
	}


	public void setPrefijo(String prefijo) {
		this.prefijo = prefijo;
	}


	public String getTipoobjeto() {
		return tipoobjeto;
	}


	public void setTipoobjeto(String tipoobjeto) {
		this.tipoobjeto = tipoobjeto;
	}


	public String getDescripcion() {
		return descripcion;
	}


	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}


	
}

