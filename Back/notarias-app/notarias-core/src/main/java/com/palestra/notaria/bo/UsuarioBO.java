package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;

public interface UsuarioBO extends GenericBo<Usuario> {

	public Usuario authenticate(String user, String password) throws NotariaException;
	
	public List<Usuario> listarPorRol(Rol rol) throws NotariaException;
	
//	public Usuario save(Usuario usuario);
	
	public List<Usuario> findAll() throws NotariaException;
	
	public String buscarIniciales(String idUsuario);
	
	public Usuario findById(String idusuario) throws NotariaException;

	UsuarioRestaurar peticionRestauraContrasenia(String correo) throws NotariaException;

	UsuarioRestaurar reseteaContrasenia(String correo, String token)
			throws NotariaException;

	Usuario buscarUsuarioPorCorreo(String correo) throws NotariaException;

	Usuario actualizaContrasenia(Usuario usuario, String token)
			throws NotariaException;

	Integer eliminaPeticion(UsuarioRestaurar peticion) throws NotariaException;

	List<Usuario> listarParaAsignar() throws NotariaException;

	

}
