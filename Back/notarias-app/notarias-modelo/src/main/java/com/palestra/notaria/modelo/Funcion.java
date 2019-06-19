package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "tbcfgm30")

@NamedQueries(value = {
	@NamedQuery(name="Funcion.listaParametros", query="SELECT fp FROM Funcion f JOIN f.detalleList fp WHERE f.identificador = :identificador"),
	@NamedQuery(name="Funcion.findById", query="SELECT f FROM Funcion f WHERE f.identificador = :identificador"),
	@NamedQuery(name="Funcion.findByNombre", query="SELECT f FROM Funcion f WHERE f.nombre = :nombre"),
	@NamedQuery(name="Funcion.findAll", query="SELECT f FROM Funcion f"),
})
public class Funcion implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String identificador;

	@Column(name="dsdescripcion")
	private String descripcion;
	
	@Column(name="dsnombre")
	private String nombre;
	
	@ManyToOne
	@JoinColumn(name="idretorno")
	private ElementoCatalogo retorno;

	@Column(name="dsforma")
	private String forma;
	
	@OneToMany(mappedBy="idfuncion",cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	private List<FuncionParametro> detalleList = new ArrayList<>();
	
	private String idsesion;

	private Timestamp tmstmp;

	public String getIdentificador(){
		return this.identificador;
	}
	
	public void setIdentificador(String identificador){
		this.identificador = identificador;
	}
	
	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public void setRetorno(ElementoCatalogo retorno){
		this.retorno = retorno;
	}
	
	public ElementoCatalogo getRetorno(){
		return this.retorno;
	}
	
	public String getForma() {
		return forma;
	}

	public void setForma(String forma) {
		this.forma = forma;
	}

	public List<FuncionParametro> getDetalleList() {
		return detalleList;
	}

	public void setDetalleList(List<FuncionParametro> detalleList) {		
		this.detalleList = detalleList;
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
