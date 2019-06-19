package procesos_integracion;

import com.fasterxml.jackson.databind.JsonNode;
import com.palestra.notaria.envio.AlertaEnvio;
import com.palestra.notaria.modelo.AlertaObjeto;
import com.palestra.notaria.modelo.Usuario;
import com.victor.utils.restconexion.PostGet;

import procesos_enums.EnumStatusAlert;

public class DocumentoEstatusUtils {
	
	private String urlRestAlertas = "http://localhost:9090/notarias-web/notaria/alertas";
	private String urlActualizaAlerta="/updatestatus";
	
	
	public void actualizaTarea(String idusuario,String idsessionactual,String idobjeto,String status){
		Usuario usuario = new Usuario();
		AlertaObjeto alertaobjeto = new AlertaObjeto();
		AlertaEnvio alertaenvio = new AlertaEnvio();
		usuario.setIdusuario(idusuario);
		usuario.setIdsesionactual(idsessionactual);
		alertaobjeto.setIdalertaobjeto(idobjeto);
		alertaobjeto.setStatusalerta(status);
		
		alertaenvio.setUsuario(usuario);
		alertaenvio.setAlertaobjeto(alertaobjeto);
		
		PostGet pg = new PostGet(urlRestAlertas + urlActualizaAlerta);
		JsonNode respuesta = pg.getPost(alertaenvio);
		alertaenvio = (AlertaEnvio) PostGet.setMapperJsonToPojo(respuesta,AlertaEnvio.class);
	}
	
	
	public static void main(String[] args) {
		DocumentoEstatusUtils deu= new DocumentoEstatusUtils();
		deu.actualizaTarea("fc2d98aadbfbca1d304e1d56ae894fbb", "DBC70889916A7A6AD7EA1A1EDDE1AC8D", "41568daa5a57d59cc13637ac36a5dd22",EnumStatusAlert.DEAD.toString());
	}
	
}
