package com.palestra.notaria.envio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.modelo.Colonia;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Expediente;

public class ComentarioEnvio extends GenericEnvio implements Serializable{

	private static final long serialVersionUID = 7253244558812530629L;
	
	private List<Comentario> comentarios = null;
	private Comentario comentario = null;
	private Expediente expediente = null;
	private Boolean setBitacora = null;
	
	public List<Comentario> getComentarios() {
		return comentarios;
	}
	public void setComentarios(List<Comentario> comentarios) {
		this.comentarios = comentarios;
	}
	
	public Comentario getComentario() {
		return comentario;
	}
	public void setComentario(Comentario comentario) {
		this.comentario = comentario;
	}
	public Expediente getExpediente() {
		return expediente;
	}
	public void setExpediente(Expediente expediente) {
		this.expediente = expediente;
	}
	
	public Boolean getSetBitacora() {
		return setBitacora;
	}
	public void setSetBitacora(Boolean setBitacora) {
		this.setBitacora = setBitacora;
	}
	
	

}
