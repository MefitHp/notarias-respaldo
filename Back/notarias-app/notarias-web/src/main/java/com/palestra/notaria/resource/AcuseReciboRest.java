package com.palestra.notaria.resource;

import java.util.ArrayList;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.palestra.notaria.bo.AcuseReciboEscrituraBo;
import com.palestra.notaria.bo.AcuseReciboPersonasBo;
import com.palestra.notaria.bo.impl.AcuseReciboEscrituraBoImpl;
import com.palestra.notaria.bo.impl.AcuseReciboPersonasBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.envio.AcuseReciboEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AcuseReciboPersonas;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/acuseRecibo")
@Produces(MediaType.APPLICATION_JSON)
public class AcuseReciboRest {
	
	private AcuseReciboEscrituraBo acuseReciboEscrituraBo;
	
	private AcuseReciboPersonasBo acuseReciboPersonasBo;
	
	public AcuseReciboRest(){
		this.acuseReciboEscrituraBo = new AcuseReciboEscrituraBoImpl();
		this.acuseReciboPersonasBo = new AcuseReciboPersonasBoImpl();
	}
	
	@POST
	public AcuseReciboEnvio guardar(AcuseReciboEnvio ae){
		AcuseReciboEnvio respuesta = new AcuseReciboEnvio();
		if (ae == null || ae.getUsuario() == null
				|| ae.getUsuario().getIdusuario() == null || ae.getUsuario().getIdusuario().isEmpty()
				|| ae.getUsuario().getIdsesionactual() == null || ae.getUsuario().getIdsesionactual().isEmpty()
				|| ae.getAcuseReciboEscritura() == null || ae.getAcuseReciboPersonas() == null
				|| ae.getAcuseReciboPersonas().isEmpty()) {
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_PARAMETROS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(ae.getUsuario().getIdsesionactual(),ae.getUsuario().getIdusuario())){
			ArrayList<AcuseReciboPersonas> personasList = new ArrayList<AcuseReciboPersonas>();
			try{
				ae.getAcuseReciboEscritura().setIdacuse(GeneradorId.generaId(ae.getAcuseReciboEscritura()));
				ae.getAcuseReciboEscritura().setIdsesion(ae.getUsuario().getIdsesionactual());
				respuesta.setAcuseReciboEscritura(getAcuseReciboEscrituraBo().save(ae.getAcuseReciboEscritura()));
				respuesta.getAcuseReciboEscritura().getEscritura().setExpediente(null);
				respuesta.getAcuseReciboEscritura().getEscritura().setNotario(null);
				respuesta.getAcuseReciboEscritura().getEscritura().setLibro(null);
				
				for(AcuseReciboPersonas persona:ae.getAcuseReciboPersonas()){
					persona.setAcuseReciboEscritura(respuesta.getAcuseReciboEscritura());
					persona = getAcuseReciboPersonasBo().save(persona);
					persona.getAcuseReciboEscritura().setEscritura(null);
					personasList.add(persona);
				}
				respuesta.setAcuseReciboPersonas(personasList);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus(Constantes.ESTATUS_REGISTRO_CORRECTO);
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	
	public AcuseReciboEscrituraBo getAcuseReciboEscrituraBo() {
		return acuseReciboEscrituraBo;
	}
	
	public void setAcuseReciboEscrituraBo(
			AcuseReciboEscrituraBo acuseReciboEscrituraBo) {
		this.acuseReciboEscrituraBo = acuseReciboEscrituraBo;
	}
	
	public AcuseReciboPersonasBo getAcuseReciboPersonasBo() {
		return acuseReciboPersonasBo;
	}
	
	public void setAcuseReciboPersonasBo(
			AcuseReciboPersonasBo acuseReciboPersonasBo) {
		this.acuseReciboPersonasBo = acuseReciboPersonasBo;
	}
}
