package com.connect.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import com.connect.bo.AccesoArchivosRacooBo;
import com.connect.bo.ConsultaBo;
import com.connect.bo.exception.ConectividadBoException;
import com.connect.bo.impl.AccesoArchivosRacooBoImpl;
import com.connect.bo.impl.ConsultaBoImpl;
import com.connect.envio.ConsultaEnvio;
import com.connect.modelo.VistaExpediente;

@Path("/consultaExpedientes")
public class ConexionRest {
	
	@POST
	@Path("/buscar")
	@Produces(MediaType.APPLICATION_JSON)
	public ConsultaEnvio buscaExpedientes(VistaExpediente exp,@Context ServletConfig config){
		ConsultaBo consultaBo = new ConsultaBoImpl();
		ConsultaEnvio respuesta = new ConsultaEnvio();
		try {
			Integer resultadosPorPagina = Integer.valueOf(config.getInitParameter("resultadosPorPagina"));
			respuesta.setVistaExpedienteList(consultaBo.buscarExpedientes(exp,resultadosPorPagina));
			Long pags = consultaBo.obtenerTotalRegistrosBusqueda(exp)/resultadosPorPagina;
			respuesta.setTotalPags(pags);
			respuesta.setEstatus("Listado correcto");
			respuesta.setExito(true);
			return respuesta;
		} catch (ConectividadBoException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/buscarComparecientesPorExpediente")
	@Produces(MediaType.APPLICATION_JSON)
	public ConsultaEnvio buscarComparecientesPorExpediente(ConsultaEnvio ce){
		ConsultaBo consultaBo = new ConsultaBoImpl();
		ConsultaEnvio respuesta = new ConsultaEnvio();
		try {
			respuesta.setVistaExpediente(consultaBo.buscarComparecientesPorExpediente(ce.getNumExpediente()));
			respuesta.setEstatus("Busqueda correcta");
			respuesta.setExito(true);
			return respuesta;
		}catch (ConectividadBoException e) {
			e.printStackTrace(System.out);
			respuesta.setEstatus(e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/listarArchivosExpediente")
	@Produces(MediaType.APPLICATION_JSON)
	public ConsultaEnvio listarArchivos(ConsultaEnvio ce, @Context ServletConfig config){
		AccesoArchivosRacooBo accesoRacooBo = new AccesoArchivosRacooBoImpl();
		ConsultaEnvio respuesta = new ConsultaEnvio();
		try {
			respuesta.setArchivosList(accesoRacooBo.listaArchivos(ce.getCarpeta(),config.getInitParameter("rutaRacoo")));
			respuesta.setEstatus("Listado correcto");
			respuesta.setExito(true);
			return respuesta;
		} catch (IOException e) {
			e.printStackTrace();
			respuesta.setEstatus("error: "+e.getMessage());
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@GET
	@Path("{carpeta}/{nombreArchivo}")
	@Produces(MediaType.APPLICATION_OCTET_STREAM)
	public void mostrarArchivo(@PathParam("nombreArchivo") String nombreArchivo,
			@PathParam("carpeta")String carpeta, @Context ServletConfig config,
			@Context HttpServletResponse response){
		try {
			recuperaArchivo(nombreArchivo,carpeta,response,config);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			response.setStatus(410);
			e.printStackTrace();
		}
	}

	
	public void recuperaArchivo(String nombreArchivo,String carpeta,HttpServletResponse response, ServletConfig config) throws IOException {
		String[] elements = carpeta.split("_");
		String estructura = "";
		for(int i=0;i<elements.length;i++){
			estructura = estructura.concat("/").concat(elements[i]);
		}
		File archivo = new File(config.getInitParameter("rutaRacoo")+estructura+"/"+nombreArchivo);
			
		FileInputStream in = new FileInputStream(archivo);
		response.setContentLength((int)archivo.length());
		OutputStream out = response.getOutputStream();
		byte[] buf = new byte[Integer.valueOf(String.valueOf(archivo.length()))];
		int len = 0;
		while ((len = in.read(buf)) >= 0)
			{
				out.write(buf, 0, len);
			}
				in.close();
				out.close();
		
	}
}
