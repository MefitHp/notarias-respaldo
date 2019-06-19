package com.palestra.notaria.resource;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ManejoSesionBo;
import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.bo.impl.ManejoSesionBoImpl;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ManejoSesion;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.NotariaUtils;

@Path("/")
public class LoginRest {

	static Logger logger = Logger.getLogger(LoginRest.class);

	private UsuarioBO usuarioBo;

	private ManejoSesionBo manejoSesionBo;

	public LoginRest(){
		 usuarioBo = new UsuarioBoImpl();
		 manejoSesionBo = new ManejoSesionBoImpl();
	}

	@POST
	@Path("/login")
	@Produces({ "application/json", "application/xml" })
	public Response autenticaUsuario(Usuario usuario,@Context HttpServletResponse response,@Context HttpServletRequest request) throws IOException {

		Usuario usuarioLogueado = null;
		try{
			usuario.setDsvalenc(NotariaUtils.getStringMessageDigest(usuario.getDsvalenc()));
			usuarioLogueado = getUsuarioBo().authenticate(usuario.getCdusuario(), usuario.getDsvalenc());
			ManejoSesion ms = null;
	//		TODO: buscar si el idusuario encontrado tiene una sesion anterior activa y cerrarla
			if(usuarioLogueado !=null){
				
				//List<ManejoSesion> msList = getManejoSesionBo().findSesionByUser(usuarioLogueado.getIdusuario());
				//if(msList.size()>0){
				//	ms = msList.get(0);
				//	ms.setIslogout(Boolean.FALSE);
				//	ms = getManejoSesionBo().update(ms);
				//	msList.remove(0);
//					if(msList.size()>0){
				//		for(ManejoSesion tmpms:msList){
				//			getManejoSesionBo().borrar(tmpms);
//				//		}
				//	}
						
				//}else{
					ms = new ManejoSesion();
					ms.setIdsesion(request.getSession().getId());
					ms.setIdusuario(usuarioLogueado.getIdusuario());
					ms.setIslogout(Boolean.FALSE);
					ms.setInip(request.getRemoteAddr());
					ms = getManejoSesionBo().save(ms);
				//	}
				
				
				Date date= new Date();
				usuarioLogueado.setDsultimoacceso(new Timestamp(date.getTime()));
				usuarioLogueado = getUsuarioBo().update(usuarioLogueado);
				usuarioLogueado.setIdsesionactual(ms.getIdsesion());						
				
				if(usuarioLogueado.getRol().getDsnombre().equals("Administrador")){
					usuarioLogueado.setAcceso("_adm");
				}else{
					usuarioLogueado.setAcceso("");
				}
				return Response.ok(usuarioLogueado).build();
			}else{
				return Response.status(Status.UNAUTHORIZED).build();
			}
		}catch(Exception e){
			e.printStackTrace(System.out);
//			response.sendError(401, "Credenciales erroneas");
			return Response.serverError().build();
		}
//			return Response.status(201).entity(usuarioResult).build();
//		return usuarioLogueado;
	}
	

	@POST
	@Path("/logout")
	@Produces({ "application/json", "application/xml" })
	public boolean terminaSesion(Usuario usuario,@Context HttpServletResponse response,@Context HttpServletRequest request) {
		ManejoSesion ms = new ManejoSesion();
//		ms.setIdsesion(usuario.getIdsesionactual());
//		ms.setIdusuario(usuario.getIdusuario());
//		ms.setIslogout(Boolean.TRUE);
		try {
			ms = getManejoSesionBo().findBySesionAndUser(usuario.getIdsesionactual(), usuario.getIdusuario());
			Date date= new Date();
			ms.setFchtermino(new Timestamp(date.getTime()));
			ms.setIslogout(true);
			
			ms = getManejoSesionBo().update(ms);
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ms == null)
			return false;

		return true;
	}
		
	public UsuarioBO getUsuarioBo() {
		return usuarioBo;
	}

	public void setUsuarioBo(UsuarioBO usuarioBo) {
		this.usuarioBo = usuarioBo;
	}

	public ManejoSesionBo getManejoSesionBo() {
		return manejoSesionBo;
	}

	public void setManejoSesionBo(ManejoSesionBo manejoSesionBo) {
		this.manejoSesionBo = manejoSesionBo;
	}
}
