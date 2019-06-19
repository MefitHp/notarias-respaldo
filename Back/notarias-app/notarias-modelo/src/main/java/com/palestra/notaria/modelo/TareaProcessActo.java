package com.palestra.notaria.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm82")
public class TareaProcessActo implements Serializable {

	private static final long serialVersionUID = -2527387436532225155L;
	
	@Id
	private Long idtarea;
	
	private String nombretarea;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idacto", referencedColumnName="idacto"),
		@JoinColumn(name="idproceso", referencedColumnName="idproceso")
		})
	private ProcessActo processActo;

	@Column(columnDefinition="INT")
	private Boolean isactive; 
	
	public Boolean getIsactive() {
		return isactive;
	}
	public void setIsactive(Boolean isactive) {
		this.isactive = isactive;
	}
	public Long getIdtarea() {
		return idtarea;
	}

	public void setIdtarea(Long idtarea) {
		this.idtarea = idtarea;
	}

	public String getNombretarea() {
		return nombretarea;
	}

	public void setNombretarea(String nombretarea) {
		this.nombretarea = nombretarea;
	}

	public ProcessActo getProcessActo() {
		return processActo;
	}

	public void setProcessActo(ProcessActo processActo) {
		this.processActo = processActo;
	}
	
	
}
