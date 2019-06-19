package com.palestra.notaria.bo;

import java.util.List;

import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.Usuario;

public interface BitacoraUsuarioBo extends GenericBo<BitacoraUsuario> {



	List<BitacoraUsuario> listarByGrupo(Usuario usu) throws NotariaException;

	BitacoraUsuario buscarXTarea(String idtarea) throws NotariaException;


}
