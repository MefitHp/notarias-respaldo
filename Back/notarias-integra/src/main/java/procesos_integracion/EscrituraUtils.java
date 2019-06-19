package procesos_integracion;

import com.fasterxml.jackson.databind.JsonNode;
import com.palestra.notaria.envio.EscrituraEnvio;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Usuario;
import com.victor.utils.restconexion.PostGet;

import pojos.pojos.ProcesoComun;

public class EscrituraUtils {

	private String urlRestEscritura = "http://localhost:9090/notarias-web/notaria/escritura";
	private String urlnopaso=urlRestEscritura+"/nopaso"; 
	private String urlIdTarea = urlRestEscritura+"/setProcesoIdTarea";
	
	public void setIdTareaEscritura(pojos.pojos.Escritura procomun, Long idtarea){
		
		Usuario usuario  = new Usuario();
		Escritura escritura = new Escritura();
		Expediente expediente = new Expediente();
		usuario.setIdusuario(procomun.getIdusuario());
		usuario.setIdsesionactual(procomun.getIdsesionactual());
		escritura.setDsnumescritura(procomun.getEscritura());
		escritura.setIdescritura(procomun.getIdescritura());
		escritura.setIdsesion(procomun.getIdsesionactual());
		escritura.setIdtachafirma(idtarea.toString());
		expediente.setIdexpediente(procomun.getIdexpediente());
		
		EscrituraEnvio esenv = new EscrituraEnvio();
		esenv.setUsuario(usuario);
		esenv.setEscritura(escritura);
		esenv.setExpediente(expediente);
		
		
		PostGet pg = new PostGet(urlIdTarea);
		JsonNode respuesta = pg.getPost(esenv);
		esenv = (EscrituraEnvio) PostGet.setMapperJsonToPojo(respuesta,EscrituraEnvio.class);
		
	}
	
public void setIdTareaEscritura(String idusuario,String sesionactual,String numescritura,String idescritura,String idexpediente, Long idtarea){
		
		Usuario usuario  = new Usuario();
		Escritura escritura = new Escritura();
		Expediente expediente = new Expediente();
		usuario.setIdusuario(idusuario);
		usuario.setIdsesionactual(sesionactual);
		escritura.setDsnumescritura(numescritura);
		escritura.setIdescritura(idescritura);
		escritura.setIdsesion(sesionactual);
		escritura.setIdtachafirma(idtarea.toString());
		expediente.setIdexpediente(idexpediente);
		
		EscrituraEnvio esenv = new EscrituraEnvio();
		esenv.setUsuario(usuario);
		esenv.setEscritura(escritura);
		esenv.setExpediente(expediente);
		
		
		PostGet pg = new PostGet(urlIdTarea);
		JsonNode respuesta = pg.getPost(esenv);
		esenv = (EscrituraEnvio) PostGet.setMapperJsonToPojo(respuesta,EscrituraEnvio.class);
		
	}
	
	public void setNoPaso(ProcesoComun procomun){
		
		Usuario usuario  = new Usuario();
		Escritura escritura = new Escritura();
		usuario.setIdusuario(procomun.getIdusuario());
		usuario.setIdsesionactual(procomun.getIdsesionactual());
		escritura.setIdescritura(procomun.getIdescritura());
		EscrituraEnvio esenv = new EscrituraEnvio();
		esenv.setUsuario(usuario);
		esenv.setEscritura(escritura);
		System.out.println(urlnopaso);
		PostGet pg = new PostGet(urlnopaso);
		JsonNode respuesta = pg.getPost(esenv);
		esenv = (EscrituraEnvio) PostGet.setMapperJsonToPojo(respuesta,EscrituraEnvio.class);
		
	}
	
		public static void main(String[] args) {
			// TEST TAREA
			/*
			usuario.setIdusuario(procomun.getIdusuario());
			usuario.setIdsesionactual(procomun.getIdsesionactual());
			escritura.setDsnumescritura(procomun.getEscritura());
			escritura.setIdsesion(procomun.getIdsesionactual());
			*/
			
			ProcesoComun procomun = new ProcesoComun();
			procomun.setIdusuario("fc2d98aadbfbca1d304e1d56ae894fbb");
			procomun.setIdsesionactual("3121C9AFA413043D03F2E4E43BF93E56");
			procomun.setEscritura("1234787657890");
			procomun.setIdescritura("1460954dab56aa7fa3906d54c578f23b");
			procomun.setIdexpediente("8080b89b36b25a4f37f803d8bcc5bce6");
			
			EscrituraUtils escutils = new EscrituraUtils();
			//escutils.setNoPaso(procomun);
			//escutils.setIdTareaEscritura(procomun,"280005");
			
			
			
		}
	

}
