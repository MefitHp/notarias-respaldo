package test;

import javax.servlet.http.HttpServletRequest;

import org.codehaus.jettison.json.JSONException;

import com.palestra.notaria.bo.ExpedienteBo;
import com.palestra.notaria.bo.PersonaBo;
import com.palestra.notaria.bo.TramiteBo;
import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.bo.impl.ExpedienteBoImpl;
import com.palestra.notaria.bo.impl.PersonaBoImpl;
import com.palestra.notaria.bo.impl.TramiteBoImpl;
import com.palestra.notaria.bo.impl.UsuarioBoImpl;
import com.palestra.notaria.envio.TramiteEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.resource.TramiteRest;

public class testCloneExpediente {

	
	public static void main(String args[]) throws NotariaException, JSONException{
		
		String idExpediente = "521ed69e1b750a829d9a1b185711d850";
		String idPersona = "";
		String idUsuario = "9e722bee5a0315310b8dfa4515e8f810";
		//Persona
		PersonaBo personaBo = new PersonaBoImpl();
		Persona persona = personaBo.findById(idPersona);
		
		//Tramite
		TramiteBo tramiteBo = new TramiteBoImpl();
		Tramite tramite = new Tramite();
		TramiteRest tramiteRest = new TramiteRest();
		TramiteEnvio tramEnv = new TramiteEnvio();
		tramEnv.setTramite(tramite);
		tramiteRest.registraTramite(tramEnv, null);
		
		
		
		
		
		
		ExpedienteBo expBo = new ExpedienteBoImpl();
		UsuarioBO usuBo = new UsuarioBoImpl();
		Usuario usu = usuBo.findById(idUsuario);
		Expediente exp = expBo.findById(idExpediente);
		
		
		
		
	}
	
}
