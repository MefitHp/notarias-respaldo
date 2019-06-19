package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="tbcfgm22")
@NamedQueries(value = {
		@NamedQuery(name="Expresion.getByComponente",query="SELECT e FROM Expresion e JOIN e.variable varstipo WHERE " +
				"e.variable.idvarstipo = ?1")
})
public class Expresion implements Serializable {

	private static final long serialVersionUID = -6166501386694384517L;
	
	@Id
	private String idexpresion;
	
	@ManyToOne
	@JoinColumn(name="idvariable")
	private VariablesTipo variable;
	
	@Transient
	private String idvariable;
	
	@Column(columnDefinition="TEXT")
	private String dsexpresion;
	
	private String ifnulo;
	
	@Column(columnDefinition="INT")
	private Boolean isvalido;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getIdvariable() {
		return idvariable;
	}
	public void setIdvariable(String idvariable) {
		this.idvariable = idvariable;
	}
	public String getIdexpresion() {
		return idexpresion;
	}

	public void setIdexpresion(String idexpresion) {
		this.idexpresion = idexpresion;
	}
	public VariablesTipo getVariable() {
		return variable;
	}
	public void setVariable(VariablesTipo variable) {
		this.variable = variable;
	}

	public String getDsexpresion() {
		return dsexpresion;
	}

	public void setDsexpresion(String dsexpresion) {
		this.dsexpresion = dsexpresion;
	}

	public String getIfnulo() {
		return ifnulo;
	}

	public void setIfnulo(String ifnulo) {
		this.ifnulo = ifnulo;
	}

	public Boolean getIsvalido() {
		return isvalido;
	}

	public void setIsvalido(Boolean isvalido) {
		this.isvalido = isvalido;
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
