package com.palestra.notaria.uif.services;

import java.io.InputStream;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.RegistroRi;
import com.palestra.notaria.uif.envio.UifEnvio;
import com.palestra.notaria.uif.models.Uif;
import com.palestra.notaria.uif.utils.NotariaUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;
import com.palestra.notaria.uif.dao.PersonaDao;
import com.palestra.notaria.uif.dao.PersonaDaoImp;
import com.palestra.notaria.uif.dao.UifDao;
import com.palestra.notaria.uif.dao.UifDaoImp;

@Path("/notarias-uif")
public class UifService {
	
	PersonaDao personadao;
	UifDao uifdao;
	
	public UifService() {
	
		personadao = new PersonaDaoImp();
		uifdao = new UifDaoImp();
		// TODO Auto-generated constructor stub
	}
	
	
	@GET
	@Path("/listar")
	//LISTAR
	@Produces(MediaType.APPLICATION_JSON)
	public UifEnvio getPersona(UifEnvio page){
		UifEnvio respuesta = new UifEnvio();
			try {
				List<Uif> uifs = uifdao.findAll();
			} catch (NotariaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Page<Uif> uifs = getPersonas(page);
			return respuesta;
	}
	

	
	@POST
	@Path("/saveFile")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	@Produces(MediaType.APPLICATION_JSON)
	public UifEnvio saveFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("file") FormDataBodyPart body,
			@FormDataParam("idsesionactual") String idsesionactual,
			@FormDataParam("idusuario") String idusuario,
			@FormDataParam("idactodoc") String idactodoc,
			@FormDataParam("desde") String desde
			){
		
				return null;
		
	}
	
	
	@GET
	@Path("/file/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarDocumento(@QueryParam("id") String id,
			@QueryParam("idusuario") String idusuario,
			@QueryParam("idsesionactual") String idsesionactual,
			@QueryParam("tipo") String tipo, @Context ServletContext context,
			@Context HttpServletResponse response) {

		if (idsesionactual == null || idsesionactual.isEmpty()
				|| idusuario == null || idusuario.isEmpty() || id == null
				|| id.isEmpty()) {

			Response.ok(Constantes.ESTATUS_FALTAN_PARAMETROS).build();
		}

			RegistroRi regRi = null;
			ActoDocumento actoDoc = null;
			String archivo;
			try {
					archivo = "";
					if(archivo !=null){
						NotariaUtils.recuperaDoc(archivo, response);
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}

		
		return null;
	}
	
	
	
	
}
