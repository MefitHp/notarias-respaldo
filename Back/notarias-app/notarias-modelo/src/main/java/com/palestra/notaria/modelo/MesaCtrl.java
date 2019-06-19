package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.palestra.notaria.enums.EnumStatusDoc;

@Entity
@Table(name="tbbsnm74")
@NamedQueries(value={
		@NamedQuery(name="MesaCtrl.findAll", query="SELECT m FROM MesaCtrl m ORDER BY m.tmstmp DESC"),
		@NamedQuery(name="MesaCtrl.findByStatusPago", query="SELECT m FROM MesaCtrl m WHERE m.pago.statuspago = :statuspago ORDER BY m.tmstmp DESC"),
		@NamedQuery(name="MesaCtrl.findByActoDocumento", query="SELECT m FROM MesaCtrl m WHERE m.actodocumento.idactodoc = :idactodoc"),
		@NamedQuery(name="MesaCtrl.findNopaso", query="SELECT m FROM MesaCtrl m WHERE m.escritura.dsnumescritura = :numeroescritura AND m.estatusdoc='NO_PASO'"),
		@NamedQuery(name="MesaCtrl.findEscritura", query="SELECT m FROM MesaCtrl m WHERE m.escritura.idescritura = :idescritura")
		
})
public class MesaCtrl implements Serializable {
	
	
	private static final long serialVersionUID = -5179218641744965685L;

	@Id
	private String idmesacontrol;
	
	@Transient
	private Boolean ispagorequire;
	
	@Column(columnDefinition="INT")
	private Boolean isterminado;
	
	private Date termino;
	
	private String idtarea;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idactodoc")
	private ActoDocumento actodocumento;
	
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idescritura")
	private Escritura escritura;

	//Conectar alertas
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idalerta")
	private AlertaObjeto alerta;
	
	//Conectar Pago
	@OneToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idpago")
	private Pago pago;
	
	
	@Enumerated(EnumType.STRING)
	private EnumStatusDoc estatusdoc;
	
	private Timestamp updated;
	private Date vencimiento;
	private Timestamp tmstmp;
	private String idsesion;

	public String getIdmesacontrol() {
		return idmesacontrol;
	}

	public void setIdmesacontrol(String idmesacontrol) {
		this.idmesacontrol = idmesacontrol;
	}

	public Boolean getIspagorequire() {
		return ispagorequire;
	}

	public void setIspagorequire(Boolean ispagorequire) {
		this.ispagorequire = ispagorequire;
	}
	

	public Timestamp getUpdated() {
		return updated;
	}
	

	public void setUpdated(Timestamp updated) {
		this.updated = updated;
	}

	
	
	
	public ActoDocumento getActodocumento() {
		return actodocumento;
	}

	public void setActodocumento(ActoDocumento actodocumento) {
		this.actodocumento = actodocumento;
	}

	public Escritura getEscritura() {
		return escritura;
	}

	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}

	public AlertaObjeto getAlerta() {
		return alerta;
	}

	public void setAlerta(AlertaObjeto alerta) {
		this.alerta = alerta;
	}

	
	public Pago getPago() {
		return pago;
	}

	public void setPago(Pago pago) {
		this.pago = pago;
	}

	public Timestamp getTmstmp() {
		return tmstmp;
	}

	public void setTmstmp(Timestamp tmstmp) {
		this.tmstmp = tmstmp;
	}

	public String getIdsesion() {
		return idsesion;
	}

	public void setIdsesion(String idsesion) {
		this.idsesion = idsesion;
	}

	public EnumStatusDoc getEstatusdoc() {
		return estatusdoc;
	}

	public void setEstatusdoc(EnumStatusDoc estatusdoc) {
		this.estatusdoc = estatusdoc;
	}

	public Timestamp getUpdate() {
		return updated;
	}

	public void setUpdate(Timestamp update) {
		this.updated= update;
	}

	public Date getVencimiento() {
		return vencimiento;
	}

	public void setVencimiento(Date vencimiento) {
		this.vencimiento = vencimiento;
	}

	public Boolean getIsterminado() {
		return isterminado;
	}

	public void setIsterminado(Boolean isterminado) {
		this.isterminado = isterminado;
	}

	public Date getTermino() {
		return termino;
	}

	public void setTermino(Date termino) {
		this.termino = termino;
	}

	public String getIdtarea() {
		return idtarea;
	}

	public void setIdtarea(String idtarea) {
		this.idtarea = idtarea;
	}
	
}
