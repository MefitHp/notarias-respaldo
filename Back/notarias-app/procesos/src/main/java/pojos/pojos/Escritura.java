package pojos.pojos;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Escritura extends ProcesoComun implements Serializable {

	private static final long serialVersionUID = 7139232620334320878L;
	
	Date fechadelcertificado;
	String nombreabogado;
	String inicialesabogado;
	List<Impuesto> impuestos;
	Boolean hasdim =false;
	String iddimdoc;
	
	public Escritura() {
		// TODO Auto-generated constructor stub
	}
	
	public List<Impuesto> getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(List<Impuesto> impuestos) {
		this.impuestos = impuestos;
	}

	public Boolean getHasdim() {
		return hasdim;
	}

	public void setHasdim(Boolean hasdim) {
		this.hasdim = hasdim;
	}

	public String getIddimdoc() {
		return iddimdoc;
	}

	public void setIddimdoc(String iddimdoc) {
		this.iddimdoc = iddimdoc;
	}

	public Date getFechadelcertificado() {
		return fechadelcertificado;
	}

	public void setFechadelcertificado(Date fechadelcertificado) {
		this.fechadelcertificado = fechadelcertificado;
	}

	public String getNombreabogado() {
		return nombreabogado;
	}

	public void setNombreabogado(String nombreabogado) {
		this.nombreabogado = nombreabogado;
	}

	public String getInicialesabogado() {
		return inicialesabogado;
	}

	public void setInicialesabogado(String inicialesabogado) {
		this.inicialesabogado = inicialesabogado;
	}

}
