package com.palestra.notaria.dato;

import org.apache.commons.lang.builder.ToStringBuilder;

public class DatoDocumentoObjeto {
	
	private String id;
	private Integer version;
	private String dsnombre;
	
	public DatoDocumentoObjeto(){
		
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public Integer getVersion() {
		return version;
	}
	
	public void setVersion(Integer version) {
		this.version = version;
	}
	
	public String getDsnombre() {
		return dsnombre;
	}
	
	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
		        .append("id", this.getId())
		        .append("version", this.getVersion())
		        .append("dsnombre", this.getDsnombre())
                .toString();
	}

}

