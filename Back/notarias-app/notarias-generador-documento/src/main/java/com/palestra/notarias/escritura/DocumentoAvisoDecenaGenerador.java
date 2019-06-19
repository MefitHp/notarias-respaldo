package com.palestra.notarias.escritura;

import java.util.ArrayList;
import java.util.Collections;
import java.sql.Date;
import java.util.List;

import com.palestra.notaria.bo.AvisoDecenaFoliosBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.bo.impl.AvisoDecenaFoliosBoImpl;
import com.palestra.notaria.bo.impl.EscrituraBoImpl;
import com.palestra.notaria.bo.impl.LibroBoImpl;
import com.palestra.notaria.bo.impl.PizarronElementoBoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notarias.utils.NumberToLetterConverter;

public class DocumentoAvisoDecenaGenerador {

	LibroBo libroBo;
	AvisoDecenaFoliosBoImpl avisoBo;
	public DocumentoAvisoDecenaGenerador() {
		// TODO Auto-generated constructor stub
		 libroBo = new LibroBoImpl();
		 avisoBo = new AvisoDecenaFoliosBoImpl();
	}

	
	public String calcula_libros(Long libronumero) throws NotariaException{
		
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		if(libro!=null){
				respuesta.append(libro.getInnumlibro() +" al "+(libro.getInnumlibro()+9));
		}
		return respuesta.toString();
	}
	
	
	public String escritura_inicio(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		if(libro !=null){
			PizarronElementoBo peBo = new PizarronElementoBoImpl();
			PizarronElemento elemento = peBo.getXLibro(libro);
			if(elemento!=null){
				respuesta.append(elemento.getNumeroescritura().toString());
			}
		}
		return respuesta.toString();
	}
	
	public String folio_inicio(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		
		if(libro!=null){
			PizarronElementoBo peBo = new PizarronElementoBoImpl();
			PizarronElemento elemento = peBo.getXLibro(libro);
			respuesta.append(elemento.getFolioInicial().toString());
		}
		return respuesta.toString();
	}
	
	
	public String numero_instrumentos(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		List<Escritura> escrituras = getEscriturasLibro(libro.getInnumlibro(),null);
		
		if(escrituras !=null){
			respuesta.append(escrituras.size());
		}
		return respuesta.toString();
	}
	
	
	public String calcula_instrumentos(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		if(libro!=null){
			List<Escritura> escrituras = getEscriturasLibro(libro.getInnumlibro(),null);
			if(escrituras.size()>2){
				List<Integer> numeros = new ArrayList<Integer>();
				for(Escritura esc:escrituras){
					numeros.add(Integer.parseInt(esc.getDsnumescritura()));
				}
				Integer escini = Collections.min(numeros);
				Integer escfin = Collections.max(numeros);
				
				respuesta.append(escini+" (");
				respuesta.append(NumberToLetterConverter.convertNumberToLetter(escini.toString(), NumberToLetterConverter.NUMBER_FORMAT).trim());
				respuesta.append(") al número ");
				respuesta.append(escfin+" (");
				respuesta.append(NumberToLetterConverter.convertNumberToLetter(escfin.toString(), NumberToLetterConverter.NUMBER_FORMAT).trim());
				respuesta.append(")");
				
			}
		}
		return respuesta.toString();
		
	}
	
	public String fechaApertura(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		AvisoDecenaFoliosBo avdefol = new AvisoDecenaFoliosBoImpl();
		AvisoDecena avisodecena	= avdefol.obtenerXnumeroLibro(libronumero);
		if(avisodecena!=null){
			Date fecha = new Date(avisodecena.getFechaApertura().getTime());
			respuesta.append(fecha);
		}else{
			throw new NotariaException("No existe ningún aviso asociado, contacte a sistemas");
		}
		return respuesta.toString();
		}

	public String fechaCierre(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();		
		AvisoDecenaFoliosBo avdefol = new AvisoDecenaFoliosBoImpl();
		AvisoDecena avisodecena	= avdefol.obtenerXnumeroLibro(libronumero-1);
		if(avisodecena!=null && avisodecena.getFechaCierre()!=null){
			Date fecha = new Date(avisodecena.getFechaCierre().getTime());
			respuesta.append(fecha);
		}else{
			throw new NotariaException("No existe ningún aviso asociado, contacte a sistemas");
		}
		return respuesta.toString();
		}

	public String escrituras_nopaso(Long libronumero) throws NotariaException{
		List<Escritura> escrituras = getEscriturasLibro(libronumero,true);
		StringBuilder respuesta = new StringBuilder();		
		respuesta.append("<p>");
		for(Escritura escritura:escrituras){
			
			respuesta.append("#LETRA)");
			respuesta.append(escritura.getDsnumescritura());
			respuesta.append(" (");
			respuesta.append(NumberToLetterConverter.convertNumberToLetter(escritura.getDsnumescritura(), NumberToLetterConverter.NUMBER_FORMAT).trim());
			respuesta.append(").");
			respuesta.append("<br>");
		}
		return respuesta.append("</p>").toString();
	}
	
	public String libro_numero(Long libronumero)throws NotariaException{
		StringBuilder respuesta = new StringBuilder();
		Libro libro = avisoBo.getLibroApertura(libronumero);
		respuesta.append(libro.getInnumlibro());
		return respuesta.toString(); 
	}
	
	
	private List<Escritura> getEscriturasLibro(Long numerolibro,Boolean status) throws NotariaException{
		EscrituraBo escBo = new EscrituraBoImpl();
		return escBo.getXnumLibroStatus(numerolibro, numerolibro+9, status);
	}
	
			
	
}
