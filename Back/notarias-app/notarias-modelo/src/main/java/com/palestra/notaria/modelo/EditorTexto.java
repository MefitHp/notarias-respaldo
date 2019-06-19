package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm19")
public class EditorTexto implements Serializable {

	private static final long serialVersionUID = -352626428472621100L;
	
	private String dstitulo;
	
	@Lob()
	private String txtexto;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getDstitulo() {
		return dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}

	public String getTxtexto() {
		return txtexto;
	}

	public void setTxtexto(String txtexto) {
		this.txtexto = txtexto;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}
	
	public Boolean getIsactivo() {
		return isactivo;
	}
	
	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}
}
