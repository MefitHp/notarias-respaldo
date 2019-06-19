package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm81")
public class ProcessActo implements Serializable{

	private static final long serialVersionUID = 413610053879652617L;
	public ProcessActo() {}
	
	@EmbeddedId
	private ProcessActoPk idactoproceso;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="idacto", insertable=false, updatable=false)
	private Acto acto;
	
	@Column(insertable=false, updatable=false)
	private Long idproceso;
	
	@OneToMany(mappedBy="processActo",cascade=CascadeType.ALL)
	private List<TareaProcessActo> listaTareaProcessActo;
	
	public List<TareaProcessActo> getListaTareaProcessActo() {
		return listaTareaProcessActo;
	}
	public void setListaTareaProcessActo(List<TareaProcessActo> listaTareaProcessActo) {
		this.listaTareaProcessActo = listaTareaProcessActo;
	}
	
	public ProcessActoPk getIdactoproceso() {
		return idactoproceso;
	}
	public void setIdactoproceso(ProcessActoPk idactoproceso) {
		this.idactoproceso = idactoproceso;
	}
	public Acto getActo() {
		return acto;
	}
	public void setActo(Acto acto) {
		this.acto = acto;
	}
	public Long getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(Long idproceso) {
		this.idproceso = idproceso;
	}

	
}
