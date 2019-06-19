package alertas;

import java.io.Serializable;
import java.util.Date;


public class AlertaDto extends GenericDto implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2135664511274946340L;


	private Long id;
	private String statusalerta;
	private Boolean isfinalizado;
	private String tipoobjeto;
	private Date vencimiento;
	
	public String getTipoobjeto() {
		return tipoobjeto;
	}

	public void setTipoobjeto(String tipoobjeto) {
		this.tipoobjeto = tipoobjeto;
	}

	

	public AlertaDto setAlerta(Long id, String statusalerta){
		this.id= id;
		this.statusalerta = statusalerta;
		return this;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatusalerta() {
		return statusalerta;
	}

	public void setStatusalerta(String statusalerta) {
		this.statusalerta = statusalerta;
	}

	public Boolean getIsfinalizado() {
		return isfinalizado;
	}

	public void setIsfinalizado(Boolean isfinalizado) {
		this.isfinalizado = isfinalizado;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	

}
