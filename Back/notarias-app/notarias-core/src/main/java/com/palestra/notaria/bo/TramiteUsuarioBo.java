package com.palestra.notaria.bo;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.TramiteUsuario;
import com.palestra.notaria.modelo.Usuario;

public interface TramiteUsuarioBo{
	
	public ArrayList<Expediente> buscarExpedientesXTramiteUsuario(Usuario usuario) throws NotariaException;

	public List<Usuario> buscarUsuariosXtramite(String idTramite) throws NotariaException;

	public TramiteUsuario saveTramiteUsuario(Usuario u, Tramite t, String sesionActual)
			throws NotariaException;
	public void deleteTramiteUsuario(Usuario u,Tramite t,String sesionActual)throws NotariaException;
	
}
