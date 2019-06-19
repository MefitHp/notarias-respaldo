package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.rmi.CORBA.Util;

import org.bonitasoft.engine.bpm.contract.ContractViolationException;
import org.bonitasoft.engine.bpm.flownode.FlowNodeExecutionException;
import org.bonitasoft.engine.bpm.flownode.UserTaskNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessActivationException;
import org.bonitasoft.engine.bpm.process.ProcessDefinitionNotFoundException;
import org.bonitasoft.engine.bpm.process.ProcessExecutionException;
import org.bonitasoft.engine.exception.BonitaHomeNotSetException;
import org.bonitasoft.engine.exception.ServerAPIException;
import org.bonitasoft.engine.exception.UnknownAPITypeException;
import org.bonitasoft.engine.exception.UpdateException;
import org.bonitasoft.engine.platform.LoginException;

import com.palestra.notaria.bo.ActoBo;
import com.palestra.notaria.bo.ActoDocumentoBo;
import com.palestra.notaria.bo.BitacoraEscrituraBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.bo.LibroBo;
import com.palestra.notaria.bo.MesaCtrlBo;
import com.palestra.notaria.bo.PizarronElementoBo;
import com.palestra.notaria.dao.ActoDao;
import com.palestra.notaria.dao.EscrituraDao;
import com.palestra.notaria.dao.ProcessActoDao;
import com.palestra.notaria.dao.TareaProcessActoDao;
import com.palestra.notaria.dao.impl.EscrituraDaoImpl;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.enums.EnumStatusDoc;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.BitacoraEscritura;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.Libro;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.PizarronElemento;
import com.palestra.notaria.modelo.ProcessActo;
import com.palestra.notaria.modelo.TareaProcessActo;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;
import com.palestra.notaria.util.VerificaEscritura;

import pojos.pojos.BonitaCommonBean;
import pojos.pojos.Impuesto;
import utiles.procesos.BonitaUtilidades;




public class EscrituraBoImpl extends GenericBoImpl<Escritura> implements
		EscrituraBo {
	
	
	private EscrituraDao escrituraDao;
	private ActoDao actoDao;
	private ProcessActoDao processActoDao;
	private TareaProcessActoDao tareaProcessActoDao;
	
	public EscrituraBoImpl() {
		this.escrituraDao = new EscrituraDaoImpl();
		super.dao = this.escrituraDao;
	}
	
	
	
	/*20170521 VICTOR: SE COMENTA ESTE MÉTODO POR QUE BONITA INICIARÁ DESDE LA APERTURA DEL ACTO*/

	/*
	@Override 
	public void iniciaProcesoEscritura(Usuario usr,Escritura escritura)throws NotariaException{
		String usrBonita = usr.getCdusuario();
		BonitaUtilidades bonitaUtilidades;
		try {
			EscrituraActoBo esactBo = new EscrituraActoBoImpl();
			ActoDocumentoBo actdocBo = new ActoDocumentoBoImpl();
			
			bonitaUtilidades = new BonitaUtilidades(usrBonita,usrBonita);
			pojos.pojos.Escritura esc = new pojos.pojos.Escritura();
			esc.setEscritura(escritura.getDsnumescritura());
			esc.setIdescritura(escritura.getIdescritura());
			esc.setExpediente(escritura.getExpediente().getNumexpediente());
			esc.setIdexpediente(escritura.getExpediente().getIdexpediente());
			esc.setReferencia(escritura.getExpediente().getDsreferencia());
			esc.setIdusuario(usr.getIdusuario());
			esc.setIdsesionactual(usr.getIdsesionactual());
			List<EscrituraActo> escactList =esactBo.findByEscrituraId(escritura.getIdescritura());
			List<Impuesto> impuestos = new ArrayList<Impuesto>();
			Impuesto imp = null;
			for(EscrituraActo escact: escactList){
				List<DatoActoDocumento> posteriores = actdocBo.obtenerPosteriores(escritura.getExpediente().getIdexpediente(), escact.getActo().getIdacto()); 
				
				
				for(DatoActoDocumento dad: posteriores){
					if(dad.getNombre().equals("DIM (pago)")){
						esc.setHasdim(true);
						esc.setIddimdoc(dad.getIdactodoc());
						continue;
					}
					imp = new Impuesto();
					imp.setActonombre(escact.getActo().getDsnombre());
					imp.setIdacto(escact.getActo().getIdacto());
					imp.setEscritura(escact.getEscritura().getDsnumescritura());
					imp.setIdexpediente(esc.getIdexpediente());
					imp.setExpediente(esc.getExpediente());
					imp.setDocumentotipo(dad.getNombre());
					imp.setIdactodoc(dad.getIdactodoc());
					imp.setIspagoRequire(dad.getIspagorequire());
					
					System.out.println("******************************************");
					System.out.println(dad.getNombre());
					System.out.println(dad.getIspagorequire());
					System.out.println("******************************************");
					impuestos.add(imp);
				}				
			}
			esc.setImpuestos(impuestos);
		
		try {
				bonitaUtilidades.bonitaNewProcess(esc);
			} catch (ProcessDefinitionNotFoundException | ProcessActivationException | ProcessExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				this.undoescritura(escritura);
			}
			
		} catch (BonitaHomeNotSetException | ServerAPIException | UnknownAPITypeException | LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			this.undoescritura(escritura);
			
		} catch (Exception e) {
			e.printStackTrace();
			this.undoescritura(escritura);
		}
	}*/
	
	@Override
	public List<Escritura> findByExpedienteId(String id)throws NotariaException {
		return this.escrituraDao.findByExpedienteId(id);
	}
	
	@Override
	public String obtenNumEscritura(String id) throws NotariaException{
		return this.escrituraDao.obtenNumEscritura(id);
	}
	
	@Override
	public Escritura buscarPorIdCompleto(Escritura escritura)throws NotariaException {
		return this.escrituraDao.buscarPorIdCompleto(escritura);
	}
	
	@Override
	public Boolean registrarEscritura(Escritura escritura,
			List<EscrituraActo> actosDeEscritura) throws NotariaException{
		return this.escrituraDao.registrarEscritura(escritura, actosDeEscritura);
	}

	@Override
	public Boolean actualizarActosNotario(Escritura escritura,
			List<EscrituraActo> actosDeEscritura)throws NotariaException {
		return this.escrituraDao.actualizarActosNotario(escritura, actosDeEscritura);
	}
	
	@Override
	public Boolean actualizarPropsEscritura(Escritura escritura, String idActo, Usuario usuario)throws NotariaException {
		VerificaEscritura.nopaso(escritura.getIdescritura());
		
		/*if(escritura.getLibro()!=null){
			LibroBo libBo = new LibroBoImpl();
			Libro libro = libBo.obtenLibroXnumero(escritura.getLibro().getInnumlibro());
			if(libro == null){
				libro = new Libro();
				libro.setDsdescripcion("libro desde el sistema creado el dia:"+new Date());
				libro.setFecha(new Date());
				libro.setIdsesion(escritura.getIdsesion());
				libro.setInfolioinicial(1L);
				libro.setInnumlibro(escritura.getLibro().getInnumlibro());
				libro.setIdlibro(GeneradorId.generaId(libro));
				libBo.save(libro);
				escritura.setLibro(libro);
			}
			}*/
		PizarronElementoBo pizelbo = new PizarronElementoBoImpl();
		PizarronElemento pizarron = pizelbo.buscarXescritura(escritura.getDsnumescritura());
		pizarron.setStatus("lib-proceso");
//		actualizaNumEscrituraBonita(escritura, idActo, usuario);
		return this.escrituraDao.actualizarPropsEscrituraPizarron(escritura, pizarron);
	}
	
	@Override
	public void saveNumEscrituraBonita(BonitaCommonBean bonitaBean){
		
	}
	
	public void actualizaNumEscrituraBonita(Escritura escritura, String idActo, Usuario usuario) throws NotariaException{
		Operacion operacion = actoDao.getOperacionPorActo(idActo);
		if(operacion.getDsnombre().equalsIgnoreCase("compraventa")){
			//obtener id proceso de actoproceso
			ProcessActo processActo = new ProcessActo();
			Acto acto = new Acto(idActo);
			processActo = processActoDao.findByActo(acto);
			TareaProcessActo tareaProcessActo = tareaProcessActoDao.getActiveByActo(acto);
//			
//			BonitaCommonBean commonBean = new BonitaCommonBean();
//			commonBean.setIdActo(formulario.getActo().getIdacto());
//			commonBean.setIdproceso(processActo.getIdproceso());
//			commonBean.setIdtarea(processActo.getIdtarea());
			try {
				BonitaUtilidades bu = new BonitaUtilidades(usuario.getCdusuario(), usuario.getCdusuario());
				bu.bonitaAssignTaskActualSession(tareaProcessActo.getIdtarea());
//				Map<String, Serializable> mapBonita = new HashMap<String,Serializable>();
//				mapBonita.put("procesodato", commonBean);
				bu.bonitaExcecuteTask(tareaProcessActo.getIdtarea());
			} catch (BonitaHomeNotSetException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (ServerAPIException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UnknownAPITypeException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (LoginException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UserTaskNotFoundException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (FlowNodeExecutionException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (ContractViolationException e) {
				e.printStackTrace();
				throw new NotariaException(e.getCause());
			} catch (UpdateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public String getExpedienteIdByEscrituraId(String id)throws NotariaException {
		return this.escrituraDao.getExpedienteIdByEscrituraId(id);
	}
	
	@Override
	public Boolean setFirmaDittoByEscrituraId(String id, Boolean isfirmaditto) throws NotariaException{
		return this.escrituraDao.setFirmaDittoByEscrituraId(id,isfirmaditto);
	}
	
	@Override
	public Boolean eliminarEscritura(String id)throws NotariaException {
		return this.escrituraDao.eliminarEscritura(id);
	}
	
	@Override
	public Boolean tieneNumEscritura(String idEscritura) throws NotariaException{
		return this.escrituraDao.tieneNumEscritura(idEscritura);
	}
	
	@Override
	public Boolean switchNotario(Escritura escritura)throws NotariaException {
		return this.escrituraDao.switchNotario(escritura);
	}

	@Override
	public String obtenerIdNotarioPorEscrituraId(String idEscritura)throws NotariaException {
		return this.escrituraDao.obtenerIdNotarioPorEscrituraId(idEscritura);
	}
	
	@Override
	public Escritura getByNumeroEscritura(String numesc) throws NotariaException{
		return this.escrituraDao.getByNumeroEscritura(numesc);
	}

	@Override
	public void nopaso(Escritura escritura,Usuario usr) throws NotariaException {
		Escritura esc = this.escrituraDao.findById(escritura.getIdescritura());
		if(esc.getDsnumescritura()==null || esc.getDsnumescritura().isEmpty()){
			throw new NotariaException("La escritura no tiene ningún número asignado");
		}
		
				
		esc.setNopaso(escritura.getNopaso());
		MesaCtrlBo msbo = new MesaCtrlBoImpl();
		MesaCtrl ms = new MesaCtrl();
		ms.setEscritura(esc);
		ms.setIdsesion(usr.getIdsesionactual());
		ms.setEstatusdoc(EnumStatusDoc.NO_PASO);
		ms.setIspagorequire(false);
		ms.setIdmesacontrol(GeneradorId.generaId(ms));

		

		
		msbo.save(ms);

		this.escrituraDao.update(esc);
	}

	@Override
	public void verificaDimAnexo5(ArrayList<DatoActoMultiSelect> actos) throws NotariaException {
		ActoBo actoBo = new ActoBoImpl();
		
		for(DatoActoMultiSelect acto : actos){
			actoBo.validaDimAnexo5(acto.getIdacto());
		}
		
		
	}
	// EN CASO DE QUE BONITA FALLE NO SE ASIGNA NÚMERO NI INICIA EL PROCESO DE LA ESCRITURA
	private void undoescritura(Escritura escritura) throws NotariaException{
		// TODO Auto-generated catch block
					PizarronElementoBo pizelbo = new PizarronElementoBoImpl();
					PizarronElemento pizarron = pizelbo.buscarXescritura(escritura.getDsnumescritura());
					pizarron.setStatus("lib-pendiente");
					escritura.setDsnumescritura(null);
					this.escrituraDao.actualizarPropsEscrituraPizarron(escritura, pizarron);
					throw new NotariaException("No se logró asignar número de escritura, algo malo pasó en la unidad BPM. Contacte a Sistemas");
	}

	@Override
	public List<Escritura> getXnumLibroStatus(Long numerolibroInicial, Long numerolibroFinal, Boolean status) throws NotariaException {
		// TODO Auto-generated method stub
		return this.escrituraDao.getXnumLibroStatus(numerolibroInicial,numerolibroFinal,status);
	}


	
	
}
