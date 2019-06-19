package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "tbbsnm78")
@NamedNativeQueries({
	@NamedNativeQuery(name="getSiguientePasada", 
						query="select esc from PizarronElemento esc where esc.numeroescritura > :numeroescritura and esc.status='lib-paso' limit 1",
						resultClass=PizarronElemento.class),
	@NamedNativeQuery(name="getPendientesBeforeSiguientePasada",
						query="select esc from PizarronElemento esc where esc.numeroescritura > :numeroescritura and esc.numeroescritura < "
							+ "(select piz.numeroescritura from PizarronElemento piz where piz.numeroescritura > :numeroescritura and piz.status='lib-paso' limit 1) "
							+ "and esc.status='lib-pendiente' order by esc.numeroescritura", 
						resultClass=PizarronElemento.class)
})
public class PizarronElemento implements Serializable  {
	
	private static final long serialVersionUID = 2471060447950448635L;

	@Id
	private String idpizarronelemento;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idabogado")
	private Usuario idabogado;
	
	@Transient
	private String abogado;
	
	@Transient
	private String libro;
	
	@Transient
	private Long cantidadfolios;

	private Long foliolibro;

	
	private Timestamp fecha;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idlibro")
	private Libro idlibro;
	
	private Long folioinicial;
	private Long foliofinal;
	
	
	private Long numeroescritura;
	
	private String status;
	
	@Column(columnDefinition="INT")
	private Boolean iscierrelibro;
	
	private String idsesion;
	
	private Timestamp tmstmp;
	
	public PizarronElemento() {
		// TODO Auto-generated constructor stub
	}

	

	public Timestamp getFecha() {
		return fecha;
	}

	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}

	

	public Long getFolioInicial() {
		return folioinicial;
	}

	public void setFolioInicial(Long folioinicial) {
		this.folioinicial = folioinicial;
	}

	public Long getFolioFinal() {
		return foliofinal;
	}

	public void setFolioFinal(Long foliofinal) {
		this.foliofinal = foliofinal;
	}

	public Long getNumeroescritura() {
		return numeroescritura;
	}

	public void setNumeroescritura(Long numeroescritura) {
		this.numeroescritura = numeroescritura;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Boolean getIscierrelibro() {
		return iscierrelibro;
	}

	public void setIscierrelibro(Boolean iscierrelibro) {
		this.iscierrelibro = iscierrelibro;
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

	public String getIdpizarronelemento() {
		return idpizarronelemento;
	}

	public void setIdpizarronelemento(String idpizarronelemento) {
		this.idpizarronelemento = idpizarronelemento;
	}





	public Usuario getIdabogado() {
		return idabogado;
	}



	public void setIdabogado(Usuario idabogado) {
		this.idabogado = idabogado;
	}



	public Libro getIdlibro() {
		return idlibro;
	}



	public void setIdlibro(Libro idlibro) {
		this.idlibro = idlibro;
	}



	public String getAbogado() {
		return abogado;
	}



	public void setAbogado(String abogado) {
		this.abogado = abogado;
	}



	public Long getCantidadfolios() {
		return this.getFolioFinal() - this.getFolioInicial();
	}



	public Long getFoliolibro() {
		return foliolibro;
	}



	public void setFoliolibro(Long foliolibro) {
		this.foliolibro = foliolibro;
	}



	public String getLibro() {
		return libro;
	}



	public void setLibro(String libro) {
		this.libro = libro;
	}
	
	
}
