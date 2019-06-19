package com.palestra.notaria.bo.impl;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.palestra.notaria.bo.ComponenteBo;
import com.palestra.notaria.bo.ConFormularioBo;
import com.palestra.notaria.bo.ConSubFormularioBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.dao.ConFormularioDao;
import com.palestra.notaria.dao.ConSubFormularioDao;
import com.palestra.notaria.dao.impl.ConFormularioDaoImpl;
import com.palestra.notaria.dao.impl.ConSubFormularioDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoFormulario;
import com.palestra.notaria.modelo.Componente;
import com.palestra.notaria.modelo.ConFormulario;
import com.palestra.notaria.modelo.ConFormularioPK;
import com.palestra.notaria.modelo.ConSubFormulario;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.PermisoRol;
import com.palestra.notaria.util.FormularioDinamicoListaPredeterminada;
import com.palestra.notaria.util.GeneradorId;

public class ConFormularioBoImpl extends GenericBoImpl<ConFormulario> implements
		ConFormularioBo {

	private ConFormularioDao conFormularioDao;

	public ConFormularioBoImpl() {
		this.conFormularioDao = new ConFormularioDaoImpl();
		super.dao = this.conFormularioDao;
	}

	@Override
	public ConFormulario buscarFormularioCompleto(ConFormularioPK id)throws NotariaException {
		return this.conFormularioDao.buscarFormularioCompleto(id);
	}

	@Override
	public Integer buscarUltimaVersion(String idconformulario)throws NotariaException {
		return this.conFormularioDao.buscarUltimaVersion(idconformulario);
	}

	@Override
	public boolean publicarFormulario(String idformulario, Integer version)throws NotariaException {
		return this.conFormularioDao.publicarFormulario(idformulario, version);
	}

	@Override
	public boolean elimninarFormulario(String idformulario, Integer version)throws NotariaException {
		return this.conFormularioDao.elimninarFormulario(idformulario, version);
	}

	@Override
	public List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto)throws NotariaException {
		return this.conFormularioDao.formulariosPorExpediente(idexpediente,
				idacto);
	}

	@Override
	public List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String intipoform) throws NotariaException {
		return this.conFormularioDao.formulariosPorExpediente(idexpediente,
				idacto, intipoform);
	}

	@Override
	public List<ConFormulario> listarPorActo(String idacto)
			throws NotariaException {
		return this.conFormularioDao.listarPorActo(idacto);
	}

	@Override
	public List<ConFormulario> listarConFormulario() throws NotariaException {
		return conFormularioDao.findAll();
	}

	@Override
	public List<ConFormulario> listarConFormulario(String idSubOperacion) throws NotariaException{
		List<ConFormulario> formularios = conFormularioDao.listarPorActo(idSubOperacion);
		List<ConFormulario> retorno = new ArrayList<>()
				;
		for(ConFormulario formulario:formularios){
			for(ActoFormulario actoFormulario: formulario.getListaActoFormularios()){
				if(actoFormulario.getSuboperacion().getIdsuboperacion().equals(idSubOperacion)){
					formulario.setListaComponentes(null);
					formulario.setListaPermisosRol(null);
					formulario.setListaSubFormularios(null);
					retorno.add(formulario);					
				}else{
					// NOTHING TO DO
				}
				break;
			}
		}
		return retorno;
	}
	
	@Override
	public ConFormulario findByName(String nombrecorto) throws NotariaException{
		return this.conFormularioDao.findByName(nombrecorto);
	}
	
//	private Map<String, String> devuelveLista(String listado, String acto)throws NotariaException{
//		Map<String, String> lista = new TreeMap<String, String>();
//		try{
//			//lectura del XML para extraer el listado
//			String hql = FormularioDinamicoListaPredeterminada.extraerHQL(listado);
//			//Realiza la consulta
//			lista = conFormularioDao.recuperaListaPredeterminada(hql, acto);
//			//itera los resultado y devuelve el listado
//			return lista;
//		}catch(ParserConfigurationException | SAXException | IOException e){
//			e.printStackTrace(System.out);
//			throw new NotariaException("Ocurrio un error al tratar de recuperar la consulta predeterminada.", e);
//		}
//	}

	@Override
	public ConFormulario buscarFormularioCompleto(ConFormularioPK id, Acto acto)
			throws NotariaException {
		ConFormulario conFormulario = buscarFormularioCompleto(id);
		if(conFormulario!=null){
			//solo usaremos el Acto en caso de que se tenga configurada una lista predeterminada:
			for(Componente componente: conFormulario.getListaComponentes()){
				if(componente.getDstablabusqueda()!=null && !componente.getDstablabusqueda().isEmpty()){
					//se localizan los valores a partir del HQL
					Map<String,String> mapa = null;
					try{
						String hql = FormularioDinamicoListaPredeterminada.extraerHQL(componente.getDstablabusqueda());
						if(hql!=null){
							mapa = conFormularioDao.recuperaListaPredeterminada(hql, acto.getIdacto());
						}
					}catch(IOException e){
						e.printStackTrace(System.out);							
					}catch(SAXException e){
						e.printStackTrace(System.out);
					}catch(ParserConfigurationException e){
						e.printStackTrace(System.out);
					}
					 
					if(mapa!=null && !mapa.isEmpty()){
						StringBuilder listaValores = new StringBuilder();
						for(String key:mapa.keySet()){
							listaValores.append(mapa.get(key)).append("::").append(mapa.get(key)).append(";;");
						}
						componente.setDslistavalores(listaValores.substring(0, listaValores.length()-2));
					}else{
						componente.setDslistavalores("empty::(no se encontraron valores)");
					}
				}else{
					//nada por hacer ....
				}
			}
			for(ConSubFormulario subFormulario:conFormulario.getListaSubFormularios()){
				for(Componente componente: subFormulario.getListaComponentes()){
					if(componente.getDstablabusqueda()!=null && !componente.getDstablabusqueda().isEmpty()){
						//se localizan los valores a partir del HQL
						Map<String,String> mapa = null;
						try{
							String hql = FormularioDinamicoListaPredeterminada.extraerHQL(componente.getDstablabusqueda());
							if(hql!=null){
								mapa = conFormularioDao.recuperaListaPredeterminada(hql, acto.getIdacto());
							}
						}catch(IOException e){
							e.printStackTrace(System.out);							
						}catch(SAXException e){
							e.printStackTrace(System.out);
						}catch(ParserConfigurationException e){
							e.printStackTrace(System.out);
						}
						 
						if(mapa!=null && !mapa.isEmpty()){
							StringBuilder listaValores = new StringBuilder();
							for(String key:mapa.keySet()){
								listaValores.append(key).append("::").append(mapa.get(key)).append(";;");
							}
							componente.setDslistavalores(listaValores.substring(0, listaValores.length()-2));
						}else{
							componente.setDslistavalores("empty::(no se encontraron valores)");
						}
					}else{
						//nada por hacer ....
					}
				}
			}
			return conFormulario;
		}else{
			return null;
		}
	}

	@Override
	public List<ActoFormulario> formulariosPorExpediente(String idexpediente,
			String idacto, String tipoform, ElementoCatalogo locacion)
			throws NotariaException {
		List<ActoFormulario> formularios = formulariosPorExpediente(idexpediente, idacto, tipoform);
		List<ActoFormulario> filtrados = new ArrayList<ActoFormulario>();
		for(ActoFormulario actoFormulario : formularios){
			if(actoFormulario.getConFormulario().getLocacion().getIdelemento().equals(locacion.getIdelemento())){
				filtrados.add(actoFormulario);
			}
		}
		return filtrados;
	}
	
	@Override
	public ConFormulario save(ConFormulario formulario) throws NotariaException{
		if(!esDuplicado(formulario)){
			return super.save(formulario);
		} else {
			throw new NotariaException("Al parecer el formulario dinámico esta duplicado, ya existe uno con este nombre corto y localidad.");
		}
	}
	
	@Override
	public String actualizaFormulario(ConFormulario formulario) throws NotariaException {
		StringBuilder mensaje = new StringBuilder("Se ha actualizado con exito el formulario dinámico.");
		try{
			ConFormulario actual = buscarFormularioCompleto(formulario.getId());
			boolean esDuplicado = esDuplicado(formulario);
			if(actual!=null && !esDuplicado){
				//actualiza los valores de los componentes nuevos.
				int agrego = agregaComponentes(formulario.getListaComponentes(), formulario);
				//se elimino algun componente, lo buscamos y lo quitamos de la UP
				int quito = quitarComponentes(formulario.getListaComponentes(), actual.getListaComponentes(),formulario);	
				quito += quitarComponentesSubFormulario(formulario.getListaSubFormularios(), actual.getListaSubFormularios());
				actual = buscarFormularioCompleto(formulario.getId()); // se busca la nueva versión para trabajar sobre esta
				//actualiza los actos:
				int iActoFormulario = conFormularioDao.removeAllActoFormulario(actual.getId());				
				//actualiza los roles:
				PermisoRolBoImpl boRol = new PermisoRolBoImpl();
				int iRolFormulario = boRol.eliminaTodos(actual.getId());
									
				actual.setDsdescripcion(formulario.getDsdescripcion());
				actual.setDsnombrecorto(formulario.getDsnombrecorto());
				actual.setDstitulo(formulario.getDstitulo());
				actual.setLocacion(formulario.getLocacion());
				actual.setTipoform(formulario.getTipoform());
				for(ActoFormulario af: formulario.getListaActoFormularios()){
					if(af.getIdactoformulario()!=null && !af.getIdactoformulario().isEmpty()){
						System.out.println("ActoFormulario para actualizar: "+af.getIdactoformulario());
					} else {
						af.setIdactoformulario(GeneradorId.generaId(af));
						af.setIdsesion(formulario.getIdsesion());
						af.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						af.setInestatus("");					
						System.out.println("ActoFormulario para registrar: "+af.getIdactoformulario());
					}
					af.setConFormulario(actual);
				}
				actual.setListaActoFormularios(formulario.getListaActoFormularios());
				for(Componente c: formulario.getListaComponentes()){
					if(c.getIdcomponente()!=null && !c.getIdcomponente().isEmpty()){
						System.out.println("Componente para actualizar: "+c.getIdcomponente());
					} else {
						c.setIdcomponente(GeneradorId.generaId(c));
						c.setIdsesion(formulario.getIdsesion());
						c.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						System.out.println("Componente para registrar: "+c.getIdcomponente());
					}
					c.setConFormulario(actual);
				}
				actual.setListaComponentes(formulario.getListaComponentes());
				for(PermisoRol r: formulario.getListaPermisosRol()){
					if(r.getIdpermisorol() !=null && !r.getIdpermisorol().isEmpty()){
						System.out.println("Rol para actualizar: "+r.getIdpermisorol());
					} else {
						r.setIdpermisorol(GeneradorId.generaId(r));
						r.setIdsesion(formulario.getIdsesion());
						r.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						System.out.println("Rol para registrar: "+r.getIdpermisorol());
					}
					r.setConFormulario(actual);
				}
				actual.setListaPermisosRol(formulario.getListaPermisosRol());
				ConSubFormularioBo boSubForm = new ConSubFormularioBoImpl();
				
				for(ConSubFormulario s: formulario.getListaSubFormularios()){
					if(s.getIdconsubform() !=null && !s.getIdconsubform().isEmpty()){
						System.out.println("Componente para actualizar: "+s.getIdconsubform());
					} else {
						s.setIdconsubform(GeneradorId.generaId(s));
						s.setIdsesion(formulario.getIdsesion());
						s.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
						
						System.out.println("Subformulario para registrar: "+s.getIdconsubform());
					}

					for(Componente c: s.getListaComponentes()){
						//c.setConFormulario(actual);
						c.setIsparasubform(true);
						c.setSubformulario(s);
						c.setIdsesion(formulario.getIdsesion());							
					}
					s.setConFormulario(actual);						
				}
				actual.setListaSubFormularios(formulario.getListaSubFormularios());
				actual.setIdsesion(formulario.getIdsesion());
				actual.setIspublicado(true);
				actual = update(actual);
				mensaje.append("Se ha actualizado el formulario dinámico correctamente ["+ (agrego+quito+iActoFormulario+iRolFormulario) +"].");
				return mensaje.toString();
			}else if(actual==null) {
				throw new NotariaException("El formulario no ha sido localizado en la unidad de persistencia.");
			} else if(esDuplicado){
				throw new NotariaException("Al parecer ya existe un formulario con este nombre para la localidad " +actual.getLocacion().getDselemento());
			} else {
				throw new NotariaException("No se ha logrado realizar la actualización del formulario, no se localizao en la unidad de persistencia/es duplicado.");
			}
			
			
		} catch (NullPointerException e){
			throw new NotariaException("El formulario no ha sido localizado en la unidad de persistencia [NULL]");
		}
		
	}
	
	/***
	 * La formula para evaluar duplicidad en formularios dinámicos es a través de la localidad y el nombre corto
	 * debído a que las plantillas para escritura tienen como identificador de varibales estos elementos.
	 * <br />El sistema permite que una plantilla pertenezca a una localidad y varios actos, por lo que se permite que
	 * el formulario tenga el mismo nombre corto para varios actos con la misma localidad y se deja la responsabilidad al usuario
	 * su integración con la plantilla para que no se dupliquen los nombres de las variables, considerando que el identificador de la variable
	 * se arma por el nombre corto del formulario y el nombre del componente <i>antecedentes[notario_nombre]<i>; En este caso,
	 * si existe este formulario para los actos CH, CV, PVC y OC, y se usa una plantilla que combine CH y CV, entonces el sistema sustituirá
	 * las variables con el valor del formulario ya capturado del primer acto que se definió al elaborar la escritura y puede generarse un 
	 * resultado no esperado por el usuario.
	 * @param formulario ConFormulario modelo que representa al Formulario dinámico
	 * @return
	 * @throws NotariaException
	 */
	private boolean esDuplicado(ConFormulario formulario) throws NotariaException{
		ElementoCatalogo localidad = formulario.getLocacion();
		String nombreCorto = formulario.getDsnombrecorto();
		List<ConFormulario> duplicados = conFormularioDao.localizaDuplicado(nombreCorto, localidad);
		if(duplicados.size()>1){
			return true;
		}else if(duplicados.size()==1 && formulario.getId()!=null){
			//solo validamos que el ID sea el mismo:
			ConFormulario duplicado = duplicados.get(0);
			if(duplicado.getId().getIdconFormulario().equals(formulario.getId().getIdconFormulario()) 
			&& duplicado.getId().getVersion().equals(formulario.getId().getVersion())){
				return false;
			}else{
				return true;
			}
			
		} else {
			return false;
		}
	}
	
//	private List<ConFormulario> verDuplicados(ConFormulario formulario)throws NotariaException{
//		ElementoCatalogo localidad = formulario.getLocacion();
//		String nombreCorto = formulario.getDsnombrecorto();
//		List<ConFormulario> duplicados = conFormularioDao.localizaDuplicado(nombreCorto, localidad);
//		//quitamos el formulario enviado si es que tiene un id asociado
//		if(formulario.getId()!=null){
//			List<ConFormulario> nuevaLista = new ArrayList<ConFormulario>();
//			for(ConFormulario f:duplicados){
//				if(!f.getId().getIdconFormulario().equals(formulario.getId().getIdconFormulario())){
//					nuevaLista.add(f);
//				}
//			}
//			return nuevaLista;
//		}else{
//			return duplicados;
//		}
//	}
	
	@Override
	public String testActualizaFormulario(ConFormulario formulario) throws NotariaException{
		StringBuilder mensaje = new StringBuilder("=====> Mensaje:\n");
		try {						
			
			ConFormulario actual = buscarFormularioCompleto(formulario.getId());
			if(actual!=null) {
				// primero busca los cambios en cuanto a componentes, del actual vs el nuevo
				for(Componente componente:formulario.getListaComponentes()){
					boolean hayComponente = false;
					for(Componente actualComponente	:actual.getListaComponentes()){
						if(componente.getIdcomponente().equals(actualComponente.getIdcomponente())){
							hayComponente = true;
							mensaje.append("\n    Analizando la variable ").append(actualComponente.getDsnombrevariable());
							if(componente.getTipocomponente().getDscodigo().equals(actualComponente.getTipocomponente().getDscodigo())){
								if(componente.getIsparasubform()==actualComponente.getIsparasubform()){
									if((componente.getDscatalogo()==null && actualComponente.getDscatalogo()==null) 
									|| (componente.getDslistavalores()==null && actualComponente.getDslistavalores()==null)){
										mensaje.append("\n    No contiene catalogo/lista de valores.");
									} else if(!(componente.getDscatalogo().equals(actualComponente.getDscatalogo()))  
										|| !(componente.getDslistavalores().equals(actualComponente.getDslistavalores()))){
											mensaje.append("\n    El catalogo/lista valores tiene cambios: ").append(componente.getDsnombrevariable()).append(" a ").append(actualComponente.getDsnombrevariable());										
									} else {
										mensaje.append("\n    Los valores de lista/tabla predeterminada no son coincidentes, se encontraron diferencias.");
										mensaje.append(String.format("\n\t\t%s - %s", componente.getDslistavalores(), actualComponente.getDslistavalores()));
										mensaje.append(String.format("\n\t\t%s - %s", componente.getDscatalogo(), actualComponente.getDscatalogo()));										
									}
									if(componente.getDsnombrevariable().equals(actualComponente.getDsnombrevariable())){
										mensaje.append("\n    No se localizaron cambios.");
										 
									}else{
										mensaje.append("\n    El nombre de la variable cambio de: ").append(componente.getDsnombrevariable()).append(" a ").append(actualComponente.getDsnombrevariable());
									}
								} else {
									mensaje.append("\n    Es el mismo componente pero en subformulario.");
								}
							}else{
								mensaje.append("\n    Camnbio el tipo de componente de: ").append(componente.getTipocomponente()).append(" a ").append(actualComponente.getTipocomponente());
							}
						}
					}
					if(!hayComponente){
						mensaje.append("\n    No se ha localizado el componente ").append(componente.getDsnombrevariable());
						
					}
				}
				// 			se busca los expedientes sin MASTER con este formulario
				FormularioBo bo = new FormularioBoImpl();
				List<Formulario> formularios = bo.findByConFormulario(actual.getId());
				if(formularios.isEmpty()){
					// no hay expedientes asociados, por lo que se pueden aplicar los cambios sin afectación a expedientes.
					mensaje.append("\n    No existen expedientes asociados al formulario, por lo que se pueden aplicar los cambios sin tener afectación a expedientes.");
				} else {
					mensaje.append("\n    Los siguientes expedientes están trabajando con el formulario, se verán afectados en cuanto a los datos ya capturados.");
					for(Formulario form:formularios){
						mensaje.append("\n\t\tSe localizo el expediente:::").append(form.getActo().getExpediente().getNumexpediente());
					}
				}
			} else {
				throw new NotariaException("El formulario no ha sido localizado en la unidad de persistencia.");
			}
			System.out.println(mensaje.toString());		
			return mensaje.toString();
		} catch (NullPointerException e){
			throw new NotariaException("El formulario no ha sido localizado en la unidad de persistencia [NULL]");
		}	

	}

	private int agregaComponentes(List<Componente> nueva, ConFormulario formulario) throws NotariaException {
		int contador = 0;
		ComponenteBo componenteBo = new ComponenteBoImpl();
		for(Componente componente: nueva){
			Componente persistido = componenteBo.findById(componente.getIdcomponente()==null?"":componente.getIdcomponente());
			if(persistido==null){ //no existe, habrà que agregarlo
				componente.setIdsesion(formulario.getIdsesion());	
				componente.setConFormulario(formulario);
				componente.setIdcomponente(GeneradorId.generaId(componente));				
				componente = componenteBo.save(componente);
				contador++;
			}
		}
		//valida la existencia de subformularios:
		if(formulario.getListaSubFormularios().size()>0){
			List<ConSubFormulario> subForms = formulario.getListaSubFormularios();
			ConSubFormularioBo boSubForm = new ConSubFormularioBoImpl();
			List<ConSubFormulario> actualSubFrm = boSubForm.findByFormulario(formulario.getId());
			
			int tmppos = 0;
			for (ConSubFormulario csf:actualSubFrm){
				csf.setDsnombrecorto("tmp_"+ System.currentTimeMillis()/1000+tmppos);
				tmppos +=1;
				boSubForm.update(csf);
			}
			
			
			//BUS	CO QUE SUBFORMULARIOS NO EXISTEN PARA BORRARLOS
			if(actualSubFrm.size()!=subForms.size()){
				for (ConSubFormulario csf: actualSubFrm){
					boolean ExisteSubfrm = false;
					for(ConSubFormulario fcsf:subForms){
						if(fcsf.getIdconsubform().length()<32 || fcsf.getIdconsubform()==null){
							break;
						}else{
							if(fcsf.getIdconsubform().equals(csf.getIdconsubform())){
								ExisteSubfrm = true;
								break;
							}
						}
					}
					if(!ExisteSubfrm){
						boSubForm.borrar(csf);
					}
				}
			}
			
			
			
			for(ConSubFormulario subFormulario:subForms){

				//subFormulario.setIdconsubform(boSubForm.obtenerIdConSubFormByShortName(subFormulario.getDsnombrecorto()));
				subFormulario.setIdsesion(formulario.getIdsesion());
				subFormulario.setTmstmp(new Timestamp(Calendar.getInstance().getTimeInMillis()));
				subFormulario.setConFormulario(formulario);

				if(subFormulario.getIdconsubform()==null || subFormulario.getIdconsubform().length()<32){
					
					List<Componente> tmplistaComponentes = new ArrayList<Componente>();
					tmplistaComponentes.addAll(subFormulario.getListaComponentes());
					subFormulario.setListaComponentes(null);
					subFormulario.setIdconsubform(GeneradorId.generaId(subFormulario));

					boSubForm.save(subFormulario);
					subFormulario.setListaComponentes(tmplistaComponentes);
					
					System.out.println("Subformulario para registrar: "+subFormulario.getIdconsubform());
				}
				for(Componente componente:subFormulario.getListaComponentes()){	
					if(componente.getIdcomponente()==null || componente.getIdcomponente().contains("new_")){ //se entiende que es nuevo
						
						componente.setIdcomponente(GeneradorId.generaId(componente));
						System.out.println("SUBFORMULARIO"+subFormulario.getDsnombrecorto());
						
						componente.setSubformulario(subFormulario);
						componente.setIdsesion(formulario.getIdsesion());
						componenteBo.save(componente);
						contador++;
					} else {
						//se actualiza el valor y esto no se hace aquí
					}
				}
				
				boSubForm.update(subFormulario);
				
			}
			
			
			
		}
		return contador;
	}
	
	private int quitarComponentes(List<Componente> nueva, List<Componente> actual,ConFormulario formulario) throws NotariaException {
		
		ComponenteBo componenteBo = new ComponenteBoImpl();
		List<Componente> noEstan = new ArrayList<Componente>();
		boolean existe;
		for(Componente actualComponente:actual){
			existe = false;
			for(Componente componente:nueva){
				if(componente.getIdcomponente().equals(actualComponente.getIdcomponente())){
					existe = true;
					break;
				}
			}
			if(!existe){
				Componente persistido = componenteBo.findById(actualComponente.getIdcomponente()==null?"":actualComponente.getIdcomponente());
				noEstan.add(persistido);
			}
		}
		for(Componente noEsta:noEstan){
			componenteBo.eliminaComponente(noEsta,formulario);
		}
		return noEstan.size();
	}
	
	private int quitarComponentesSubFormulario(List<ConSubFormulario> nueva, List<ConSubFormulario> actual) throws NotariaException {
		int suma = 0;
		for(ConSubFormulario subFormularioActual:actual){
			for(ConSubFormulario subFormulario:nueva){
				if(subFormularioActual.getIdconsubform().equals(subFormulario.getIdconsubform())){
					suma+=quitarComponentes(subFormulario.getListaComponentes(), subFormularioActual.getListaComponentes(),null);
				}
			}
		}
		return suma;
	}
	
	@Override
	public List<ConSubFormulario> listaConSubFormularios(ConFormularioPK idConFormulario) throws NotariaException{
		if(idConFormulario!=null){
			if(idConFormulario.getIdconFormulario()!=null && idConFormulario.getVersion()!=null){
				ConSubFormularioDao dao = new ConSubFormularioDaoImpl();
				return dao.findByIdConFormulario(idConFormulario);
			}
		}
		throw new NotariaException("El identificador del formulario no es valido");					
	}
	
}