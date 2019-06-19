package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;

import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.dato.DatoEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;

public class EscrituraEnvio extends GenericEnvio implements Serializable{
	
	private static final long serialVersionUID = 2185926627004588479L;
	
	private Escritura escritura = null;
	private Expediente expediente = null;
	private ArrayList<DatoActoMultiSelect> actos = null;
	private ArrayList<DatoEscritura> listaEscrituras = null;
	private String actosConforman = null;
	private String archivofinal = null;
	private Integer porcentajeUltimaPag = null;
	private Integer paginas = null;
	private String fechacertificado = null;
	private boolean istraslativa;
	
	public Escritura getEscritura() {
		return escritura;
	}
	public void setEscritura(Escritura escritura) {
		this.escritura = escritura;
	}
	public ArrayList<DatoActoMultiSelect> getActos() {
		return actos;
	}
	public void setActos(ArrayList<DatoActoMultiSelect> actos) {
		this.actos = actos;
	}
	
	public String getActosConforman() {
		return actosConforman;
	}
	public void setActosConforman(String actosConforman) {
		this.actosConforman = actosConforman;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	public ArrayList<DatoEscritura> getListaEscrituras() {
		return listaEscrituras;
	}
	public void setListaEscrituras(ArrayList<DatoEscritura> listaEscrituras) {
		this.listaEscrituras = listaEscrituras;
	}
	public String getArchivofinal() {
		return archivofinal;
	}
	public void setArchivofinal(String archivofinal) {
		this.archivofinal = archivofinal;
	}
	public Integer getPorcentajeUltimaPag() {
		return porcentajeUltimaPag;
	}
	public void setPorcentajeUltimaPag(Integer porcentajeUltimaPag) {
		this.porcentajeUltimaPag = porcentajeUltimaPag;
	}

	public Integer getPaginas() {
		return paginas;
	}
	public void setPaginas(Integer paginas) {
		this.paginas = paginas;
	}
	public String getFechacertificado() {
		return fechacertificado;
	}
	public void setFechacertificado(String fechacertificado) {
		this.fechacertificado = fechacertificado;
	}
	public boolean isIstraslativa() {
		return istraslativa;
	}
	public void setIstraslativa(boolean istraslativa) {
		this.istraslativa = istraslativa;
	}
	
	
}
