package com.palestra.notaria.dao;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioRestaurar;

public interface UsuarioDao extends GenericDao<Usuario, Integer>{
	
	public Usuario authenticate(String user, String password) throws NotariaException;
	
//	public Usuario save(Usuario usuario) throws NotariaException;
	
	public List<Usuario> listarPorRol(Rol rol) throws NotariaException;
	
//	public Usuario update(Usuario usuario)throws NotariaException;
	
	public List<Usuario> findAll() throws NotariaException;
	
	public String buscarIniciales(String idUsuario) throws NotariaException;

	Usuario findByIniciales(String iniciales)throws NotariaException;

	List<Usuario> buscaPorCorreo(String correo) throws NotariaException;

	List<UsuarioRestaurar> buscaPeticionesActivasPorUsuario(Usuario usuario)
			throws NotariaException;

	List<UsuarioRestaurar> buscaPeticionActivaPorCorreo(String correo)
			throws NotariaException;

	
//	public Usuario findById(String idusuario)throws NotariaException; lo mande a la chingada de la impl

}
