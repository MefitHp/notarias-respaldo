package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.BitacoraFolios;
import com.palestra.notaria.modelo.Contacto;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.DevolucionRechazadaFolio;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraExterna;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.SolicitudPrestamoFolios;
import com.palestra.notaria.modelo.Usuario;

public class FolioEnvio extends GenericEnvio {

	private List<BitacoraFolios> foliosDevueltos = new ArrayList<BitacoraFolios>();
	
	private List<SolicitudPrestamoFolios> foliosPrestadosList= new ArrayList<SolicitudPrestamoFolios>();
	
	private List<DevolucionRechazadaFolio> foliosRechazadosList = new ArrayList<DevolucionRechazadaFolio>();
	
	private List<SolicitudPrestamoFolios> solicitudesNoAtendidas = new ArrayList<SolicitudPrestamoFolios>();
	
	private List<SolicitudPrestamoFolios> prestadosAbogado = new ArrayList<SolicitudPrestamoFolios>();
	
	private List<SolicitudPrestamoFolios> solicitadosAbogado = new ArrayList<SolicitudPrestamoFolios>();
	
	private SolicitudPrestamoFolios solicitudPrestamo = null;
	
		
	private DevolucionRechazadaFolio devolucionRechazada = null;
	
	private EscrituraExterna escrituraExt = null;
	
	private Usuario abogado = null;
	
	private List<Escritura> escrituras = new ArrayList<Escritura>();
	
	private Contacto contactoCliente = null;
	
	private Libro libro = null;
	
	private String comentario  = null;
	
	private String fechaInicio =null;
	private String fechaFin= null;
	
	
	public List<SolicitudPrestamoFolios> getPrestadosAbogado() {
		return prestadosAbogado;
	}
	public void setPrestadosAbogado(
			List<SolicitudPrestamoFolios> prestadosAbogado) {
		this.prestadosAbogado = prestadosAbogado;
	}
	public Usuario getAbogado() {
		return abogado;
	}
	public void setAbogado(Usuario abogado) {
		this.abogado = abogado;
	}
	
	public List<SolicitudPrestamoFolios> getSolicitudesNoAtendidas() {
		return solicitudesNoAtendidas;
	}
	public void setSolicitudesNoAtendidas(
			List<SolicitudPrestamoFolios> solicitudesNoAtendidas) {
		this.solicitudesNoAtendidas = solicitudesNoAtendidas;
	}
	public Libro getLibro() {
		return libro;
	}
	public void setLibro(Libro libro) {
		this.libro = libro;
	}
	
	private Long folioInicio = null;
	
	private Long folioFin = null;
	
	public Long getFolioInicio() {
		return folioInicio;
	}
	public void setFolioInicio(Long folioInicio) {
		this.folioInicio = folioInicio;
	}
	public Long getFolioFin() {
		return folioFin;
	}
	public void setFolioFin(Long folioFin) {
		this.folioFin = folioFin;
	}
	
	public List<Escritura> getEscrituras() {
		return escrituras;
	}
	public void setEscrituras(List<Escritura> escrituras) {
		this.escrituras = escrituras;
	}
	
	public Contacto getContactoCliente() {
		return contactoCliente;
	}
	public void setContactoCliente(Contacto contactoCliente) {
		this.contactoCliente = contactoCliente;
	}
	
	public EscrituraExterna getEscrituraExt() {
		return escrituraExt;
	}
	public void setEscrituraExt(EscrituraExterna escrituraExt) {
		this.escrituraExt = escrituraExt;
	}
	
	public DevolucionRechazadaFolio getDevolucionRechazada() {
		return devolucionRechazada;
	}
	public void setDevolucionRechazada(
			DevolucionRechazadaFolio devolucionRechazada) {
		this.devolucionRechazada = devolucionRechazada;
	}
	
	
	private ControlFolios controlFolios = null;

	public SolicitudPrestamoFolios getSolicitudPrestamo() {
		return solicitudPrestamo;
	}
	public void setSolicitudPrestamo(SolicitudPrestamoFolios solicitudPrestamo) {
		this.solicitudPrestamo = solicitudPrestamo;
	}
	public ControlFolios getControlFolios() {
		return controlFolios;
	}
	public void setControlFolios(ControlFolios controlFolios) {
		this.controlFolios = controlFolios;
	}

	public List<BitacoraFolios> getFoliosDevueltos() {
		return foliosDevueltos;
	}

	public void setFoliosDevueltos(List<BitacoraFolios> list) {
		this.foliosDevueltos = list;
	}

	public List<SolicitudPrestamoFolios> getFoliosPrestadosList() {
		return foliosPrestadosList;
	}

	public void setFoliosPrestadosList(
			List<SolicitudPrestamoFolios> foliosPrestadosList) {
		this.foliosPrestadosList = foliosPrestadosList;
	}

	public List<DevolucionRechazadaFolio> getFoliosRechazadosList() {
		return foliosRechazadosList;
	}

	public void setFoliosRechazadosList(
			List<DevolucionRechazadaFolio> foliosRechazadosList) {
		this.foliosRechazadosList = foliosRechazadosList;
	}
	public String getComentario() {
		return comentario;
	}
	public void setComentario(String comentario) {
		this.comentario = comentario;
	}
	public String getFechaFin() {
		return fechaFin;
	}
	public void setFechaFin(String fechaFin) {
		this.fechaFin = fechaFin;
	}
	public String getFechaInicio() {
		return fechaInicio;
	}
	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	public List<SolicitudPrestamoFolios> getSolicitadosAbogado() {
		return solicitadosAbogado;
	}
	public void setSolicitadosAbogado(List<SolicitudPrestamoFolios> solicitadosAbogado) {
		this.solicitadosAbogado = solicitadosAbogado;
	}
	
	
}
