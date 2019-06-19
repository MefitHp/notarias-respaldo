package pojos.pojos;

import java.util.Date;

public class Tarea {
	
	private Long id;
	private String nombre; 
	private Date vencimiento;
	private Date solicidada;
	private Impuesto impuesto;
	private Escritura escritura;
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getNombre() {
		return nombre;
	}


	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


	public Date getVencimiento() {
		return vencimiento;
	}


	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}


	public Date getSolicidada() {
		return solicidada;
	}


	public void setSolicidada(Date solicidada) {
		this.solicidada = solicidada;
	}


	public Tarea() {
		// TODO Auto-generated constructor stub
	}


	public Impuesto getImpuesto() {
		return impuesto;
	}


	public void setImpuesto(Impuesto impuesto) {
		this.impuesto = impuesto;
	}


	public Escritura getEscritura() {
		return escritura;
	}


	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

}
