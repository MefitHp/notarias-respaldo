package procesos_integracion;

import java.util.Date;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.palestra.notaria.envio.MesaCtlEnvio;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Usuario;
import com.victor.utils.restconexion.PostGet;

public class ImpuestoUtils {
	
	private String urlRestMesa = "http://localhost:9090/notarias-web/notaria/mesacontrol";
	private String urlSave="/save";
	private String urlsetIdTarea="/actualizaTareaBo";

	public ImpuestoUtils() {
		// TODO Auto-generated constructor stub
	}
	
	public String save(Boolean ispagorequire,String idactodoc,String idusuario,String idsessionactual,Date vencimiento,Long idtarea){
		Usuario usuario  = new Usuario();
		MesaCtrl mesactrl = new MesaCtrl();
		ActoDocumento actodoc = new ActoDocumento();
		MesaCtlEnvio mesaenv = new MesaCtlEnvio();
		usuario.setIdusuario(idusuario);
		usuario.setIdsesionactual(idsessionactual);
		actodoc.setIdactodoc(idactodoc);
		mesactrl.setIspagorequire(ispagorequire);
		mesactrl.setActodocumento(actodoc);
		mesactrl.setVencimiento(vencimiento);
		if(idtarea!= null){
			mesactrl.setIdtarea(idtarea.toString());
		}
		mesaenv.setUsuario(usuario);
		mesaenv.setDocumento(mesactrl);
		PostGet pg = new PostGet(urlRestMesa + urlSave);
		JsonNode respuesta = pg.getPost(mesaenv);
		
		mesaenv = (MesaCtlEnvio) PostGet.setMapperJsonToPojo(respuesta,MesaCtlEnvio.class);
		
		System.out.println(mesaenv);
		
		if(mesaenv != null && mesaenv.getDocumento()!=null){
			return mesaenv.getDocumento().getIdmesacontrol();
			//return mesaenv.toString();
		}else{
			return "nada2";
		}
		
	}
	
	public void actualizaTarea(String idusuario,String idsessionactual,String idmesactrl,Long idtarea){
		Usuario usuario = new Usuario();
		MesaCtrl mesa = new MesaCtrl();
		MesaCtlEnvio mesaenvio = new MesaCtlEnvio();
		usuario.setIdusuario(idusuario);
		usuario.setIdsesionactual(idsessionactual);
		mesa.setIdmesacontrol(idmesactrl);
		if(idtarea!=null){
			mesa.setIdtarea(idtarea.toString());
		}
		mesaenvio.setDocumento(mesa);
		mesaenvio.setUsuario(usuario);
		
		PostGet pg = new PostGet(urlRestMesa + urlsetIdTarea);
		JsonNode respuesta = pg.getPost(mesaenvio);
		mesaenvio = (MesaCtlEnvio) PostGet.setMapperJsonToPojo(respuesta,MesaCtlEnvio.class);
	}

	public static void main(String[] args) {
		String idusuario ="fc2d98aadbfbca1d304e1d56ae894fbb";
		String idsessionactual ="EE70DE125C1F96E78604E6FBFD39BBF6";
		//String idescritura ="1460954dab56aa7fa3906d54c578f23b";
		String idactodoc ="1949e4cc3016f4a544ee4bad4aa812c8";
		//String idexpediente = "f5237a4560908ad85b673164d897504b";
		//String idmesactrl ="4111f645b9e7011a1bc590129f7f89eb";
		Long idtarea = null;
		Date hoy = new Date();
		ImpuestoUtils demo = new ImpuestoUtils();
		String idmesa = demo.save(false, idactodoc, idusuario, idsessionactual,hoy,idtarea);
		System.out.println("*****************************************************************");
		System.out.println("**********************"+idmesa+"***************************************");
		System.out.println("*****************************************************************");
		//demo.actualizaTarea(idusuario, idsessionactual, idmesactrl, null);
		
	}
}
