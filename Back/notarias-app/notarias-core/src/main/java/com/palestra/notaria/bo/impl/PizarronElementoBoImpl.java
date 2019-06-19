package com.palestra.notaria.bo.impl;



import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.palestra.notaria.bo.ControlFoliosBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.bo.UsuarioBO;
import com.palestra.notaria.dao.PizarronElementoDao;
import com.palestra.notaria.dao.impl.PizarronElementoDaoImp;
import com.palestra.notaria.dato.DatoElementoPizarron;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.ControlFolios;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class PizarronElementoBoImpl extends GenericBoImpl<PizarronElemento> implements PizarronElementoBo {

	private PizarronElementoDao pizarronDao;
	
	public PizarronElementoBoImpl() {
		// TODO Auto-generated constructor stub
		this.dao = new PizarronElementoDaoImp(PizarronElemento.class);
		pizarronDao = new PizarronElementoDaoImp(PizarronElemento.class);
		
	}

	@Override
	public void borrar(String idpizarronelemento,Long numeroescritura) throws NotariaException {
		
		EscrituraBo escrituraBo = new EscrituraBoImpl();
		Escritura escritura = escrituraBo.getByNumeroEscritura(numeroescritura.toString());
		
		if(escritura!=null){
			throw new NotariaException("No es posible borrar este elemento,ya tiene una escritura asignada");
		}
		
		Long pe = this.pizarronDao.getUltimaEscritura();
		if(pe.equals(numeroescritura)){
			this.borrar(idpizarronelemento);
		}else{
			throw new NotariaException("El elemento que deseas eliminar no es el último");
		}
		
	}
	
	
	@Override
	public List<DatoElementoPizarron> buscartodos() throws NotariaException {
		
		return this.buscartodos(0,0);
	};
	
	
	@Override
	public List<DatoElementoPizarron> buscartodos(Integer inicio, Integer total) throws NotariaException {		
		ArrayList<PizarronElemento> respuestaDao = (ArrayList<PizarronElemento>) this.pizarronDao.findAll(inicio,total);
		if(respuestaDao !=null){
			ArrayList<DatoElementoPizarron> respuesta = new ArrayList<DatoElementoPizarron>();
			for(PizarronElemento p : respuestaDao){
				DatoElementoPizarron dep = new DatoElementoPizarron(p);
				respuesta.add(dep);
			}
			return respuesta;
		}
		return null;
	}
	
	
	@Override
	public DatoElementoPizarron save(DatoElementoPizarron pizarrondato,Boolean actualizatodo,Usuario usuario)throws NotariaException{
		
		PizarronElemento pizarron = DatoElementoPizarron.creaModelo(pizarrondato);
		pizarron.setIdsesion(usuario.getIdsesionactual());

		UsuarioBO usuarioBo = new UsuarioBoImpl();
		Usuario usr = usuarioBo.findById(usuario.getIdusuario());
		
		/*if(!usr.getRol().getDsprefijo().equals("abog")){
			throw new NotariaException("Solo los abogados tienen permiso de guardar en el pizarron");
		}*/
		
		LibroBo libroBo = new LibroBoImpl();
		Libro libro = null;
		ControlFoliosBo cfbo = new ControlFoliosBoImpl();

		
		if(pizarrondato.getIdpizarronelemento()== null){
			
			libro = libroBo.obtenUltimoLibro();
			Long ultimoelemento = this.getUltimaEscritura();
			ControlFolios cf = cfbo.getUltimo();
			long foliosdisponibles = cf.getFoliosDisponibles() - pizarrondato.getCantidadfolios();
			if(foliosdisponibles < 0 ){
				throw new NotariaException("No hay folios suficientes para completar esta acción, contacte a sistemas");
			}
			//AJUSTO FOLIO
			Long actualizacionfolio = (cf.getFolioActual() + pizarrondato.getCantidadfolios());
			pizarron.setFolioInicial(cf.getFolioActual());
			pizarron.setFolioFinal(actualizacionfolio);
			pizarron.setStatus("lib-pendiente");
			pizarron.setNumeroescritura(ultimoelemento+1);
			
			if(libro !=null){
				pizarron.setIdpizarronelemento(GeneradorId.generaId(pizarron));
				Long cantidadFolios = pizarron.getCantidadfolios();
				pizarron.setFoliolibro(libro.getInfolioinicial());
				pizarron.setIdlibro(libro);
				pizarron.setIscierrelibro(libroBo.validaFoliosLibro(cantidadFolios,null));
			}
			
			
			// Actualizo control de folios con los nuevos numeros
			
			//cfbo.save(newControlFolios);
			// fin de actualizacion de folios
			// AJUSTO FOLIO PARA QUE VAYA AL SIGUIENTE
			ControlFolios newControlFolios = creaControlFolios(foliosdisponibles, (actualizacionfolio), pizarron.getIdsesion());
			pizarron = this.pizarronDao.save(pizarron,newControlFolios);
		}else{		
			// defino las reglas de actualización
			PizarronElemento pizarronanterior = this.pizarronDao.buscarXescritura(pizarron.getNumeroescritura());
			// TODO: Omar 14Sep17: comento la restriccion de lib-pendiente y permito que actualice, mientras que bloqueo la escritura pasada
//			if(pizarronanterior.getStatus().equals("lib-pendiente")){
//				throw new NotariaException("No es posible actualizar este elemento,ya tiene no tiene una escritura asignada");
//			}else 
			if(pizarronanterior.getStatus().equals("lib-paso")){
				throw new NotariaException("No es posible modificar una escritura pasada");
			}else if(pizarronanterior.getStatus().equals("lib-nopaso")){
				throw new NotariaException("No es posible actualizar este elemento,se encuentra como NO PASO");
			}
			
			if(actualizatodo){
//				TODO: omar 28nov17 comento la validacion para poder modificar pizarrones aunque este cerrado el libro
//			      y aunque haya un elemento posterior
//				this.validamodificacion(pizarron.getNumeroescritura());
				libro = libroBo.findById(pizarron.getIdlibro().getIdlibro());
				Long fpiant = pizarronanterior.getCantidadfolios(); 
				Long fpizact = pizarrondato.getCantidadfolios();
//				SI EL PIZARRON ESTA ENTRE 2 PASADAS NO SE PUEDE CAMBIAR NADA
				if(!this.pizarronDao.isPizarronEditable(Integer.valueOf(pizarron.getNumeroescritura().toString()))
						&& !fpiant.equals(fpizact)){
					throw new NotariaException("No es posible actualizar este elemento,se encuentra entre 2 escrituras pasadas");
				}
//				AQUI SE EMPIEZA A HACER EL RECORRIDO DE FECHAS HACIA ADELANTE O ATRAS
				if(!pizarronanterior.getFecha().equals(pizarron.getFecha())){
					Boolean existPosterioresPasados = this.pizarronDao.existenPosterioresPasados(pizarron.getNumeroescritura());
					Timestamp fechaAnterior = this.pizarronDao.obtenerAnteriorPasada(pizarron.getNumeroescritura()).getFecha();
					if(existPosterioresPasados){
						PizarronElemento siguientePasada = this.pizarronDao.obtenerSiguientePasada(pizarron.getNumeroescritura());
						if(pizarron.getFecha().after(siguientePasada.getFecha())){
							throw new NotariaException("La fecha debe ser inferior a la de la siguiente escritura pasada");
						}
					} if(pizarron.getFecha().before(fechaAnterior)){
						throw new NotariaException("La fecha no puede ser menor a la de la última escritura pasada");
					}
//						SE EVALUA SI LAS FECHA SE MODIFICA HACIA ADELANTE O HACIA ATRAS EN LINEA DE TIEMPO PARA SABER SI SE OBTIENEN 
//						PENDIENTES POSTERIORES O PENDIENTES ANTERIORES A LAS ESCRITURAS PASADAS
					List<PizarronElemento> listaPendientes = null;
					Boolean isForwardingDates = null;
					if(pizarron.getFecha().after(pizarronanterior.getFecha())){
//							SI SE CAMBIO LA FECHA Y HAY POSTERIORES PASADOS SE OBTIENE LA LISTA DE PENDIENTES POSTERIORES PARA RECORRER ESAS FECHAS
						Boolean hasPosteriores = null;
						if(existPosterioresPasados){
							hasPosteriores = Boolean.TRUE;
						}else{
							hasPosteriores = Boolean.FALSE;
						}
						listaPendientes = this.pizarronDao.getPendientesAntesDeSiguientePasada(pizarron.getNumeroescritura(),hasPosteriores);
						isForwardingDates = Boolean.TRUE;
					}else if (pizarron.getFecha().before(pizarronanterior.getFecha())){
						listaPendientes = this.pizarronDao.getPendientesAntesDeAnteriorPasada(pizarron.getNumeroescritura());
						isForwardingDates = Boolean.FALSE;
					}
					listaPendientes.add(0,pizarron);
					Timestamp pizdateActualiza = pizarron.getFecha();
					for(int i=0;i<listaPendientes.size();i++){
						PizarronElemento petmp = listaPendientes.get(i);								
						if(pizdateActualiza.after(petmp.getFecha()) && isForwardingDates){
							petmp.setFecha(pizdateActualiza);
						}else if(pizdateActualiza.before(petmp.getFecha()) && !isForwardingDates){
							petmp.setFecha(pizdateActualiza);
						}
						
					}
					this.pizarronDao.update(listaPendientes);

					
				}
				if(!fpiant.equals(fpizact)
//						|| !pizarronanterior.getFecha().equals(pizarron.getFecha())
						|| !pizarronanterior.getStatus().equals(pizarron.getStatus())
						|| !pizarronanterior.getIdabogado().getIdusuario().equals(pizarron.getIdabogado().getIdusuario())){
//					@omarete CALCULO DE AJUSTE
					Long ajusteFolio = fpizact - pizarronanterior.getCantidadfolios();
//					@omarete SI HAY FOLIOS POSTERIORES PASADOS SE HACE EL REAJUSTE ENTRE FOLIOS EXISTENTES
					if(pizarron.getIscierrelibro() && !fpiant.equals(fpizact)){
						throw new NotariaException("Para agregar o quitar folios modifique la escritura anterior a ésta");
					}
					Boolean existPosterioresPasados = this.pizarronDao.existenPosterioresPasados(pizarron.getNumeroescritura());
					if(existPosterioresPasados || 
							this.pizarronDao.buscarMayorXEscritura(pizarron.getNumeroescritura(), "AND pielem.iscierrelibro=true").size()>0
							|| pizarron.getIscierrelibro()){

//						OBTENER SIGUIENTE PIZARRON Y VALIDAR QUE NO ESTE PASADO
						PizarronElemento pizarronSiguiente=pizarronDao.buscarXescritura(pizarron.getNumeroescritura()+1);
						List<PizarronElemento> elementosUpdate = new ArrayList<PizarronElemento>();
						if(!fpiant.equals(fpizact)){
							
							if(pizarronSiguiente.getStatus().equals("lib-paso")){
								throw new NotariaException("No se pueden intercambiar folios con una escritura pasada");
							}
	//						VALIDAR QUE NO SE EXCEDA EL MAXIMO DE INTERCAMBIO ENTRE PIZARRONES
	//						CAMBIO MAXIMO ES IGUAL A LOS FOLIOS DEL SIGUIENTE PIZARRON -1 PARA QUE NO SE QUEDE SIN FOLIOS EL SIG PIZARRON 
							Long cambioMax = pizarronSiguiente.getFolioFinal() - pizarronSiguiente.getFolioInicial() -1;
							if(ajusteFolio>cambioMax){
								throw new NotariaException("No se pueden tomar más folios de los disponibles en la siguiente escritura");
							}
	//						AUMENTAR O RESTAR FOLIOS A PIZARRON SIGUIENTE Y ACTUALIZAR CAMBIOS EN ACTUAL Y SIGUIENTE
	//						EL FOLIO FINAL DEL PIZARRON SIGUIENTE DEBE SER EL MISMO
							pizarronSiguiente.setFolioInicial(pizarronSiguiente.getFolioInicial()+ajusteFolio);
							pizarronSiguiente.setFoliolibro(pizarronSiguiente.getFoliolibro()+ajusteFolio);
							pizarronanterior.setFolioFinal(pizarronanterior.getFolioFinal()+ajusteFolio);
							elementosUpdate.add(pizarronSiguiente);
						}
						pizarronanterior.setStatus(pizarron.getStatus());
						pizarronanterior.setFecha(pizarron.getFecha());
						pizarronanterior.setIdabogado(pizarron.getIdabogado());
						elementosUpdate.add(pizarronanterior);
						this.pizarronDao.update(elementosUpdate);
					}else{
//						@omarete SI NO HAY FOLIOS POSTERIORES PASADOS SE HACE EL CALCULO NORMAL
						ControlFolios cf = cfbo.getUltimo();
						if(pizarron.getFecha().before(
								this.pizarronDao.obtenerAnteriorPasada(pizarron.getNumeroescritura()).getFecha())){
							throw new NotariaException("La fecha no puede ser menor a la de la última escritura pasada");
						}
	//					SE OBTIENEN POSTERIORES 
						
						List<PizarronElemento> posteriores = this.pizarronDao.buscarMayorXEscritura(pizarron.getNumeroescritura(), "");
						posteriores.add(0,pizarron);

						long foliosdisponibles = cf.getFoliosDisponibles() - ajusteFolio;
						
						if(foliosdisponibles < 0 ){
							throw new NotariaException("No hay folios suficientes para completar esta acción, contacte a sistemas");
						}
						
//						Timestamp pizdateActualiza = pizarron.getFecha();
						for(int i=0;i<posteriores.size();i++){
							PizarronElemento petmp = posteriores.get(i);
							// SI ES EL FOLIO QUE ESTOY MODIFICANDO, DEJO EL INICIO TAL CUAL E INCREMENTO LOS POSTERIORES
							if(i>0){
								petmp.setFolioInicial(petmp.getFolioInicial()+ ajusteFolio);
								petmp.setFoliolibro(petmp.getFoliolibro() + ajusteFolio);
							}
							petmp.setFolioFinal(petmp.getFolioFinal()+ ajusteFolio);
						}		
						
						// VALIDO EL ULTIMO FOLIO Y ACTUALIZO EL LIBRO
						posteriores.get(posteriores.size()-1).setIscierrelibro(libroBo.validaFoliosLibro(ajusteFolio,pizarron));
						Long actualizacionfolio = cf.getFolioActual() + ajusteFolio;
						ControlFolios cfnew = creaControlFolios(foliosdisponibles, actualizacionfolio, pizarron.getIdsesion());
						this.pizarronDao.update(posteriores, cfnew);
						return pizarrondato;
					}
				}
			}else{
				// ACTUALIZAR FOLIOS PONER REGLAS
				pizarron = this.dao.update(pizarron);
			}
			
		}
		
		
		return pizarrondato; 
	}


	/*OBTENGO EL ELEMENTO DE LA DECENA PARA SETEARLA EN EL PIZARRON*/
	@Override
	public Integer calculadecena(ArrayList<DatoElementoPizarron> elementos) throws NotariaException {
		DatoElementoPizarron elementoMinimo;
		if(elementos.size()>0){
			elementoMinimo = elementos.get(elementos.size()-1);
		}else{
			throw new NotariaException("No se encontraron elementos guardados en el pizarron para mostrar");
		}
		int numero = (int) (elementoMinimo.getNumeroescritura() % 10);
		return (int) (elementoMinimo.getNumeroescritura() - numero);
	}

	@Override
	public void validaFecha(Timestamp fecha) throws NotariaException {
		if(fecha.before(this.pizarronDao.getFechaMenor())){
			throw new NotariaException("No se puede registrar la escritura, ya existe al menos un registro con una fecha posterior");
		}
	}

	@Override
	public ArrayList<DatoElementoPizarron> buscarpendientes(String idabogado) throws NotariaException {
		ArrayList<PizarronElemento> respuestaDao = (ArrayList<PizarronElemento>) this.pizarronDao.buscarPendientes(idabogado);

		if(respuestaDao !=null){
			ArrayList<DatoElementoPizarron> respuesta = new ArrayList<DatoElementoPizarron>();
			
			for(PizarronElemento p : respuestaDao){
				DatoElementoPizarron dep = new DatoElementoPizarron(p);
				respuesta.add(dep);
			}
			
			return respuesta;
		}
		return null;
	}
	
	@Override
	public Long getUltimaEscritura() throws NotariaException{
		return this.pizarronDao.getUltimaEscritura();
	}
	
	@Override
	public Date getUltomaFecha() throws NotariaException{
		return this.pizarronDao.getFechaMenor();
	}

	@Override
	public PizarronElemento getXLibro(Libro libro) throws NotariaException{
		return this.pizarronDao.getXLibro(libro);
	}

	@Override
	public PizarronElemento buscarXescritura(String dsnumescritura) throws NotariaException {
		long numesc = Long.parseLong(dsnumescritura);
		return this.pizarronDao.buscarXescritura(numesc);
	}

	@Override
	public void borrar(String idpizarronelemento) throws NotariaException {
		this.pizarronDao.borrar(idpizarronelemento);
	}
	
//	TODO: omar 28nov17 comento la validacion para poder modificar pizarrones aunque este cerrado el libro
//    y aunque haya un elemento posterior
//	@Override
//	public boolean validamodificacion(Long numeroescritura) throws NotariaException{

//		List<PizarronElemento> pasadas = this.pizarronDao.buscarMayorXEscritura(numeroescritura, "AND pielem.status='lib-paso'");
//		if(pasadas.size()>0){
//			throw new NotariaException("Ya existe un elemento posterior pasado");
//		}

//		List<PizarronElemento> cierre = this.pizarronDao.buscarMayorXEscritura(numeroescritura, "AND pielem.iscierrelibro=true");
//		if(cierre.size()>0){
//			throw new NotariaException("Ya esta cerrado el libro correspondiente");
//		}
		
//		return true;
//	}
	
	private ControlFolios creaControlFolios(Long foliosdisponibles,Long actualizacionfolio,String idsesion){
		
		ControlFolios respuesta = new ControlFolios();
		respuesta.setActualizacion(new Date());
		respuesta.setFolioActual(actualizacionfolio);
		respuesta.setFoliosDisponibles(foliosdisponibles);
		respuesta.setIdcontrolfolios(GeneradorId.generaId(respuesta));
		respuesta.setIdsesion(idsesion);
		
		return respuesta;
		
	}
	
}
