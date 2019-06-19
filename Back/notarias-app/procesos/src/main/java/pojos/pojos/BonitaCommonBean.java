package pojos.pojos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import pojos.pojos.Impuesto;

public class BonitaCommonBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4950500607638691653L;

	private String idExpediente;
	
	private String numExpediente;
	
	private String idActo;
	
	private String nombreOperacion;
	
	private String usuario;
	
	private String idsesionactual;
	
	private String nombreAbogado;
	
	private String iniciales;
	
	private String referencia;
	
	private String numeroEscritura;
	
	private String idEscritura;
	
	private Date fechaCertificadoGravamen;
	
	private List<Impuesto> impuestos;
	
	private Boolean hasDim;
	
	private Boolean isCompraventa;
	
	private String idDimDoc;
	
	private String localidad;
	
	private Long idproceso;
	
	private Long idtarea;
	
	private String nombreTarea;
	
	private String idusuario;
	
	private String nombreDemandado;
	
	private String expedienteJudicial;
	
	private String mensajeCorreo;
	
	private int dias;
	
	private Calendar calendar;
	
	
	public String getMensajeCorreo() {
		return mensajeCorreo;
	}
	public void setMensajeCorreo(String mensajeCorreo) {
		this.mensajeCorreo = mensajeCorreo;
	}
	
	public String getExpedienteJudicial() {
		return expedienteJudicial;
	}
	public void setExpedienteJudicial(String expedienteJudicial) {
		this.expedienteJudicial = expedienteJudicial;
	}
	public String getNombreDemandado() {
		return nombreDemandado;
	}
	public void setNombreDemandado(String nombreDemandado) {
		this.nombreDemandado = nombreDemandado;
	}
	
	public Long getIdtarea() {
		return idtarea;
	}
	public void setIdtarea(Long idtarea) {
		this.idtarea = idtarea;
	}
	public String getIdusuario() {
		return idusuario;
	}
	public void setIdusuario(String idusuario) {
		this.idusuario = idusuario;
	}
	public Long getIdproceso() {
		return idproceso;
	}
	public void setIdproceso(Long idproceso) {
		this.idproceso = idproceso;
	}

	public String getIdActo() {
		return idActo;
	}
	public void setIdActo(String idActo) {
		this.idActo = idActo;
	}
	public String getNombreOperacion() {
		return nombreOperacion;
	}
	public void setNombreOperacion(String nombreOperacion) {
		this.nombreOperacion = nombreOperacion;
	}
	public String getLocalidad() {
		return localidad;
	}

	public void setLocalidad(String localidad) {
		this.localidad = localidad;
	}

	public String getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(String idExpediente) {
		this.idExpediente = idExpediente;
	}

	public String getNumExpediente() {
		return numExpediente;
	}

	public void setNumExpediente(String numExpediente) {
		this.numExpediente = numExpediente;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getIdsesionactual() {
		return idsesionactual;
	}

	public void setIdsesionactual(String idsesionactual) {
		this.idsesionactual = idsesionactual;
	}

	public String getNombreAbogado() {
		return nombreAbogado;
	}

	public void setNombreAbogado(String nombreAbogado) {
		this.nombreAbogado = nombreAbogado;
	}

	public String getIniciales() {
		return iniciales;
	}

	public void setIniciales(String iniciales) {
		this.iniciales = iniciales;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNumeroEscritura() {
		return numeroEscritura;
	}

	public void setNumeroEscritura(String numeroEscritura) {
		this.numeroEscritura = numeroEscritura;
	}

	public String getIdEscritura() {
		return idEscritura;
	}

	public void setIdEscritura(String idEscritura) {
		this.idEscritura = idEscritura;
	}

	public Date getFechaCertificadoGravamen() {
		return fechaCertificadoGravamen;
	}

	public void setFechaCertificadoGravamen(Date fechaCertificadoGravamen) {
		this.fechaCertificadoGravamen = fechaCertificadoGravamen;
	}

	public List<Impuesto> getImpuestos() {
		return impuestos;
	}

	public void setImpuestos(List<Impuesto> impuestos) {
		this.impuestos = impuestos;
	}

	public Boolean getHasDim() {
		return hasDim;
	}

	public void setHasDim(Boolean hasDim) {
		this.hasDim = hasDim;
	}

	public String getIdDimDoc() {
		return idDimDoc;
	}

	public void setIdDimDoc(String idDimDoc) {
		this.idDimDoc = idDimDoc;
	}

	public Boolean getIsCompraventa() {
		return isCompraventa;
	}
	public void setIsCompraventa(Boolean isCompraventa) {
		this.isCompraventa = isCompraventa;
	}
	public String getNombreTarea() {
		return nombreTarea;
	}
	public void setNombreTarea(String nombreTarea) {
		this.nombreTarea = nombreTarea;
	}
	public int getDias() {
		return dias;
	}
	public void setDias(int dias) {
		this.dias = dias;
	}
	public Calendar getCalendar() {
		return calendar;
	}
	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}


}
