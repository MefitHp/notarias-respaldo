package com.palestra.notaria.dato;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Comparator;

import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.Usuario;

public class DatoElementoPizarron implements Serializable,Comparator<DatoElementoPizarron>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5938604194953655559L;

	

	private Timestamp fecha;
	private Long foliofinal;
	private Long folioinicial;
	private Long foliolibro;
	private String idpizarronelemento;
	private Long numeroescritura;
	private String status;
	private String abogado;
	private String idabogado;
	private Long cantidadfolios;
	private boolean iscierrelibro;
	private Long libro;
	private String idlibro;

	public DatoElementoPizarron(){
		// TODO Auto-generated constructor stub
	}
	
	public DatoElementoPizarron(PizarronElemento pe) {
		this.fecha = pe.getFecha();
		this.foliofinal = pe.getFolioFinal();
		this.folioinicial = pe.getFolioInicial();
		this.foliolibro = pe.getFoliolibro();
		this.idpizarronelemento = pe.getIdpizarronelemento();
		this.numeroescritura = pe.getNumeroescritura();
		this.status = pe.getStatus();
		this.abogado = calculaAbogado(pe.getIdabogado());
		if(pe.getIdabogado()!=null){
			this.setIdabogado(pe.getIdabogado().getIdusuario());
		}
		this.cantidadfolios = pe.getCantidadfolios();
		this.iscierrelibro = pe.getIscierrelibro();
		this.libro = pe.getIdlibro().getInnumlibro();
		this.idlibro = pe.getIdlibro().getIdlibro();
	}
	
	
	
	public Timestamp getFecha() {
		return fecha;
	}
	public void setFecha(Timestamp fecha) {
		this.fecha = fecha;
	}
	public Long getFoliofinal() {
		return foliofinal;
	}
	public void setFoliofinal(Long foliofinal) {
		this.foliofinal = foliofinal;
	}
	public Long getFolioinicial() {
		return folioinicial;
	}
	public void setFolioinicial(Long folioinicial) {
		this.folioinicial = folioinicial;
	}
	public Long getFoliolibro() {
		return foliolibro;
	}
	public void setFoliolibro(Long foliolibro) {
		this.foliolibro = foliolibro;
	}
	public String getIdpizarronelemento() {
		return idpizarronelemento;
	}
	public void setIdpizarronelemento(String idpizarronelemento) {
		this.idpizarronelemento = idpizarronelemento;
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
	public String getAbogado() {
		return abogado;
	}
	public void setAbogado(String abogado) {
		this.abogado = abogado;
	}
	public Long getCantidadfolios() {
		return cantidadfolios;
	}
	public void setCantidadfolios(Long cantidadfolios) {
		this.cantidadfolios = cantidadfolios;
	}
	public boolean isIscierrelibro() {
		return iscierrelibro;
	}
	public void setIscierrelibro(boolean iscierrelibro) {
		this.iscierrelibro = iscierrelibro;
	}
	public Long getLibro() {
		return libro;
	}
	public void setLibro(Long libro) {
		this.libro = libro;
	}
	
	
	private String calculaAbogado(Usuario a){
		if(a==null) return null;
		StringBuilder abini = new StringBuilder();
		abini.append(a.getDsiniciales());
		return abini.toString();
		
	}
	// EN ESTA CLASE NO SETEO NI LIBRO NI ABOGADO YA QUE SE TIENE QUE CALCULAR POR INICIALES Y POR NUMERO DE LIBRO PARA SETEARLO
	public static PizarronElemento creaModelo(DatoElementoPizarron dep){
		PizarronElemento respuesta = new PizarronElemento();
		
		
		respuesta.setFecha(dep.getFecha());
		respuesta.setFolioFinal(dep.getFoliofinal());
		respuesta.setFolioInicial(dep.getFolioinicial());
		respuesta.setFoliolibro(dep.getFoliolibro());
		respuesta.setIdpizarronelemento(dep.getIdpizarronelemento());
		respuesta.setNumeroescritura(dep.getNumeroescritura());
		respuesta.setStatus(dep.getStatus());
		respuesta.setIscierrelibro(dep.iscierrelibro);
		respuesta.setAbogado(dep.getAbogado());
		
		
		if( dep.getIdabogado() !=null && !dep.getIdabogado().isEmpty()){
			Usuario abogado = new Usuario();
			abogado.setIdusuario(dep.getIdabogado());
			respuesta.setIdabogado(abogado);

		}
		
		if( dep.getIdlibro() !=null && !dep.getIdlibro().isEmpty()){
			Libro libro = new Libro();
			libro.setIdlibro(dep.getIdlibro());
			respuesta.setIdlibro(libro);

		}
		
		
		return respuesta;
	}

	public String getIdabogado() {
		return idabogado;
	}

	public void setIdabogado(String idabogado) {
		this.idabogado = idabogado;
	}

	public String getIdlibro() {
		return idlibro;
	}

	public void setIdlibro(String idlibro) {
		this.idlibro = idlibro;
	}

	
	@Override
	public int compare(DatoElementoPizarron elemento, DatoElementoPizarron elemento1) {
		// TODO Auto-generated method stub
		return (int) (elemento.numeroescritura - elemento1.numeroescritura);
	}
	
}
