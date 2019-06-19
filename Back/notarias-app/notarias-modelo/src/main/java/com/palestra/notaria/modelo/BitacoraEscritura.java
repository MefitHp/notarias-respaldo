package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="tbbsnm80")
@NamedQueries(value={@NamedQuery(name="obtenerUltimaXEscritura",query="SELECT be from BitacoraEscritura be where be.escritura.idescritura = :idescritura ORDER BY be.tmstmp DESC")})
public class BitacoraEscritura implements Serializable {

	private static final long serialVersionUID = -3429541337267606093L;

	@Id
	private String idbitacoraescritura;

	private String bitacora;

  	@ManyToOne(fetch=FetchType.LAZY)
  	@JoinColumn(name="idescritura")
  	private Escritura escritura;
  	
	private String idsesion;
	
	@Column(columnDefinition="INT")
	private Boolean terminado;

	private Timestamp tmstmp;

    public void BitacoraExpediente() {}

	public String getIdbitacoraescritura() {
		return idbitacoraescritura;
	}

	public void setIdbitacoraescritura(String idbitacoraescritura) {
		this.idbitacoraescritura = idbitacoraescritura;
	}

	public String getBitacora() {
		return bitacora;
	}

	public void setBitacora(String bitacora) {
		this.bitacora = bitacora;
	}

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
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

	public Boolean getTerminado() {
		return terminado;
	}

	public void setTerminado(Boolean terminado) {
		this.terminado = terminado;
	}
}
