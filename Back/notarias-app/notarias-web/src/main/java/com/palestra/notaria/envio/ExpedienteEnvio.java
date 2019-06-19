package com.palestra.notaria.envio;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.dato.DatoOperacion;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Usuario;

public class ExpedienteEnvio extends GenericEnvio{

	private Expediente expediente = null;
	private ArrayList<Expediente> lista = null;
	private Compareciente compareciente=null;
	private DatoOperacion operacion;
	private ArrayList<DatoOperacion> listaOperaciones = null;
	private String idOperacion = null;
	private Persona persona = null;
	private String idacto = null;
	private ArrayList<Persona> listaPersona = null;
	private List<Usuario> usuariosList = null;
	private Usuario usrAsoc = null;
//	private ArrayList<DatoOperacion> comboOperaciones = null;
//	private ArrayList<DatoActo> comboSubOperaciones = null;
//	private ArrayList<DatoActoDocumento> comboPrevios = null;
//	private ArrayList<DatoActoDocumento> comboPosteriores = null;
	
	public String getIdacto() {
		return idacto;
	}
	public void setIdacto(String idacto) {
		this.idacto = idacto;
	}
	public Compareciente getCompareciente() {
		return compareciente;
	}
	public void setCompareciente(Compareciente compareciente) {
		this.compareciente = compareciente;
	}
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
	public ArrayList<Expediente> getLista() {
		return lista;
	}
	public void setLista(ArrayList<Expediente> lista) {
		this.lista = lista;
	}
	
	public DatoOperacion getOperacion() {
		return operacion;
	}
	public void setOperacion(DatoOperacion operacion) {
		this.operacion = operacion;
	}
	public String getIdOperacion() {
		return idOperacion;
	}
	public void setIdOperacion(String idOperacion) {
		this.idOperacion = idOperacion;
	}
	public ArrayList<DatoOperacion> getListaOperaciones() {
		return listaOperaciones;
	}
	public void setListaOperaciones(ArrayList<DatoOperacion> listaOperaciones) {
		this.listaOperaciones = listaOperaciones;
	}
	public Persona getPersona() {
		return persona;
	}
	public void setPersona(Persona persona) {
		this.persona = persona;
	}
	public ArrayList<Persona> getListaPersona() {
		return listaPersona;
	}
	public void setListaPersona(ArrayList<Persona> listaPersona) {
		this.listaPersona = listaPersona;
	}
//	public ArrayList<DatoOperacion> getComboOperaciones() {
//		return comboOperaciones;
//	}
//	public void setComboOperaciones(ArrayList<DatoOperacion> comboOperaciones) {
//		this.comboOperaciones = comboOperaciones;
//	}
//	public ArrayList<DatoActo> getComboSubOperaciones() {
//		return comboSubOperaciones;
//	}
//	public void setComboSubOperaciones(ArrayList<DatoActo> comboSubOperaciones) {
//		this.comboSubOperaciones = comboSubOperaciones;
//	}
//	public ArrayList<DatoActoDocumento> getComboPrevios() {
//		return comboPrevios;
//	}
//	public void setComboPrevios(ArrayList<DatoActoDocumento> comboPrevios) {
//		this.comboPrevios = comboPrevios;
//	}
//	public ArrayList<DatoActoDocumento> getComboPosteriores() {
//		return comboPosteriores;
//	}
//	public void setComboPosteriores(ArrayList<DatoActoDocumento> comboPosteriores) {
//		this.comboPosteriores = comboPosteriores;
//	}
	public List<Usuario> getUsuariosList() {
		return usuariosList;
	}
	public void setUsuariosList(List<Usuario> usuariosList) {
		this.usuariosList = usuariosList;
	}
	public Usuario getUsrAsoc() {
		return usrAsoc;
	}
	public void setUsrAsoc(Usuario usrAsoc) {
		this.usrAsoc = usrAsoc;
	}
	
	
	
}
