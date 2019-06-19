package com.palestra.notaria.resource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ComentarioBo;
import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.impl.ActoBoImpl;
import com.palestra.notaria.bo.impl.ComentarioBoImpl;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.ReporteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.util.NotariaUtils;

import pojos.pojos.TareaBean;
import utiles.utilidades.victor.BotMail;

@Path("/reportes")
public class ReportesRest {

	static Logger logger = Logger.getLogger(ReportesRest.class);
	public static final String LAST_DATE_QUERY="/devpal/notaria/.config/ultima-consulta-expj";
	
	@POST
	@Path("/enviarReporte")
	@Produces({ "application/json", "application/xml" })
	public ReporteEnvio enviarReporte(){
		ReporteEnvio respuesta = new ReporteEnvio();
		String lastDateStr = ""; 
		Date hoy = new Date();
		Date lastDateQuery=null;
	    try {
	    	if(Files.exists(Paths.get(LAST_DATE_QUERY))){
	    		lastDateStr = new String(Files.readAllBytes(Paths.get(LAST_DATE_QUERY)));
	    		lastDateQuery = new Date(Long.valueOf(lastDateStr));
	    	}else{
	    		lastDateQuery = new Date(86400000L);
	    	}
//	    	obtener expedientes por abogado
    		ExpedienteBo expBo = new ExpedienteBoImpl();
    		String idabogado= NotariaUtils.getProperties("bonita.reporte.idabogado");
    		List<Expediente> expedientes =expBo.obtenerExpedienteXAbogado(idabogado);
//	    		obtener actos para obtener solo los de exp judicial
    		ActoBo actoBo = new ActoBoImpl();
    		List<Acto> actosExp = new ArrayList<Acto>();
    		StringBuilder mailText = new StringBuilder();
			mailText.append("<h2> Reporte de estatus Expedientes Judiciales </h2>");
			mailText.append("<br> <br>");
    		for(Expediente exp:expedientes){
    			actosExp = actoBo.filterActoByIdExpediente(exp.getIdexpediente());
    			if(!actosExp.isEmpty()){
	    			if(actosExp.get(0).getSuboperacion().getOperacion().getDsnombre().equals("Expediente Judicial")){
//		    				agregar texto correo
	    				mailText.append("<h3 style='color:blue'>Expediente Judicial: "+exp.getDsreferencia()+"</h3>");
//		    			obtener los comentarios 
	    				ComentarioBo commentBo = new ComentarioBoImpl();
	    				List<Comentario> comments = commentBo.getByDatesAndObject(exp.getIdexpediente(), lastDateQuery, hoy);
	    				if(comments != null && !comments.isEmpty()){
		    				for(Comentario comment:comments){
		    					mailText.append(comment.getDstexto()+"<br>");
		    					mailText.append("Por: "+comment.getUsuario().getDsnombre()+" "+
		    								comment.getUsuario().getDspaterno()+"<br>");
		    					mailText.append("El: "+comment.getTmstmp()+"<br><br>");
		    					
		    				}
	    				}else{
	    					mailText.append("Sin actualizaciones <br><br>");
	    				}
	    				
	    			}
    			}
    		}
//    		String emails = NotariaUtils.getProperties("expjudicial.emails");
    		BotMail.enviaMailHTML("Reporte actualizaciones de Exp Judiciales", mailText.toString(), "mmarquez@notarias98y24.com.mx,omar_tablas@hotmail.com");
//	    		actualiza fecha de consulta
    		Long fechaLong = hoy.getTime();
    		String fechastr = String.valueOf(fechaLong);
    		byte data[] =fechastr.getBytes();
			java.nio.file.Path file = Paths.get(LAST_DATE_QUERY);
			try {
				Files.write(file, data,StandardOpenOption.CREATE);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (NotariaException e) {
			e.printStackTrace();
			respuesta.setExito(false);
			respuesta.setEstatus(e.getMessage());
			return respuesta;
		} catch (IOException e) {
			e.printStackTrace();
			respuesta.setExito(false);
			respuesta.setEstatus(e.getMessage());
			return respuesta;
		}
	    respuesta.setExito(true);
		respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
		return respuesta;
	}
	
	
	@POST
	@Path("/reporteCliente")
	@Produces({ "application/json", "application/xml" })
	public ReporteEnvio reporteCliente(TareaBean tareaBean){
		ReporteEnvio respuesta = new ReporteEnvio();
		ActoBo actoBo = new ActoBoImpl();
		ExpedienteBo expedienteBo = new ExpedienteBoImpl();
		ComentarioBo commentBo = new ComentarioBoImpl();
		TramiteBo tramiteBo = new TramiteBoImpl();
		
		try {
			String idExpediente = actoBo.getExpedienteIdByActoId(tareaBean.getIdacto());
//			String idExpediente = actoBo.getExpedienteIdByActoId("6da6a633da3cd890ad73dbc8a7d06b7d");
			Expediente exp = new Expediente();
			exp.setIdexpediente(idExpediente);
			Tramite tramite = tramiteBo.buscarPorExpediente(exp);
			Expediente expFound = expedienteBo.buscarPorIdCompleto(exp);
			List<Comentario> commentList = commentBo.buscarPorObjeto(idExpediente);
			StringBuilder mailText = new StringBuilder();
			mailText.append("<h2> Reporte de estatus Expediente Judicial: "+expFound.getDsreferencia() +" </h2> <br><br>");
			if(commentList !=null && !commentList.isEmpty()){
				for(Comentario comment:commentList){
					mailText.append(comment.getDstexto()+"<br>");
					mailText.append("Por: "+comment.getUsuario().getDsnombre()+" "+
								comment.getUsuario().getDspaterno()+"<br>");
					mailText.append("El: "+comment.getTmstmp()+"<br><br>");
					
				}
			}else{
				mailText.append("Sin actualizaciones por el momento");
			}
			BotMail.enviaMailHTML("Reporte actualizaciones de Expediente: "+expFound.getDsreferencia(), mailText.toString(), tramite.getCliente().getEmails());
			respuesta.setExito(true);
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			return respuesta;
		} catch (NotariaException e) {
			e.printStackTrace();
			respuesta.setExito(false);
			respuesta.setEstatus(e.getMessage());
			return respuesta;
		}
		
	}
	public static void main(String[]args) throws ParseException{
//		String datas = ""; 
//	    try {
//	    	if(Files.exists(Paths.get(LAST_DATE_QUERY))){
//	    		datas = new String(Files.readAllBytes(Paths.get(LAST_DATE_QUERY)));
//	    		Date fecha = new Date(Long.valueOf(datas));
////	    		obtener expedientes por abogado
//	    		ExpedienteBo expBo = new ExpedienteBoImpl();
//	    		List<Expediente> expedientes =expBo.obtenerExpedienteXAbogado("fc2d98aadbfbca1d304e1d56ae894fbb");
////	    		obtener actos para obtener solo los de exp judicial
//	    		ActoBo actoBo = new ActoBoImpl();
//	    		List<List<Acto>> arrayActos = new ArrayList<List<Acto>>();
//	    		List<Acto> actosExp = new ArrayList<Acto>();
//	    		for(Expediente exp:expedientes){
//	    			actosExp = actoBo.filterActoByIdExpediente(exp.getIdexpediente());
//	    			if(actosExp.get(0).getSuboperacion().getOperacion().getDsnombre().equals("Expediente Judicial")){
//	    				arrayActos.add(actosExp);
//	    			}
//	    		}
//	    		for(List<Acto> actos:arrayActos){
//	    			for(Acto acto:actos){
//	    				System.out.println(acto.getExpediente().getNumexpediente());
//	    			}
//	    		}
//	    	}else{
////	    		si no existe archivo se devuelve todo
//	    		
//	    	}
//			
//			System.out.println(datas); 
//		} catch (NotariaException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	    
//		Date inicio = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2017-09-24 17:36:10");
//		Long time = inicio.getTime();
//		String timestr = String.valueOf(time);
//		
//		byte data[] =timestr.getBytes();
//				java.nio.file.Path file = Paths.get("/devpal/notaria/.config/ultima-consulta-expj");
//				try {
//					Files.write(file, data,StandardOpenOption.CREATE);
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		System.out.println(new Date(86400000L));
 

	}
}
