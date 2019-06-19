package com.palestra.notaria.servicios;

import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class Tester {
	
	public static void main (String [] args){
		UsuarioBO usuarioBo = new UsuarioBoImpl();
		Usuario user = new Usuario();
		user.setCdusuario("usuario");
		user.setDscorreo("user@user.com");
		user.setDsmaterno("castillo");
		user.setDsnombre("alan");
		user.setDspaterno("sanchez");
		user.setDsreferencia("ref");
		user.setDsrfc("asdfasdfsa");
		user.setDsvalenc("pass");
		user.setIdsesion("asfasd");
//		---------------------------------------------
		user.setIdusuario(GeneradorId.generaId(user));
		user.setIsactualizapwd(false);
		user.setInestatus(true);
		user.setIsactivo(true);
		Rol rol = new Rol();
		rol.setIdrol("1");
		user.setRol(rol);
		System.out.println("objeto antes de guardar: "+user);
		try {
			System.out.println("objeto guardado: "+usuarioBo.save(user));
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println(usuarioBo.findById("a605ffcf636b38c0b98352d797952fb4").getCdusuario());
//		System.out.println(usuarioBo.authenticate(user.getCdusuario(), user.getDsvalenc()));
		
	}

}
