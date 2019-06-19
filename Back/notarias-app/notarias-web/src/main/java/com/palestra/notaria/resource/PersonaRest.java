package com.palestra.notaria.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import com.palestra.notaria.bo.ContactoBo;
import com.palestra.notaria.bo.DomicilioBo;
import com.palestra.notaria.bo.PersonaBo;
import com.palestra.notaria.bo.impl.ContactoBoImpl;
import com.palestra.notaria.bo.impl.DomicilioBoImpl;
import com.palestra.notaria.bo.impl.PersonaBoImpl;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoBusquedaPersona;
import com.palestra.notaria.dato.DatoNombrePersona;
import com.palestra.notaria.envio.PersonaEnvio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Contacto;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.NotariaUtils;

@Path("/persona")
public class PersonaRest {

	static Logger logger = Logger.getLogger(PersonaRest.class);
	private PersonaBo personaBo;
	
	private DomicilioBo domicilioBo;
	
	public PersonaRest(){
		personaBo=new PersonaBoImpl();
		domicilioBo= new DomicilioBoImpl();
	}

//	SE USA?
	@GET
	@Path("/listar")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<Persona> listar() {
		System.out.println("entra a findAll");
		List<Persona> lista;
		try {
			lista = this.personaBo.findAll();
		} catch (NotariaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		if (lista != null) {
//			for (Persona p : lista) {
				// p.setDomicilio(null);
				// p.setTipopersona(null);
				// p.setNacionalidad(null);
				// p.setEstadocivil(null);
//			}
			return new ArrayList<Persona>(lista);
		} else
			return new ArrayList<Persona>();
	}

	@POST
	@Path("/obtenerPorId")
	@Produces({ "application/json", "application/xml" })
	public Persona obtenerPorId(Persona persona,
			@Context HttpServletRequest request) throws JSONException {
		System.out.println("entra a obtenerPorId");
		JSONObject msj = new JSONObject();
		Persona p = null;
		try {
			p = personaBo.findById(persona.getIdpersona());
			p.setTipopersona(null);
			p.setNacionalidad(null);
			msj.put("Resultado", "Registro existoso");
		} catch (Exception e) {
			msj.put("Resultado", "Registro fallido");
			e.printStackTrace();
		}
		return p;
	}

	@POST
	@Path("/obtenerPorIdCompleto")
	@Produces({ "application/json", "application/xml" })
	public PersonaEnvio obtenerPorIdCompleto(PersonaEnvio pe){
		PersonaEnvio respuesta = new PersonaEnvio(); 
		if(NotariaUtils.faltanRequeridosUsuario(pe) || pe.getPersona() == null || pe.getPersona().getIdpersona()==null){
			respuesta.setEstatus(Constantes.ESTATUS_FALTAN_REQUERIDOS);
			respuesta.setExito(false);
			return respuesta;
		}
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			try {
				respuesta.setPersona(this.personaBo.buscarPorIdCompleto(pe.getPersona()));
				respuesta.setEstatus("busqueda correcta");
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			
		}else{
			respuesta.setEstatus("sesion invalida");
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/guardar")
	@Produces({ "application/json", "application/xml" })
	public PersonaEnvio registraPersona(PersonaEnvio pe){
		PersonaEnvio respuesta = new PersonaEnvio();
		Persona persona = pe.getPersona();
		if(NotariaUtils.isSesionValida(pe.getUsuario().getIdsesionactual(),pe.getUsuario().getIdusuario())){
			persona.setIdsesion(pe.getUsuario().getIdsesionactual());
			persona.setIdpersona(GeneradorId.generaId(pe.getPersona()));
			try{
//				pe.getPersona().setDomicilio(getDomicilioBo().save(pe.getPersona().getDomicilio()));
				respuesta.setPersona(getPersonaBo().save(pe.getPersona()));
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
			if(respuesta.getPersona()==null){
				respuesta.setEstatus("ocurrio un error al guardar");
				respuesta.setExito(false);
				return respuesta;
			}
			respuesta.setEstatus("guardado correcto");
			respuesta.setExito(true);
			return respuesta;
		}else{
			respuesta.setEstatus("sesion invalida");
			respuesta.setExito(false);
			return respuesta;
		}
	}

	@POST
	@Path("/actualizar")
	@Produces({ "application/json", "application/xml" })
	public Persona actualizarPersona(Persona persona,
			@Context HttpServletRequest request) throws JSONException {
		// persona.setIdpersona(GeneradorId.generaId(persona));
		persona.setIdsesion(request.getSession().getId());
		JSONObject msj = new JSONObject();
		Persona p = null;
		try {
			p = personaBo.update(persona);
			p = personaBo.buscarPorIdCompleto(p);
			msj.put("Resultado", "Registro existoso");
		} catch (Exception e) {
			msj.put("Resultado", "Registro fallido "+e.getMessage());
			e.printStackTrace();
		}
		return p;
	}

	@GET
	@Path("/busquedaPersonaPorNombreGet")
	@Produces(MediaType.APPLICATION_JSON)
	public ArrayList<DatoNombrePersona> busquedaPersonaPorNombreGet( 
			@QueryParam("s") String s) {
		try{
			PersonaEnvio pe = new PersonaEnvio();
			pe.setNombrePersona(s);
			PersonaEnvio respuesta = this.buscaPersonaPorNombre(pe);
			if(respuesta==null || respuesta.getResultados()==null){
				return null;
			}
			ArrayList<DatoNombrePersona> lista = new ArrayList<DatoNombrePersona>();
			DatoNombrePersona dnp=null;
			for(DatoBusquedaPersona dbp: respuesta.getResultados()){
				dnp = new DatoNombrePersona();
				dnp.setDsnombre(dbp.getDsnombre());
				dnp.setDsapellidopat(dbp.getDsapellidopat());
				dnp.setDsapellidomat(dbp.getDsapellidomat());
				dnp.setNombrecompleto(dbp.getDsnombre()+" "+dbp.getDsapellidopat()+" "+dbp.getDsapellidomat());
				lista.add(dnp);
			}
			return lista;
		}catch(NotariaException e){
			e.printStackTrace(System.out);
			return null;
		}
	}
	
	@POST
	@Path("/busquedaPersonaPorNombrePost")
	@Produces({ "application/json", "application/xml" })
	public PersonaEnvio busquedaPersonaPorNombrePost(PersonaEnvio personaRequest,
			@Context HttpServletRequest request) throws JSONException {
		PersonaEnvio respuesta = new PersonaEnvio();
		if (NotariaUtils.isSesionValida(personaRequest.getUsuario()
				.getIdsesionactual(), personaRequest.getUsuario().getIdusuario())) {
			try{
				if (personaRequest.getNombrePersona() == null
						|| personaRequest.getNombrePersona().isEmpty()) {
					respuesta.setExito(false);
					respuesta.setEstatus("Falta nombre de persona.");
					return respuesta;
				}
				return this.buscaPersonaPorNombre(personaRequest);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		} else {
			respuesta.setEstatus("La sesion no es válida");
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@POST
	@Path("/buscarPersonas")
	@Produces(MediaType.APPLICATION_JSON)
	public PersonaEnvio buscarPersonas(PersonaEnvio persEnvio){
		PersonaEnvio respuesta = new PersonaEnvio();
		if (NotariaUtils.isSesionValida(persEnvio.getUsuario()
				.getIdsesionactual(), persEnvio.getUsuario().getIdusuario())) {
			try{
				respuesta.setPersonaList(personaBo.buscarPersonaPorNombre(persEnvio.getPersona().getDsnombrecompleto()));
				respuesta.setEstatus(Constantes.ESTATUS_BUSQUEDA_CORRECTA);
				respuesta.setExito(true);
				return respuesta;
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				respuesta.setEstatus(Constantes.ESTATUS_ERROR_CONSULTA+" "+e.getMessage());
				respuesta.setExito(false);
				return respuesta;
			}
		}else{
			respuesta.setEstatus(Constantes.ESTATUS_SESION_INVALIDA);
			respuesta.setExito(false);
			return respuesta;
		}
	}
	
	@GET
	@Path("/buscarPersonas")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Persona> buscarPersonasGet(@QueryParam("nombre") String nombre){
			try{
				return personaBo.buscarPersonaPorNombre(nombre);
			}catch(NotariaException e){
				e.printStackTrace(System.out);
				return null;
			}
	}
	
	@POST
	@Path("/contactoXpersona")
	@Produces(MediaType.APPLICATION_JSON)
	public Contacto contactoXpersona(Persona persona){
		Contacto respuesta = new Contacto();
		if(persona !=null){
			ContactoBo co = new ContactoBoImpl();
			try {
				return co.buscarXpersona(persona.getIdpersona());
			} catch (NotariaException e) {
				e.printStackTrace();
			}
		}
		return respuesta;
	}
	
	
	
	/**
	 * Busca una persona por su nombre en la BD. Tambien devuelve comparecientes
	 * @param personaRequest
	 * @return
	 * @throws NotariaException 
	 */
	private PersonaEnvio buscaPersonaPorNombre(PersonaEnvio personaRequest) throws NotariaException{
		PersonaEnvio respuesta = new PersonaEnvio();
		String nombrePersona = personaRequest.getNombrePersona().replaceAll("\\s+", " ").trim();
		List<DatoBusquedaPersona> lista = personaBo.findPersonaByName(nombrePersona);
		if (lista != null) {
			respuesta.setResultados(new ArrayList<DatoBusquedaPersona>(
					lista));
			respuesta.setNumeroResultados(lista.size());
			respuesta.setEstatus("Búsqueda correcta.");
			respuesta.setExito(true);
			return respuesta;
		} else {
			respuesta.setResultados(null);
			respuesta.setNumeroResultados(0);
			respuesta.setEstatus("No se encontraron resultados.");
			respuesta.setExito(false);
			return respuesta;
		}
	}

	public PersonaBo getPersonaBo() {
		return personaBo;
	}
	public void setPersonaBo(PersonaBo personaBo) {
		this.personaBo = personaBo;
	}
	public DomicilioBo getDomicilioBo() {
		return domicilioBo;
	}
	public void setDomicilioBo(DomicilioBo domicilioBo) {
		this.domicilioBo = domicilioBo;
	}
}
