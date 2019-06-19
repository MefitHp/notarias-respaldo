package com.palestra.notaria.resource;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.fop.render.bitmap.BitmapRendererConfigurator;

import com.palestra.notaria.bo.BitacoraUsuarioBo;
import com.palestra.notaria.bo.GrupoTrabajoBo;
import com.palestra.notaria.bo.UsuarioGrupoTrabajoBo;
import com.palestra.notaria.bo.impl.BitacoraUsuarioBoImpl;
import com.palestra.notaria.bo.impl.GrupoTrabajoBoImp;
import com.palestra.notaria.bo.impl.UsuarioGrupoTrabajoBoImp;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.BitacoraUsuarioEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.BitacoraUsuario;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.modelo.UsuarioGrupoTrabajo;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/bitacorausuario")
public class BitacoraUsuarioRest {

	private BitacoraUsuarioBo bitusubo;
	
	public BitacoraUsuarioBo getBitusubo() {
		return bitusubo;
	}

	public BitacoraUsuarioRest(){
		bitusubo = new BitacoraUsuarioBoImpl();
	}
	
	@POST
	@Path("/save")
	@Produces(value=MediaType.APPLICATION_JSON)		
	public BitacoraUsuarioEnvio save(BitacoraUsuarioEnvio bitacora){
		BitacoraUsuarioEnvio respuesta = new BitacoraUsuarioEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(bitacora)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		/*if (NotariaUtils.isSesionValida(bitacora.getUsuario()
				.getIdsesionactual(), bitacora.getUsuario()
				.getIdusuario())) {
		*/	
			try {
				//String idusu = "454d49368b6c7965228152c510a4a83d";
				BitacoraUsuario bitusu = bitacora.getBitacorausuario();
				UsuarioGrupoTrabajoBo usugrupBo = new UsuarioGrupoTrabajoBoImp();
				UsuarioGrupoTrabajo usugrup = usugrupBo.buscarXusuario(bitacora.getUsuario().getIdusuario());
				StringBuilder idusugrp = new StringBuilder();

				if(usugrup !=null){
					idusugrp.append(usugrup.getGrupoTrabajo().getIdgrupotrabajo());
				}else{
					GrupoTrabajoBo gpoUsu = new GrupoTrabajoBoImp();
					Usuario usu = new Usuario();
					usu.setIdusuario(bitacora.getUsuario().getIdusuario());
					List<GrupoTrabajo> gpo = gpoUsu.buscarXresponsable(usu);
					if(gpo!= null && gpo.size()>0){
						idusugrp.append(gpo.get(0).getIdgrupotrabajo());
					}
				}
				/*bitusu.setIdacto("f117e06a176fc09275a656779d104231");
				bitusu.setIdobjeto("1e2332e5240258543de79c4bea36d906");
				bitusu.setStatus("WARNING");
				bitusu.setTexto("El documento esta proximo a vencer");
				bitusu.setTipo("DOCALERT");*/	
				bitusu.setIdbitusu(GeneradorId.generaId(bitusu));
				bitusu.setIdgrupotrabajo(idusugrp.toString());
				getBitusubo().save(bitusu);
				respuesta.setBitacorausuario(bitusu);
				return respuesta;
			
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_REGISTRO);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
		/*} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}*/
		
	}
		
	@POST
	@Path("/desactivar")
	@Produces(value=MediaType.APPLICATION_JSON)		
	public BitacoraUsuarioEnvio desactivar(BitacoraUsuarioEnvio bitacora){
		BitacoraUsuarioEnvio respuesta = new BitacoraUsuarioEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(bitacora)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(bitacora.getUsuario()
				.getIdsesionactual(), bitacora.getUsuario()
				.getIdusuario())) {
			
			try {
					BitacoraUsuario bitusu = getBitusubo().findById(bitacora.getBitacorausuario().getIdbitusu());
					bitusu.setActive(false);
					getBitusubo().update(bitusu);
					respuesta.setExito(true);
					respuesta.setEstatus(Constantes.ESTATUS_ACTUALIZACION_CORRECTA);
					return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	
	@POST
	@Path("/listar")
	@Produces(value=MediaType.APPLICATION_JSON)		
	public BitacoraUsuarioEnvio listar(BitacoraUsuarioEnvio bitacora){
		BitacoraUsuarioEnvio respuesta = new BitacoraUsuarioEnvio();
		if (NotariaUtils.faltanRequeridosUsuario(bitacora)) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}

		if (NotariaUtils.isSesionValida(bitacora.getUsuario()
				.getIdsesionactual(), bitacora.getUsuario()
				.getIdusuario())) {
			
			try {
					respuesta.setBitacoras(getBitusubo().listarByGrupo(bitacora.getUsuario()));
					respuesta.setEstatus(Constantes.ESTATUS_LISTADO_CORRECTO);
					respuesta.setExito(true);
					return respuesta;
			} catch (NotariaException e) {
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_LISTAR);
				respuesta.setExito(false);
				return respuesta;
			}
			
			
		} else {
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
		
	}
	
	
		
	

}
