package com.palestra.notaria.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

	@Entity
	@Table(name="tbbsnm83")
	public class FechasBPM implements Serializable {
		private static final long serialVersionUID = 1L;
		
		@Id
		@Column
		private Date fecha; 
		
		public Date getFecha() {
			return fecha;
		}
		public void setFecha(Date fecha) {
			this.fecha = fecha;
		}
}
