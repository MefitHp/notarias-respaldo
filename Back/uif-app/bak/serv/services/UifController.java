package com.palestra.notaria.uif.services;

/*
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

import javax.activation.MimetypesFileTypeMap;
import javax.annotation.processing.FilerException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.palestra.notaria.uif.dao.PersonaDao;
import com.palestra.notaria.uif.dao.UifDao;
import com.palestra.notaria.uif.envio.UifEnvio;
import com.palestra.notaria.uif.models.Persona;
import com.palestra.notaria.uif.models.Uif;
import com.palestra.notaria.uif.utiles.GeneradorId;
import com.palestra.notaria.uif.utiles.StorageService;
*/
class UifController{
	
}
/*
@RestController
@RequestMapping("/notarias-uif")
@CrossOrigin
public class UifController {
	
	
	@Autowired
	private UifDao uifDao;
	@Autowired
	private PersonaDao personaDao;
	
	final int limitpage = 20;
	
	
	// REVISAR IMPLEMENTACION DE:
	//http://javasampleapproach.com/java-integration/upload-multipartfile-spring-boot
	 @Autowired
	 private StorageService storageService;
	 
	 
	//LISTAR
	@RequestMapping(value=("/{page}"),method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Uif>> getPersona(@PathVariable Integer page){
		
		Page<Uif> uifs = getPersonas(page);
		return new ResponseEntity<Page<Uif>>(uifs,HttpStatus.OK);
	}
	
	// X ID
	@RequestMapping(value="/{page}/{id}",method=RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Uif> getPersona(@PathVariable("id") long id){
		Optional<Uif> uif = uifDao.findOne(id);
		if(uif.isPresent()){
			return new ResponseEntity<Uif>(uif.get(),HttpStatus.OK);
		}else{
			return new ResponseEntity<Uif>(HttpStatus.NOT_FOUND);
		}
	}
	
	// X GET FILE
		@RequestMapping(value="/file/{id}",method=RequestMethod.GET)
		public ResponseEntity<InputStreamResource> getFile(@PathVariable("id") long id){
			Optional<Uif> uif = uifDao.findOne(id);
			if(!uif.isPresent()){
				return new ResponseEntity<InputStreamResource>(HttpStatus.NOT_FOUND);
			}
			Resource file = storageService.loadFile(uif.get().getArchivo());
			Long lenght;
			try {
				lenght = file.contentLength();
				MimetypesFileTypeMap mtf = new MimetypesFileTypeMap(file.getInputStream());
				MediaType mt = 	MediaType.valueOf(mtf.getContentType(file.getFile()));
				HttpHeaders headers = new HttpHeaders();
				headers.setContentDispositionFormData("inline",file.getFilename());
				headers.setContentType(mt);
				
				return (ResponseEntity<InputStreamResource>) ResponseEntity.ok().contentLength(lenght)
						.headers(headers).body(new InputStreamResource(file.getInputStream()));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
			
			
		}
	
	// DELETE
	@RequestMapping(value="/{page}/{id}",method=RequestMethod.DELETE,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Uif>> delete(@PathVariable("id") long id,@PathVariable Integer page){
		Optional<Uif> uif = uifDao.findOne(id);
		if(uif.isPresent()){
			try {
				storageService.delete(uif.get().getArchivo());
			} catch (IOException e) {
				return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.NOT_MODIFIED);
			}
			uifDao.delete(id);
				
		}
		
		
		return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.OK);
	}
	
	// SAVE
	@RequestMapping(value="/",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Page<Uif>> savePersona(@RequestBody UifEnvio personaenvio){
		Uif uif = personaenvio.getPersona();
		Integer page = personaenvio.getPage();
		
		uifDao.save(uif);
		return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.OK);
	}
	
	@RequestMapping(value="/saveFile",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<Page<Uif>> uploadFile(
			@RequestParam(name="id",required=false)Long id,
			@RequestParam(name="page",required=true)Integer page,
			@RequestParam(name="idpersona",required=false)String idpersona,
			@RequestParam(name="dsnombre",required=true) String nombres,
			@RequestParam(name="dsapellidopat",required=false) String paterno,
			@RequestParam(name="dsapellidomat",required=false) String materno,
			@RequestParam(name="dsrfc",required=true) String rfc,
			@RequestParam(name="dscurp",required=false) String curp,
			@RequestParam(name="acto",required=false) String acto,
			@RequestParam(name="tipocompareciente",required=false) String tipocompareciente,
			@RequestParam(name="escritura",required=true) Long escritura,
			@RequestParam(name="fecha",required=false) String fecha,
			@RequestParam(name="archivo",required=false) String archivo,
			@RequestParam(name="file",required=false) MultipartFile file){
		
		String urlfile = null;
		Date tmpfecha = new Date(Long.parseLong(fecha,10));
		
		if(paterno!=null && paterno.equals("null")) paterno = null;
		
		if(materno != null && materno.equals("null")) materno = null;
		
		if(curp != null && curp.equals("null"))curp = null;
	
		if(acto != null && acto.equals("null")) acto = null;
		
		if(tipocompareciente != null && tipocompareciente.equals("null")) tipocompareciente = null;
			
		if(archivo != null && archivo.equals("null")) archivo = null;
	
		
		
		
		boolean actualizauif = false;
		boolean actualizatodo = false;
		Persona persona = new Persona(nombres, paterno, materno);
		
		if(idpersona != null && id!=null){
			actualizatodo=true;
		}
		
		if(idpersona!=null && id==null){
			actualizauif = true;
		}
		
		if(actualizatodo){
			Optional<Persona> optpersona = personaDao.findOne(idpersona);
			if(optpersona.isPresent()){
				persona = optpersona.get();
			}
			persona.setDsnombre(nombres);
			persona.setDsapellidopat(paterno);
			persona.setDsapellidomat(materno);
			StringBuilder nombrecompleto = new StringBuilder(nombres);
			if(paterno!=null){
				nombrecompleto.append(" ");
				nombrecompleto.append(paterno);
			}
			if(materno!=null){
				nombrecompleto.append(" ");
				nombrecompleto.append(materno);
			}
			
			persona.setDsnombrecompleto(nombrecompleto.toString().trim());
		}
		
		persona.setDsrfc(rfc);
		persona.setDscurp(curp);
		persona.setIdpersona((idpersona!=null)?idpersona:GeneradorId.generaId(persona));
		
		
		if(file!=null){
			if(rfc==null){
				return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.FAILED_DEPENDENCY);
			}
			urlfile = this.storageService.storeUif(file,rfc, Long.toString(escritura));	
		}
		
		Uif uif = new Uif();
		uif.setId(id);
		if(file==null && archivo!=null){
			urlfile = archivo;
		}
		uif.setArchivo(urlfile);
		uif.setActo(acto);
		uif.setTipocompareciente(tipocompareciente);
		uif.setEscritura(escritura);
		uif.setFecha(tmpfecha);
		
		if(actualizatodo){			
			personaDao.save(persona);
			uif.setIdpersona(persona);
			uifDao.save(uif);
		}else{
			
			if(actualizauif){
				uif.setIdpersona(persona);
				uifDao.save(uif);
			}else{
				persona.addUif(uif);
				personaDao.save(persona);
			}
			
		}

		return new ResponseEntity<Page<Uif>>(this.getPersonas(page),HttpStatus.OK);
		
	}
	
	
	
	// BUSCAR
	@RequestMapping(value="/buscar",method=RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,consumes=MediaType.APPLICATION_JSON_VALUE)
		public ResponseEntity<Page<Uif>> buscarPersona(@RequestBody UifEnvio personaenvio){
			PageRequest paginator = new PageRequest(0, 1000,Sort.Direction.DESC, "escritura");
			String parametro = personaenvio.getParametrobusqueda();
			String tipo = personaenvio.getTipobusqueda();
			Page<Uif> pageresponse = null;
			switch (tipo) {
			case "escritura":
				Long numesc = Long.parseLong(parametro,10);
				pageresponse = uifDao.findByEscritura(numesc, paginator);
				break;
			case "rfc":
				
				pageresponse = uifDao.findByPersonaDsrfc(parametro, paginator);
				break;
				
			case "curp":
				pageresponse = uifDao.findByPersonaDscurp(parametro, paginator);
				break;
			
			case "fechas":
				//todo: Falta implementacion
				break;

			default:
				pageresponse = uifDao.findByPersonaDsnombrecompletoContaining(parametro,paginator);
			}
			
			
			return new ResponseEntity<Page<Uif>>(pageresponse,HttpStatus.OK);
		}
	
		
		// METODO UTILES
		
	private Page<Uif> getPersonas(int page){
		PageRequest paginator = getPAgeRequest(page);
		return (Page<Uif>) uifDao.findByActiveTrue(paginator);
	}
	
	private PageRequest getPAgeRequest (int page){
		
		if(page < 0) page = 0;
		
		return new PageRequest( page, limitpage,Sort.Direction.DESC, "escritura");
	}
	
	
}
*/


