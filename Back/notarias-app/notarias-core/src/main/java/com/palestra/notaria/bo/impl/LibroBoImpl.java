package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.Date;

import com.palestra.notaria.bo.AvisoDecenaFoliosBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.dao.LibroDao;
import com.palestra.notaria.dao.impl.LibroDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.util.GeneradorId;

public class LibroBoImpl extends GenericBoImpl<Libro> implements LibroBo{
	
	public LibroDao libroDao;
	private int maxfolios=200;
	
	public LibroBoImpl() {
		this.libroDao = new LibroDaoImpl();
		super.dao = this.libroDao;
	}
	
	@Override
	public Libro obtenUltimoLibro() throws NotariaException{
		return this.libroDao.obtenUltimoLibro();
	}
	
	@Override
	public Libro obtenLibroXnumero(Long numero)throws NotariaException{
		return this.libroDao.obtenLibroXnumero(numero);
	}
	
	@Override
	public boolean validaFoliosLibro(Long folios, PizarronElemento pizarron)throws NotariaException{
		Libro libro=null;
		if(pizarron==null){
			libro = this.obtenUltimoLibro();
		}else{
			libro = libroDao.findById(pizarron.getIdlibro().getIdlibro());
		}
		long sumfolios = libro.getInfolioinicial() + folios;
		boolean respuesta = false;
		if(sumfolios > maxfolios){
			Long faltantes =  maxfolios-libro.getInfolioinicial();
			throw new NotariaException("Los folios solicitados exceden el límite, solo faltan "+faltantes+" para cerrar el libro "+libro.getInnumlibro());
		
		}else if(sumfolios == maxfolios){
			respuesta = true;
			Libro libsave = new Libro();
			libsave.setInfolioinicial(0L);
			libsave.setInnumlibro(libro.getInnumlibro()+1);
			libsave.setFecha(new Date());
			libsave.setTmstmp(new Timestamp(new Date().getTime()));
			libsave.setIdlibro(GeneradorId.generaId(libro));
			libsave.setDsdescripcion("Generado desde el sistema");
			libsave.setIdsesion("0");
			this.dao.save(libsave);
			
			if(libsave.getInnumlibro() % 10 == 1){

				AvisoDecenaFoliosBo avisodecenaBo = new AvisoDecenaFoliosBoImpl();				
				//cierro decena anterior
				avisodecenaBo.cierraDecena(libro.getInnumlibro()-1);
				avisodecenaBo.abreDecena();
				
			}
			
		}else{
			libro.setInfolioinicial(sumfolios);
			this.dao.update(libro);
		}
		
		return respuesta;
	}
	
	
	
		
	 
	 
	

}
