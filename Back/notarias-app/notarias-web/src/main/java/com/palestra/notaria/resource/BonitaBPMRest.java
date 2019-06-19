 package com.palestra.notaria.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.palestra.notaria.bo.BonitaBPMBo;
import com.palestra.notaria.bo.impl.BonitaBPMBoImpl;
import com.palestra.notaria.envio.BonitaBPMEnvio;
import com.palestra.notaria.exceptions.NotariaException;

import pojos.pojos.BonitaCommonBean;

@Path("/bonitaBPM")
public class BonitaBPMRest {

	static Logger logger = Logger.getLogger(BonitaBPMRest.class);
	
	@POST
	@Path("/saveTaskName")
	@Produces(MediaType.APPLICATION_JSON)
	public BonitaBPMEnvio getActos(BonitaCommonBean envio) {
		System.out.println(envio.getNombreTarea());
		return null;
	}
	
	@POST
	@Path("/validaFechasHabiles")
	@Produces(MediaType.APPLICATION_JSON)
	public BonitaBPMEnvio validaFechasHabiles(BonitaCommonBean envio) {
		BonitaBPMEnvio response = new BonitaBPMEnvio();
		
		BonitaBPMBo bo = new BonitaBPMBoImpl();
		BonitaCommonBean bc = new BonitaCommonBean();
		try {
			bc.setCalendar(bo.addBusinessDay(envio.getDias(), envio.getCalendar()));
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		response.setExito(true);
		response.setBonitaCommonBean(bc);
		return response;
	}
}
