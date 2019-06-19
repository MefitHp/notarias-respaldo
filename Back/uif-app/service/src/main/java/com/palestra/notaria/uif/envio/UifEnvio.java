package com.palestra.notaria.uif.envio;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.palestra.notaria.uif.core.models.Uif;

@XmlRootElement(name="uifenvio")
public class UifEnvio implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 376783931967349880L;
	protected Uif uif;
	protected Long page;
	protected Long totalpages;
	protected Long number;
	protected Long numberOfElements;
	protected String parametrobusqueda;
	protected String tipobusqueda;
	protected List<Uif> content;
	protected boolean first=false;
	protected boolean last=false;
	protected boolean exito = true;
	protected String status;
	
	
	public List<Uif> getContent() {
		return content;
	}
	public void setContent(List<Uif> content) {
		this.content = content;
	}
	public Uif getPersona() {
		return uif;
	}
	public void setPersona(Uif uif) {
		this.uif = uif;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public String getParametrobusqueda() {
		return parametrobusqueda;
	}
	public void setParametrobusqueda(String parametrobusqueda) {
		this.parametrobusqueda = parametrobusqueda;
	}
	public String getTipobusqueda() {
		return tipobusqueda;
	}
	public void setTipobusqueda(String tipobusqueda) {
		this.tipobusqueda = tipobusqueda;
	}
	public Uif getUif() {
		return uif;
	}
	public void setUif(Uif uif) {
		this.uif = uif;
	}
	public Long getTotalpages() {
		return totalpages;
	}
	public void setTotalpages(Long totalpages) {
		this.totalpages = totalpages;
	}
	public boolean isFirst() {
		return first;
	}
	public void setFirst(boolean first) {
		this.first = first;
	}
	public boolean isLast() {
		return last;
	}
	public void setLast(boolean last) {
		this.last = last;
	}
	public boolean isExito() {
		return exito;
	}
	public void setExito(boolean exito) {
		this.exito = exito;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Long getNumber() {
		return number;
	}
	public void setNumber(Long number) {
		this.number = number;
	}
	public Long getNumberOfElements() {
		return numberOfElements;
	}
	public void setNumberOfElements(Long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

}
