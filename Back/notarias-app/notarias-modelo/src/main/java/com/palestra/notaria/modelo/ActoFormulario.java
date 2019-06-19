package com.palestra.notaria.modelo;

import java.io.Serializable;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name="tbbsnm47")
@NamedQueries({
	@NamedQuery(name="ActoFormulario.findById", query="SELECT af FROM ActoFormulario af WHERE af.idactoformulario = :identificador"),
	@NamedQuery(name="ActoFormulario.deleteById", query="DELETE FROM ActoFormulario WHERE idactoformulario = :identificador"),
	@NamedQuery(name="ActoFormulario.findAll", query="SELECT af FROM ActoFormulario af WHERE af.conFormulario.id.idconFormulario = :idconformulario AND af.conFormulario.id.version = :version"),
	@NamedQuery(name="ActoFormulario.deleteAll", query="DELETE FROM ActoFormulario WHERE conFormulario.id.idconFormulario = :idconformulario AND conFormulario.id.version = :version"),
})
public class ActoFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private String idactoformulario;

	private String idsesion;

	private Timestamp tmstmp;

	//bi-directional many-to-one association to Acto
	@ManyToOne(fetch = FetchType.LAZY )
	@JoinColumn(name="idsuboperacion")
	private Suboperacion suboperacion;

	//bi-directional many-to-one association to ConFormulario
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumns({
		@JoinColumn(name="idconformulario", referencedColumnName="idconFormulario"),
		@JoinColumn(name="versionform", referencedColumnName="version")
		})
	private ConFormulario conFormulario;
	
	@Column(columnDefinition="ENUM")
	private String inestatus;

	private int inposicion;
	
	public ActoFormulario() {
	}

	public String getIdactoformulario() {
		return this.idactoformulario;
	}

	public void setIdactoformulario(String idactoformulario) {
		this.idactoformulario = idactoformulario;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public Suboperacion getSuboperacion() {
		return suboperacion;
	}

	public void setSuboperacion(Suboperacion suboperacion) {
		this.suboperacion = suboperacion;
	}

	public ConFormulario getConFormulario() {
		return conFormulario;
	}

	public void setConFormulario(ConFormulario conFormulario) {
		this.conFormulario = conFormulario;
	}

	public String getInestatus(){
		return this.inestatus;
	}
	
	public void setInestatus(String inestatus){
		this.inestatus = inestatus;
	}
	
	public int getInposicion(){
		return this.inposicion;
	}
	
	public void setInposicion(int inposicion){
		this.inposicion = inposicion;
	}
}