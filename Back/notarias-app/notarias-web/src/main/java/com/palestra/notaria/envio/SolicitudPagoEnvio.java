package com.palestra.notaria.envio;

import java.util.ArrayList;

import com.palestra.notaria.dato.DatoNombrePagador;
import com.palestra.notaria.modelo.DatoFiscalPago;
import com.palestra.notaria.modelo.DatoPago;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.SolicitudPago;

public class SolicitudPagoEnvio extends GenericEnvio{

	private SolicitudPago solicitudPago = null;
	
	private DatoFiscalPago datosPagador = null;
	
	private DatoPago datoPago = null;
	
	private Expediente expediente = null;
	
	private ArrayList<SolicitudPago> listaSolicitudes = null;
	
	private DatoNombrePagador datoNombrePagador;

	public SolicitudPago getSolicitudPago() {
		return solicitudPago;
	}

	public void setSolicitudPago(SolicitudPago solicitudPago) {
		this.solicitudPago = solicitudPago;
	}

	public ArrayList<SolicitudPago> getListaSolicitudes() {
		return listaSolicitudes;
	}

	public void setListaSolicitudes(ArrayList<SolicitudPago> listaSolicitudes) {
		this.listaSolicitudes = listaSolicitudes;
	}
	
	public Expediente getExpediente() {
		return expediente;
	}

	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}

	public DatoFiscalPago getDatosPagador() {
		return datosPagador;
	}

	public void setDatosPagador(DatoFiscalPago datosPagador) {
		this.datosPagador = datosPagador;
	}

	public DatoPago getDatoPago() {
		return datoPago;
	}

	public void setDatoPago(DatoPago datoPago) {
		this.datoPago = datoPago;
	}
	
	public DatoNombrePagador getDatoNombrePagador() {
		return datoNombrePagador;
	}
	
	public void setDatoNombrePagador(DatoNombrePagador datoNombrePagador) {
		this.datoNombrePagador = datoNombrePagador;
	}

}
