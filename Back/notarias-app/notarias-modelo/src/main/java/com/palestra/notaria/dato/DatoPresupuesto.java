package com.palestra.notaria.dato;

public class DatoPresupuesto implements java.io.Serializable{

	
	private static final long serialVersionUID = -2098609471265735851L;
	private String idacto;
	private String nombreacto;
	private boolean pagado;
	private Double importe = 0.0;
	private Double iva = 0.0;
	private Double total = 0.0;
	
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public String getNombreacto() {
		return nombreacto;
	}
	public void setNombreacto(String nombreacto) {
		this.nombreacto = nombreacto;
	}
	public boolean isPagado() {
		return pagado;
	}
	public void setPagado(boolean pagado) {
		this.pagado = pagado;
	}
	public Double getImporte() {
		return importe;
	}
	public void setImporte(Double importe) {
		this.importe = importe;
	}
	public Double getIva() {
		return iva;
	}
	public void setIva(Double iva) {
		this.iva = iva;
	}
	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}
	
}
