package com.palestra.notaria.bo.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.AvisoDecenaFoliosBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.dao.AvisoDecenaDao;
import com.palestra.notaria.dao.LibroDao;
import com.palestra.notaria.dao.impl.AvisoDecenaDaoImp;
import com.palestra.notaria.dao.impl.LibroDaoImpl;
import com.palestra.notaria.dato.DatoAvisoDecena;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.AvisoDecena;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.UtilidadesModelos;

public class AvisoDecenaFoliosBoImpl extends GenericBoImpl<AvisoDecena> implements AvisoDecenaFoliosBo {

	AvisoDecenaDao avisoDao;
	LibroBo libroBo;
	
	@Override
	public List<DatoAvisoDecena> getAll()throws NotariaException{
		List<AvisoDecena> avisos = findAll();
		List<DatoAvisoDecena> respuesta = new ArrayList<DatoAvisoDecena>();
		for(AvisoDecena aviso:avisos){
			Libro libro = UtilidadesModelos.inicializaObjeto(aviso.getLibroapertura());
			DatoAvisoDecena dad = new DatoAvisoDecena();
			dad.setLibroinicial(libro.getInnumlibro());
			dad.setFechaapertura(aviso.getFechaApertura());
			dad.setFechacierre(aviso.getFechaCierre());
			dad.setIdavisodecena(aviso.getIdavisodecena());
			dad.setHas_evidencia_cierre(aviso.getUrlDocCierre()!=null);
			dad.setHas_evidencia_apertura(aviso.getUrlDocApertura()!=null);
			dad.setHas_evidencia_notificacion(aviso.getUrlDocAviso()!=null);
			
			respuesta.add(dad);
		}
		
		return respuesta;
	}
	
	public AvisoDecenaFoliosBoImpl() {
		// TODO Auto-generated constructor stub
		this.dao = new AvisoDecenaDaoImp(AvisoDecena.class);
		this.avisoDao = new AvisoDecenaDaoImp(AvisoDecena.class);
		libroBo = new LibroBoImpl();
		
	}

	@Override
	public List<Escritura> obtenerNoPaso(Long libroFinal) throws NotariaException {
		// TODO Auto-generated method stub
		EscrituraBo escBo = new EscrituraBoImpl();
		return escBo.getXnumLibroStatus((libroFinal-9),libroFinal, true);
	}

	@Override
	public List<Escritura> obtenerPasadas(Long libroFinal) throws NotariaException {
		EscrituraBo escBo = new EscrituraBoImpl();
		return escBo.getXnumLibroStatus((libroFinal-9),libroFinal, false);
	}

	@Override
	public AvisoDecena obtenerXnumeroLibro(Long numeroLibro) throws NotariaException {
		// TODO Auto-generated method stub
		return this.avisoDao.obtenerXnumeroLibro(this.calculaNumeroLibroApertura(numeroLibro));
		
	}
	
	@Override
	public void abreDecena() throws NotariaException {
		AvisoDecena avisoDecena = new AvisoDecena();
		LibroDao libDao = new LibroDaoImpl();
		Libro ultimolibro = libDao.obtenUltimoLibro();
		// falta notario y demas
		avisoDecena.setLibroapertura(ultimolibro);
		Timestamp timehoy = new Timestamp(System.currentTimeMillis());
		avisoDecena.setFechaApertura(timehoy);
		avisoDecena.setIdavisodecena(GeneradorId.generaId(avisoDecena));
		this.dao.save(avisoDecena);
	}

	@Override
	public void cierraDecena(Long numeroLibro) throws NotariaException {
		// TODO Auto-generated method stub
		//FALTA GENERAR DOCUMENTO DE CIERRE Y AVISO
		AvisoDecena avisodecena = this.obtenerXnumeroLibro(this.calculaNumeroLibroApertura(numeroLibro));
		if(avisodecena!=null){
			Timestamp timehoy = new Timestamp(System.currentTimeMillis());
			avisodecena.setFechaCierre(timehoy);
			this.dao.update(avisodecena);
		}
	}
	
	public Long calculaNumeroLibroApertura(Long numeroLibro)throws NotariaException{
		if(numeroLibro<10){
			throw new NotariaException("El número de libro no es válido, por favor verificalo");
		}
		return numeroLibro = (numeroLibro - (numeroLibro%10))+1;	
	}
	
	
	public Libro getLibroApertura(Long numerolibro)throws NotariaException{
		numerolibro = calculaNumeroLibroApertura(numerolibro);
		Libro respuesta = libroBo.obtenLibroXnumero(numerolibro);
		if(respuesta==null){
			throw new NotariaException("No se pudo localizar el libro inicial solicitado, consulte a sistemas");
		}
		return respuesta;
	}
	
	

}


