package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "tbbsnm46")
@NamedQueries({
	@NamedQuery(name="ConFormulario.findDuplicate", query="SELECT f FROM ConFormulario f WHERE f.dsnombrecorto = :nombrecorto AND f.locacion.idelemento = :idlocacion AND f.ispublicado = true"),
	@NamedQuery(name="ConFormulario.findById", query = "SELECT f FROM ConFormulario f WHERE f.id.idconFormulario = :identificador AND f.id.version = :version"),
	@NamedQuery(name="ConFormulario.findByNombreCorto", query="SELECT f FROM ConFormulario f WHERE f.dsnombrecorto = :nombrecorto AND f.ispublicado = true"),
})

public class ConFormulario implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ConFormularioPK id;
	
	private int posiciontarjeton;

	private String dsdescripcion;

	private String dstitulo;
	
	@Column(unique=true)
	private String dsnombrecorto;

	@Temporal(TemporalType.DATE)
	private Date fechapublicacion;

	@Column(columnDefinition="INT")
	private Boolean ispublicado;

	@ManyToOne
	@JoinColumn(name="idlocacion")
	private ElementoCatalogo locacion;
	
	
	private String idsesion;
	private Timestamp tmstmp;	
	
	//bi-directional many-to-one association to ActoFormulario
	@OneToMany(mappedBy="conFormulario", cascade = CascadeType.ALL)
	private List<ActoFormulario> listaActoFormularios;

	//bi-directional many-to-one association to PermisoRol
	@OneToMany(mappedBy="conFormulario", cascade = CascadeType.ALL)
	private List<PermisoRol> listaPermisosRol;

	//bi-directional many-to-one association to ConSubFormulario
	@OneToMany(mappedBy="conFormulario", cascade = CascadeType.ALL)
	private List<ConSubFormulario> listaSubFormularios;

	//bi-directional many-to-one association to Componente
	@OneToMany(mappedBy="conFormulario", cascade = CascadeType.ALL)
	private List<Componente> listaComponentes;
	
	// Solo se permite dos valores, D | E, documentos o escritura respectivamente
	@Enumerated(value=EnumType.STRING)
	@Column(columnDefinition="ENUM",name="intipoform")
	private String tipoform;
	
	public ConFormulario() {
	}

	public ConFormularioPK getId() {
		return this.id;
	}

	public void setId(ConFormularioPK id) {
		this.id = id;
	}

	public String getDsdescripcion() {
		return this.dsdescripcion;
	}

	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}

	public String getDstitulo() {
		return this.dstitulo;
	}

	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}
	
	public String getDsnombrecorto() {
		return dsnombrecorto;
	}
	
	public void setDsnombrecorto(String dsnombrecorto) {
		this.dsnombrecorto = dsnombrecorto;
	}
	
	public Date getFechapublicacion() {
		return this.fechapublicacion;
	}

	public void setFechapublicacion(Date fechapublicacion) {
		this.fechapublicacion = fechapublicacion;
	}

	public String getIdsesion() {
		return this.idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public Boolean getIspublicado() {
		return ispublicado;
	}

	public void setIspublicado(Boolean ispublicado) {
		this.ispublicado = ispublicado;
	}

	public Timestamp getTmstmp() {
		return this.tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public List<PermisoRol> getListaPermisosRol() {
		return listaPermisosRol;
	}

	public void setListaPermisosRol(List<PermisoRol> listaPermisosRol) {
		this.listaPermisosRol = listaPermisosRol;
	}

	public List<ConSubFormulario> getListaSubFormularios() {
		return listaSubFormularios;
	}

	public void setListaSubFormularios(List<ConSubFormulario> listaSubFormularios) {
		this.listaSubFormularios = listaSubFormularios;
	}

	public List<ActoFormulario> getListaActoFormularios() {
		return listaActoFormularios;
	}

	public void setListaActoFormularios(List<ActoFormulario> listaActoFormularios) {
		this.listaActoFormularios = listaActoFormularios;
	}

	public List<Componente> getListaComponentes() {
		return listaComponentes;
	}

	public void setListaComponentes(List<Componente> listaComponentes) {
		this.listaComponentes = listaComponentes;
	}

	public String getTipoform(){
		return this.tipoform;		
	}
	
	public void setTipoform(String tipoform){
		tipoform = tipoform.toUpperCase();
		if(tipoform.equals("E")||tipoform.equals("D")||tipoform.equals("O")){
			this.tipoform = tipoform;
		}else{
			tipoform = " ";
		}
	}
	
	public ElementoCatalogo getLocacion() {
		return locacion;
	}

	public void setLocacion(ElementoCatalogo locacion) {
		this.locacion = locacion;
	}

	public int getPosiciontarjeton() {
		return posiciontarjeton;
	}

	public void setPosiciontarjeton(int posiciontarjeton) {
		this.posiciontarjeton = posiciontarjeton;
	}

}