package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="tbcfgm18")
public class DocumentoObjeto implements Serializable {

	private static final long serialVersionUID = 6890847394776944722L;

	@EmbeddedId
	private DocumentoObjetoPK id;
	
	private String dsnombre;
	
	private String dsdescripcion;
	
	@Column(columnDefinition="INT")
	private Boolean ispublicada;
	
	private Timestamp fchpublicacion;
	
	@Lob()
	private String dscontenido;
	
	@ManyToOne
	@JoinColumn(name="idusuariopublico")
	private Usuario usuarioPublico;
	
	@ManyToOne
	@JoinColumn(name="idusuariocreomodifico")
	private Usuario usuarioCreoModifico;
	
	@Column(columnDefinition="INT")
	private Boolean isactivo;
	
	private String idsesion;
	
	private Timestamp tmstmp;

	public String getDsnombre() {
		return dsnombre;
	}

	public void setDsnombre(String dsnombre) {
		this.dsnombre = dsnombre;
	}

	public String getDsdescripcion() {
		return dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public Boolean getIspublicada() {
		return ispublicada;
	}

	public void setIspublicada(Boolean ispublicada) {
		this.ispublicada = ispublicada;
	}

	public Timestamp getFchpublicacion() {
		return fchpublicacion;
	}

	public void setFchpublicacion(Timestamp fchpublicacion) {
		this.fchpublicacion = fchpublicacion;
	}

	public String getDscontenido() {
		return dscontenido;
	}

	public void setDscontenido(String dscontenido) {
		this.dscontenido = dscontenido;
	}

	public Usuario getUsuarioPublico() {
		return usuarioPublico;
	}

	public void setUsuarioPublico(Usuario usuarioPublico) {
		this.usuarioPublico = usuarioPublico;
	}

	public Usuario getUsuarioCreoModifico() {
		return usuarioCreoModifico;
	}
	
	public void setUsuarioCreoModifico(Usuario usuarioCreoModifico) {
		this.usuarioCreoModifico = usuarioCreoModifico;
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
	
	public DocumentoObjetoPK getId() {
		return id;
	}
	
	public void setId(DocumentoObjetoPK id) {
		this.id = id;
	}
	
	public Boolean getIsactivo() {
		return isactivo;
	}
	
	public void setIsactivo(Boolean isactivo) {
		this.isactivo = isactivo;
	}
}
