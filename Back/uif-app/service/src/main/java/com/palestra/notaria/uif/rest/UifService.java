package com.palestra.notaria.uif.rest;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FilenameUtils;

import com.palestra.notaria.uif.core.dao.PersonaDao;
import com.palestra.notaria.uif.core.dao.PersonaDaoImp;
import com.palestra.notaria.uif.core.dao.UifDao;
import com.palestra.notaria.uif.core.dao.UifDaoImp;
import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.core.models.Uif;
import com.palestra.notaria.uif.core.utiles.Constantes;
import com.palestra.notaria.uif.core.utiles.GeneradorId;
import com.palestra.notaria.uif.envio.UifEnvio;
import com.palestra.notaria.uif.exceptions.NotariaException;
import com.palestra.notaria.uif.utils.NotariaUtils;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

@Path("/v1")
public class UifService {
	
	PersonaDao personadao;
	UifDao uifdao;
	private final String rootLocation = "/devpal/notaria/uif";

	public UifService() {
		personadao = new PersonaDaoImp();
		uifdao = new UifDaoImp();
	}
	
	@GET
	@Path("/{page}")
	@Produces({ "application/json"})
	public UifEnvio listar(@PathParam("page")Long page){
		UifEnvio respuesta = new UifEnvio();
			try {
				if(page<0){
					page = 0L;
				}
				
					loadData(respuesta, page);
				} catch (NotariaException e) {
					respuesta.setExito(false);
					respuesta.setStatus(Constantes.ESTATUS_ERROR_LISTAR);
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
			@FormDataParam("file") InputStream file,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("id")Long id,
			@FormDataParam("page")Integer page,
			@FormDataParam("idpersona")String idpersona,
			@FormDataParam("dsnombre") String nombres,
			@FormDataParam("dsapellidopat") String paterno,
			@FormDataParam("dsapellidomat") String materno,
			@FormDataParam("dsrfc") String rfc,
			@FormDataParam("dscurp") String curp,
			@FormDataParam("acto") String acto,
			@FormDataParam("tipocompareciente") String tipocompareciente,
			@FormDataParam("escritura") Long escritura,
			@FormDataParam("fecha") String fecha,
			@FormDataParam("archivo") String archivo,
			@FormDataParam("password") String password
			){
		
		
		
		
		UifEnvio respuesta = new UifEnvio();
		
		
		String syspassword="";
		try {
			syspassword = NotariaUtils.getProperties("uif.password");
		} catch (NotariaException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if(!syspassword.equals(password)){
			respuesta.setStatus("Contraseña incorrecta, notifique a sistemas");
			respuesta.setExito(false);
			return respuesta;
		}
		
		
		
		String urlfile = null;
		Date tmpfecha = new Date(Long.parseLong(fecha,10));
		
		if(paterno!=null && paterno.equals("null")) paterno = null;
		
		if(materno != null && materno.equals("null")) materno = null;
		
		if(curp != null && curp.equals("null"))curp = null;
	
		if(acto != null && acto.equals("null")) acto = null;
		
		if(tipocompareciente != null && tipocompareciente.equals("null")) tipocompareciente = null;
			
		if(archivo != null && archivo.equals("null")) archivo = null;
	
		PersonaDao personaDao = new PersonaDaoImp();
		
		
		boolean actualizauif = false;
		boolean actualizatodo = false;
		Persona persona = new Persona(nombres, paterno, materno);
		
		if(idpersona != null && id!=null){
			actualizatodo=true;
		}
		
		if(idpersona!=null && id==null){
			actualizauif = true;
		}
		
		if(actualizatodo){
			try {
				persona = personadao.findById(idpersona);
			} catch (NotariaException e) {
				e.printStackTrace();
			}
			persona.setDsnombre(nombres);
			persona.setDsapellidopat(paterno);
			persona.setDsapellidomat(materno);
			StringBuilder nombrecompleto = new StringBuilder(nombres);
			if(paterno!=null){
				nombrecompleto.append(" ");
				nombrecompleto.append(paterno);
			}
			if(materno!=null){
				nombrecompleto.append(" ");
				nombrecompleto.append(materno);
			}
			
			persona.setDsnombrecompleto(nombrecompleto.toString().trim());
		}
		
		persona.setDsrfc(rfc);
		persona.setDscurp(curp);
		persona.setIdpersona((idpersona!=null)?idpersona:GeneradorId.generaId(persona));
		
		
		if(file!=null){

			if(rfc==null){
				respuesta.setExito(false);
				respuesta.setStatus("El RFC es requerido para continuar con el proceso");
				return respuesta;
			}
	    	SimpleDateFormat ft = new SimpleDateFormat ("yyyy_MM_dd");
	    	StringBuilder filename = new StringBuilder();
	    	filename.append(escritura);
	        filename.append("_"+ft.format(new Date()));
	        filename.append(".");
	        filename.append(FilenameUtils.getExtension(fileDetail.getFileName()));
			
			String uploadedFileLocationExp = rootLocation + File.separator +rfc+ File.separator + filename.toString();
			boolean bexp = NotariaUtils.writeToFile(file, uploadedFileLocationExp);
			if(!bexp){
				respuesta.setExito(false);
				respuesta.setStatus(Constantes.ESTATUS_ERROR_SUBIR_DOC);
			}else{
				urlfile = rfc+ File.separator + filename.toString();
			}
		}
		
		Uif uif = new Uif();
		uif.setId(id);
		if(file==null && archivo!=null){
			urlfile = archivo;
		}
		uif.setArchivo(urlfile);
		uif.setActo(acto);
		uif.setTipocompareciente(tipocompareciente);
		uif.setEscritura(escritura);
		uif.setFecha(tmpfecha);
		
		if(actualizatodo){			
			try {
				personadao.update(persona);
				uif.setIdpersona(persona);
				uifdao.update(uif);
			} catch (NotariaException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			
			if(actualizauif){
				uif.setIdpersona(persona);
				try {
					uifdao.save(uif);
				} catch (NotariaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{
				persona.addUif(uif);
				try {
					personaDao.save(persona);
				} catch (NotariaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		

		
		try {
			loadData(respuesta, page);
		} catch (NotariaException e) {
			respuesta.setExito(false);
			respuesta.setStatus(Constantes.ESTATUS_ERROR_LISTAR);
			e.printStackTrace();
		}

		
		
		return respuesta;
		//return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.OK);
		

		
	}
	
	
	@GET
	@Path("/file/{id}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public Response descargarDocumento(@PathParam("id") Long id,
			@Context ServletContext context,
			@Context HttpServletResponse response) {
					
			try {	
				
					Uif uif = uifdao.findById(id);
				
					if(uif !=null && uif.getArchivo()!=null){
						NotariaUtils.recuperaDoc(rootLocation+File.separator+uif.getArchivo(), response);
					}
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		return null;
	}
	
	
	// DELETE
		@DELETE
		@Path(value="/{page}/{password}/{id}")
		@Produces({ "application/json"})
		public UifEnvio delete(@PathParam("id") Long id,@PathParam("page")Long page,@PathParam("password") String password){
			UifEnvio respuesta = new UifEnvio();

		
			String syspassword="";
			try {
				syspassword = NotariaUtils.getProperties("uif.password");
			} catch (NotariaException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(!syspassword.equals(password)){
				respuesta.setStatus("Contraseña incorrecta, notifique a sistemas");
				respuesta.setExito(false);
				return respuesta;
			}
			
			
			
				try {
					Uif uif = uifdao.findById(id);
					if(uif!=null){
						if(uif.getArchivo()!=null){
							NotariaUtils.eliminarArchivo(rootLocation+File.separator+uif.getArchivo());
						}
						uifdao.borrar(uif);	
					}
					
				} catch (NotariaException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					respuesta.setExito(false);
					respuesta.setStatus(Constantes.ESTATUS_ERROR_ELIMINAR);
				}
				
				try {
					loadData(respuesta, page);
				} catch (NotariaException e) {
					respuesta.setExito(false);
					respuesta.setStatus(Constantes.ESTATUS_ERROR_LISTAR);
					e.printStackTrace();
				}
				
				return respuesta;
				
		}
		
		
		// BUSCAR
		@POST
		@Path(value="/buscar")
		@Produces({ "application/json"})
		@Consumes({ "application/json"})
		public UifEnvio buscar(UifEnvio personaenvio){
			UifEnvio respuesta = new UifEnvio();
			String parametro = personaenvio.getParametrobusqueda();
			String tipo = personaenvio.getTipobusqueda();
			try {
				List<Uif> listaresp = uifdao.buscar(tipo,parametro);
				respuesta.setContent(listaresp);
				respuesta.setNumberOfElements( new Long(listaresp.size()));
			} catch (NotariaException e) {
				respuesta.setExito(false);
				respuesta.setStatus(Constantes.ESTATUS_ERROR_LISTAR);
				e.printStackTrace();
			}
			
				return respuesta;
			}
		
		
		
	
	// LOAD ENVIO
		private UifEnvio loadData(UifEnvio ue,long page) throws NotariaException{
			List<Uif> uifs = uifdao.buscarXPagina(page);
			Long tpages= uifdao.getTotalPage();
			ue.setTotalpages(tpages);
			ue.setNumber(page);
			
			if(page==0){
				ue.setFirst(true);
			}else if(page==tpages-1){
				ue.setLast(true);
			}
			
			ue.setPage(page);
			ue.setContent(uifs);
			ue.setNumberOfElements(new Long(uifs.size()));
			return ue;
		}
	
		
		
	
	
}
