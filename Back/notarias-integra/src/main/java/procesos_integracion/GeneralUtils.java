package procesos_integracion;

import java.util.Calendar;
import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.palestra.notaria.envio.ActoEnvio;
import com.palestra.notaria.envio.BonitaBPMEnvio;
import com.palestra.notaria.envio.EscrituraEnvio;
import com.palestra.notaria.envio.ReporteEnvio;
import com.victor.utils.restconexion.PostGet;

import pojos.pojos.BonitaCommonBean;
import pojos.pojos.TareaBean;

public class GeneralUtils {

	private String urlRestActos = "http://localhost:9090/notarias-web/notaria/actos";
	private String urlRestEscritura = "http://localhost:9090/notarias-web/notaria/escritura";
	private String urlRestBonitaBPM = "http://localhost:9090/notarias-web/notaria/bonitaBPM";
	private String urlRestReportes = "http://localhost:9090/notarias-web/notaria/reportes";
	private String urlSaveTaskName = "/saveTaskName";
	private String urlValidaFechasHabiles = "/validaFechasHabiles";
	private String urlSave="/saveBonitaProcess";
	private String urlTarea="/saveTask";
	private String urlSaveNumEscritura = "/saveNumEscrituraBonita";
//	private String urlTestTask = "/testTaskName";
	private String reporteUpdates = "/enviarReporte";
	private String reporteCliente = "/reporteCliente";
//	private String urlsetIdTarea="/actualizaTareaBo";
	
	public String sendUpdateReport(){
		PostGet pg = new PostGet(urlRestReportes + reporteUpdates);
		JsonNode respuesta = pg.getPost(null);
		ReporteEnvio reporteEnvio = new ReporteEnvio();
		reporteEnvio = (ReporteEnvio) PostGet.setMapperJsonToPojo(respuesta, ReporteEnvio.class);

		if(reporteEnvio.isExito()){
			return "ok";
		}else{
			return "error";
		}
	}
	
	public Date validateHolidays(int days,Calendar time){
		
		BonitaCommonBean bc = new BonitaCommonBean();
		bc.setCalendar(time);
		bc.setDias(days);
		PostGet pg = new PostGet(urlRestBonitaBPM + urlValidaFechasHabiles);
		
		JsonNode respuesta = pg.getPost(bc);
		BonitaBPMEnvio bpmEnvio = new BonitaBPMEnvio();
		bpmEnvio = (BonitaBPMEnvio)PostGet.setMapperJsonToPojo(respuesta,BonitaBPMEnvio.class);
		if(bpmEnvio.isExito()){
			return bpmEnvio.getBonitaCommonBean().getCalendar().getTime();
		}else
			return null;
	}
	
	public String sendClientUpdates(String idacto){
		TareaBean tareaBean = new TareaBean();
		tareaBean.setIdacto(idacto);
		PostGet pg = new PostGet(urlRestReportes + reporteCliente);
		JsonNode respuesta = pg.getPost(tareaBean);
		ReporteEnvio reporteEnvio = new ReporteEnvio();
		reporteEnvio = (ReporteEnvio) PostGet.setMapperJsonToPojo(respuesta, ReporteEnvio.class);

		if(reporteEnvio.isExito()){
			return "ok";
		}else{
			return "error";
		}
	}
	public String saveTaskName(String idActo,Long processId, String idsesionactual, String idusuario, Long taskId, String taskName, String username){
		BonitaCommonBean comBean = new BonitaCommonBean();
		comBean.setIdActo(idActo);
		comBean.setIdproceso(processId);
		comBean.setIdsesionactual(idsesionactual);
		comBean.setIdusuario(idusuario);
		comBean.setIdtarea(taskId);
		comBean.setNombreTarea(taskName);
		comBean.setUsuario(username);
		PostGet pg = new PostGet(urlRestBonitaBPM + urlSaveTaskName);
		JsonNode respuesta = pg.getPost(comBean);
		BonitaBPMEnvio bpmEnvio = new BonitaBPMEnvio();
		bpmEnvio = (BonitaBPMEnvio)PostGet.setMapperJsonToPojo(respuesta,BonitaBPMEnvio.class);
		if(bpmEnvio.isExito()){
			return "ok";
			//return mesaenv.toString();
		}else{
			return "error";
		}
	}
	
	public String save(String idActo,Long processId, String idsesionactual, String idusuario, Long taskId, String taskName, String username){
		BonitaCommonBean comBean = new BonitaCommonBean();
		comBean.setIdActo(idActo);
		comBean.setIdproceso(processId);
		comBean.setIdsesionactual(idsesionactual);
		comBean.setIdusuario(idusuario);
		comBean.setIdtarea(taskId);
		comBean.setNombreTarea(taskName);
		comBean.setUsuario(username);
		PostGet pg = new PostGet(urlRestActos + urlSave);
		JsonNode respuesta = pg.getPost(comBean);
		ActoEnvio actoEnvio = new ActoEnvio();
		actoEnvio = (ActoEnvio) PostGet.setMapperJsonToPojo(respuesta,ActoEnvio.class);
		
		System.out.println(actoEnvio);
		
		if(actoEnvio.isExito()){
			return "ok";
			//return mesaenv.toString();
		}else{
			return "error";
		}
		
	}
	
	
	public String saveTask(String idproceso,String idacto,String idtarea,String nombretarea,String idusuario, String idsesionactual){
		
		TareaBean tareabean = new TareaBean();
		tareabean.setIdproceso(idproceso);
		tareabean.setIdacto(idacto);
		tareabean.setIdtarea(idtarea);
		tareabean.setNombretarea(nombretarea);
		tareabean.setIdsesionactual(idsesionactual);
		tareabean.setIdusuario(idusuario);
		PostGet pg = new PostGet(urlRestActos+urlTarea);
		JsonNode respuesta = pg.getPost(tareabean);
		ActoEnvio actoEnvio = new ActoEnvio();
		actoEnvio = (ActoEnvio) PostGet.setMapperJsonToPojo(respuesta, ActoEnvio.class);

		if(actoEnvio.isExito()){
			return "ok";
		}else{
			return "error";
		}
		
	}
	
	public String saveNumEscrituraBonita(Long idproceso,String idacto,Long idtarea,String idusuario, String idsesionactual){
		BonitaCommonBean comBean = new BonitaCommonBean();
		comBean.setIdActo(idacto);
		comBean.setIdproceso(idproceso);
		comBean.setIdsesionactual(idsesionactual);
		comBean.setIdusuario(idusuario);
		comBean.setIdtarea(idtarea);
//		comBean.setNombreTarea(taskName);
//		comBean.setUsuario(username);
		PostGet pg = new PostGet(urlRestEscritura + urlSaveNumEscritura);
		JsonNode respuesta = pg.getPost(comBean);
		EscrituraEnvio escrituraEnvio = new EscrituraEnvio();
		escrituraEnvio = (EscrituraEnvio) PostGet.setMapperJsonToPojo(respuesta,EscrituraEnvio.class);
		
		System.out.println(escrituraEnvio);
		
		if(escrituraEnvio.isExito()){
			return "ok";
			//return mesaenv.toString();
		}else{
			return "error";
		}
	}
	
}
