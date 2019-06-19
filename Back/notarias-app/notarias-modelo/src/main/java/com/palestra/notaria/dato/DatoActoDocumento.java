package com.palestra.notaria.dato;

import java.util.Date;

import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.Gestor;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.Valuador;

public class DatoActoDocumento implements java.io.Serializable{

	private static final long serialVersionUID = -833138528928492487L;
	private String idactodoc;
	private String nombre;
	private String notario;
	private String acto;
	private String operacion;
	private String isgestionado;
	private Date fechasolicitud;
	private Date fechaentrega;
	private String rutaArchivo;
	private String rutaEvidencia;
	private String iddocumento;
	private boolean eliminar = false;
	private boolean crear = false;
	private boolean isonline = false;	
	private String status = "X";
	private Documento documento = null;
	private Gestor gestor;
	private FormatoPDF formatopdf = null;
	private ElementoCatalogo tipodoc=null;
	private String dsdescripcion=null;
	private String dstitulo = null;
	private Usuario objNotario = null;
	private Boolean switchNotario = null;
	private Valuador valuador;
	private Integer inorden;
	private Boolean isentregado;
	private Boolean isaprobado;
	private Boolean issolicitado;
	private Boolean tienecomments;
	private Boolean ispagorequire;
	private MesaCtrl mesacontrol;

	public boolean isIsonline() {
		return isonline;
	}

	public void setIsonline(boolean isonline) {
		this.isonline = isonline;
	}

	public Boolean getIsentregado() {
		return isentregado;
	}
	
	public void setIsentregado(Boolean isentregado) {
		this.isentregado = isentregado;
	}
	public Boolean getIsaprobado() {
		return isaprobado;
	}
	public void setIsaprobado(Boolean isaprobado) {
		this.isaprobado = isaprobado;
	}
	
	public String getOperacion() {
		return operacion;
	}
	public void setOperacion(String operacion) {
		this.operacion = operacion;
	}
	public String getRutaEvidencia() {
		return rutaEvidencia;
	}
	public void setRutaEvidencia(String rutaEvidencia) {
		this.rutaEvidencia = rutaEvidencia;
	}
	public String getIsgestionado() {
		return isgestionado;
	}
	public void setIsgestionado(String isgestionado) {
		this.isgestionado = isgestionado;
	}
	public String getDsdescripcion() {
		return dsdescripcion;
	}
	public void setDsdescripcion(String dsdescripcion) {
		this.dsdescripcion = dsdescripcion;
	}
	public String getDstitulo() {
		return dstitulo;
	}
	public void setDstitulo(String dstitulo) {
		this.dstitulo = dstitulo;
	}
	public ElementoCatalogo getTipodoc() {
		return tipodoc;
	}
	public void setTipodoc(ElementoCatalogo tipodoc) {
		this.tipodoc = tipodoc;
	}
	public FormatoPDF getFormatopdf() {
		return formatopdf;
	}
	public void setFormatopdf(FormatoPDF formatopdf) {
		this.formatopdf = formatopdf;
	}
	
	public Boolean getSwitchNotario() {
		return switchNotario;
	}
	
	public void setSwitchNotario(Boolean switchNotario) {
		this.switchNotario = switchNotario;
	}
	
	public String getIdactodoc() {
		return idactodoc;
	}
	public void setIdactodoc(String idactodoc) {
		this.idactodoc = idactodoc;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getNotario() {
		return notario;
	}
	public void setNotario(String notario) {
		this.notario = notario;
	}
	public String getActo() {
		return acto;
	}
	public void setActo(String acto) {
		this.acto = acto;
	}
	public Date getFechasolicitud() {
		return fechasolicitud;
	}
	public void setFechasolicitud(Date fechasolicitud) {
		this.fechasolicitud = fechasolicitud;
	}
	public Date getFechaentrega() {
		return fechaentrega;
	}
	public void setFechaentrega(Date fechaentrega) {
		this.fechaentrega = fechaentrega;
	}
	public String getIddocumento() {
		return iddocumento;
	}
	public void setIddocumento(String iddocumento) {
		this.iddocumento = iddocumento;
	}
	public boolean isEliminar() {
		return eliminar;
	}
	public void setEliminar(boolean eliminar) {
		this.eliminar = eliminar;
	}
	public boolean isCrear() {
		return crear;
	}
	public void setCrear(boolean crear) {
		this.crear = crear;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRutaArchivo() {
		return rutaArchivo;
	}
	public void setRutaArchivo(String rutaArchivo) {
		this.rutaArchivo = rutaArchivo;
	}
	public Documento getDocumento() {
		return documento;
	}
	public void setDocumento(Documento documento) {
		this.documento = documento;
		if(documento!=null){
			this.iddocumento = this.documento.getIddocumento();
		}
	}
	
	public Usuario getObjNotario() {
		return objNotario;
	}
	
	public void setObjNotario(Usuario objNotario) {
		this.objNotario = objNotario;
	}
	
	public Gestor getGestor(){
		return this.gestor;
	}
	public void setGestor(Gestor gestor){
		this.gestor = gestor;
	}
	public Valuador getValuador() {
		return valuador;
	}
	public void setValuador(Valuador valuador) {
		this.valuador = valuador;
	}
	public Integer getInorden() {
		return inorden;
	}
	public void setInorden(Integer inorden) {
		this.inorden = inorden;
	}

	public Boolean getIssolicitado() {
		return issolicitado;
	}

	public void setIssolicitado(Boolean issolicitado) {
		this.issolicitado = issolicitado;
	}

	public Boolean getTienecomments() {
		return tienecomments;
	}

	public void setTienecomments(Boolean tienecomments) {
		this.tienecomments = tienecomments;
	}

	public MesaCtrl getMesacontrol() {
		return mesacontrol;
	}

	public void setMesacontrol(MesaCtrl mesacontrol) {
		if(mesacontrol!=null){
			mesacontrol.setActodocumento(null);
			mesacontrol.setEscritura(null);
		}
		this.mesacontrol = mesacontrol;
		
	}

	public Boolean getIspagorequire() {
		return ispagorequire;
	}

	public void setIspagorequire(Boolean ispagorequire) {
		this.ispagorequire = ispagorequire;
	}
	
}
