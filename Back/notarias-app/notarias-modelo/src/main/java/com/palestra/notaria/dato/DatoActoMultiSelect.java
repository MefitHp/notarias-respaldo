package com.palestra.notaria.dato;
import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;


/**
 * Clase wraper para mostrar datos en el combo multiselect para
 * seleccionar actos y asociarlos a la escritura.
 * @author sofia
 *
 */
public class DatoActoMultiSelect implements Serializable{

	private static final long serialVersionUID = 139849702237213260L;
	
	private String idacto;
	
	private String nombreActo;
	
	private String descripcionActo;
	
	private String nombreOperacion;
	
	/** indica si el acto esta asociado a una escritura, es decir si esta seleccionado **/
	private String checked;
	
	public DatoActoMultiSelect(){
		
	}
	
	public String getNombreOperacion() {
		return nombreOperacion;
	}
	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}

	public String getIdacto() {
		return idacto;
	}

	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}

	public String getNombreActo() {
		return nombreActo;
	}

	public void setNombreActo(String nombreActo) {
		this.nombreActo = nombreActo;
	}

	public String getDescripcionActo() {
		return descripcionActo;
	}

	public void setDescripcionActo(String descripcionActo) {
		this.descripcionActo = descripcionActo;
	}

	public String getChecked() {
		return checked;
	}
	
	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("idacito", this.getIdacto())
		        .append("nombreActo", this.getNombreActo())
		        .append("descripcionActo", this.getDescripcionActo())
		        .append("checked", this.checked)
                .toString();
	}
}
