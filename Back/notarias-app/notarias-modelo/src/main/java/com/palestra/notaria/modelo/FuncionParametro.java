package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm31")
@NamedQueries(value={
		@NamedQuery(name="FuncionParametro.eliminaParametros", query="DELETE FROM FuncionParametro fp WHERE fp.idfuncion.identificador = :identificador")
})

public class FuncionParametro implements Serializable{

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer cdfuncion;
	
	@ManyToOne
	@JoinColumn(name="idfuncion",referencedColumnName="identificador")
	private Funcion idfuncion;
	
	@Column(name="dsparam")
	private String parametro; 
	
	@ManyToOne
	@JoinColumn(name="idtipo")
	private ElementoCatalogo tipo;
	
	@Column(columnDefinition="INT")
	private Boolean isrequerido;
	
	private String idsesion;
	private Timestamp tmstmp;
	
	
	public Integer getCdfuncion() {
		return cdfuncion;
	}
	
	public void setCdfuncion(Integer cdfuncion) {
		this.cdfuncion = cdfuncion;
	}
	
	public Funcion getIdfuncion(){
		return this.idfuncion;
	}
	
	public void setIdfuncion(Funcion idfuncion){
		this.idfuncion = idfuncion;
	}
	
	public String getParametro() {
		return parametro;
	}

	public void setParametro(String parametro) {
		this.parametro = parametro;
	}

	public void setTipo(ElementoCatalogo tipo){
		this.tipo = tipo;
	}
	
	public ElementoCatalogo getTipo(){
		return this.tipo;
	}
	
	public Boolean getIsrequerido() {
		return isrequerido;
	}

	public void setIsrequerido(Boolean isrequerido) {
		this.isrequerido = isrequerido;
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
}
