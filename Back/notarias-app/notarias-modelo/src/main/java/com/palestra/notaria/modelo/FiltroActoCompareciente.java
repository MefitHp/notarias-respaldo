package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


/**
 * The persistent class for the tbbsnm18 database table.
 * 
 */
@Entity
@Table(name="tbbsnm18b")
@NamedQueries({
	@NamedQuery(name="FiltroActoCompareciente.filtrarXSubop", query="SELECT fac FROM FiltroActoCompareciente fac where fac.suboperacion.idoperacion = :identificador"),
})
public class FiltroActoCompareciente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	private String idfiltroActoCompareciente;
	//bi-directional many-to-one association to Suboperacion
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="idsuboperacion")
	private Operacion suboperacion;


	// bi-directional many-to-one association to TipoCompareciente
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "idtipocompareciente")
		private TipoCompareciente tipoCompareciente;
		
		private String idsesion;
		private Timestamp tmstmp;
		public String getIdfiltroActoCompareciente() {
			return idfiltroActoCompareciente;
		}
		public void setIdfiltroActoCompareciente(String idfiltroActoCompareciente) {
			this.idfiltroActoCompareciente = idfiltroActoCompareciente;
		}
		public Operacion getSuboperacion() {
			return suboperacion;
		}
		public void setSuboperacion(Operacion suboperacion) {
			this.suboperacion = suboperacion;
		}
		public TipoCompareciente getTipoCompareciente() {
			return tipoCompareciente;
		}
		public void setTipoCompareciente(TipoCompareciente tipoCompareciente) {
			this.tipoCompareciente = tipoCompareciente;
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