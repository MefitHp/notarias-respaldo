package com.palestra.notaria.dao.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.fasterxml.jackson.databind.JsonNode;
import com.palestra.notaria.dao.MesaCtrlDao;
import com.palestra.notaria.enums.EnumStatusPago;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.AlertaObjeto;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.MesaCtrl;
import com.palestra.notaria.modelo.Pago;
import com.palestra.notaria.util.GeneradorId;
import com.victor.utils.restconexion.PostGet;

//import alertas.AlertaDto;

public class MesaCtrlDaoImpl extends GenericDaoImpl<MesaCtrl, Integer> implements MesaCtrlDao {

	public MesaCtrlDaoImpl() {
		super(MesaCtrl.class);
		// TODO Auto-generated constructor stub
	}
	
	public final String URL= "http://localhost:8080/alertas/api/v1/alertas";

	
	private Expediente expediente;
	private Acto acto;
			
	@Override
	public void borrar(MesaCtrl mesa)throws NotariaException{
		EntityManager em = factory.createEntityManager();
		em.getTransaction().begin();
		try{
			MesaCtrl mesaTmp = em.find(mesa.getClass(), mesa.getIdmesacontrol());
			if(mesaTmp!=null){
				em.remove(mesaTmp);
			}else{
				System.out.println("=====> The object does not exists in the unit persistence "+mesa.toString());
			}
			em.getTransaction().commit();
		}catch(PersistenceException e){
			e.printStackTrace(System.out);
//			throw new NotariaException(String.format("Something went wrong in persisting data , view server log for details [delete %s at %s].", obj.toString(), dateFormat.format(new Date())), e.getCause());
			throw new NotariaException("Ocurrió un error al eliminar, causado por: "+e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public MesaCtrl save(MesaCtrl mesa)throws NotariaException{

		// Lo conecto al sistema de alertas SOLO PRUEBA URL
				Pago pago  = mesa.getPago();
				//AlertaDto alertaDto = new AlertaDto();
				ActoDocumento actodoc = mesa.getActodocumento();
				/*PostGet cr = new PostGet(URL);
				//alertaDto.setTipoobjeto("POSTERIOR");
				alertaDto.setTipoobjeto(actodoc.getFormatoPdf().getTipoalerta());
				JsonNode respuestaJson = cr.getPost(alertaDto);
				alertaDto= (AlertaDto) PostGet.setMapperJsonToPojo(respuestaJson, AlertaDto.class);
				*/
				//alertaDto = setAlertaRemote(alertaDto,actodoc.getFormatoPdf().getTipoalerta());
				//AlertaObjeto alertaObjeto= new AlertaObjeto();
				//alertaObjeto.setIdalerta(alertaDto.getId().toString());
				//alertaObjeto.setTipoobjeto(alertaDto.getTipoobjeto());
				//alertaObjeto.setStatusalerta(alertaDto.getStatusalerta());
				//alertaObjeto.setIsfinalizado(alertaDto.getIsfinalizado());
				//alertaObjeto.setIdsesion(mesa.getIdsesion());
				//alertaObjeto.setIdalertaobjeto(GeneradorId.generaId(alertaObjeto));
				
				/*
				JsonNode jsonNode = cr.getPost(url, ConfiguracionAlerta.usuario);
				ConfiguracionAlerta.usuario = (Usuario) ConfiguracionAlerta.setMapperJsonToPojo(jsonNode, Usuario.class);
				System.out.println("**********************************************************************");
				System.out.println(jsonNode);
				 */
					//Genero solicitud de pago //
				if(mesa.getActodocumento()!=null){
					pago.setActodocumento(mesa.getActodocumento());
				}
				pago.setIdpago(GeneradorId.generaId(pago));
				pago.setIdsesion(mesa.getIdsesion());
				
				if(mesa.getIspagorequire()){
					pago.setStatuspago(EnumStatusPago.PENDIENTE);
				}else{
					pago.setStatuspago(EnumStatusPago.PAGADO);
				}
								
				mesa.setIdmesacontrol(GeneradorId.generaId(mesa));
				//mesa.setAlerta(alertaObjeto);
				//mesa.setVencimiento(alertaDto.getVencimiento());
				//alertaObjeto.setIdobjeto(mesa.getIdMesaControl());
				
				EntityManager em = factory.createEntityManager();
				EntityTransaction tx = em.getTransaction();

				try {
					tx.begin();

					em.persist(pago);
					em.persist(mesa);
//					//em.persist(alertaObjeto);
					tx.commit();

				} catch (Exception e) {
					e.printStackTrace();
					throw new NotariaException("No se logro guardar el objeto para mesa de control");
				}finally {
					em.close();
				}
				
				
				// Fin de pago
				
		return mesa;
	}

	@Override
	public MesaCtrl findByActoDocumento(String actodocumento){
		EntityManager em = factory.createEntityManager();
		TypedQuery<MesaCtrl> queryResult =  em.createNamedQuery("MesaCtrl.findByActoDocumento",MesaCtrl.class);
		queryResult.setParameter("idactodoc",actodocumento);
		try {

			MesaCtrl mesa = null;
			List<MesaCtrl> mesas = queryResult.getResultList();
			if(mesas.size()>0){
				mesa = mesas.get(0);
				LazyInit(mesa);
			}
			return mesa;
		} catch (NoResultException e) {
			return null;
		}finally {
			em.close();
		}
		
	}
	
	@Override
	public List<MesaCtrl> findByEstatusPago(EnumStatusPago enumPago){
		EntityManager em = factory.createEntityManager();
		TypedQuery<MesaCtrl> queryResult =  em.createNamedQuery("MesaCtrl.findByStatusPago",MesaCtrl.class);
		queryResult.setParameter("statuspago", enumPago);
		List<MesaCtrl> resultado = queryResult.getResultList();
		for(MesaCtrl mesa : resultado){
			LazyInit(mesa);
		}
		em.close();
		return resultado;
	}
	
	@Override
	public List<MesaCtrl> findAll(){
		EntityManager em = factory.createEntityManager();
		TypedQuery<MesaCtrl> queryResult =  em.createNamedQuery("MesaCtrl.findAll",MesaCtrl.class);
		List<MesaCtrl> resultado = queryResult.getResultList();
		for(MesaCtrl mesa : resultado){
			LazyInit(mesa);
		}
		em.close();
		return resultado;
	}
	
	
	
	
	private MesaCtrl LazyInit(MesaCtrl mesa){		
		System.out.println("mesa");
		if (mesa.getPago() !=null && mesa.getPago().getExpediente() !=null) {
			expediente = mesa.getPago().getExpediente();
		
		
		if(expediente instanceof HibernateProxy){
			expediente = (Expediente) ((HibernateProxy) expediente)
					.getHibernateLazyInitializer().getImplementation();
			expediente.setTramite(null);
			mesa.getPago().setExpediente(expediente);
			mesa.getEscritura().setExpediente(null);
			mesa.getEscritura().setNotario(null);
			mesa.getEscritura().setLibro(null);
	
		}
		}
		if(mesa.getActodocumento() != null){
			acto = mesa.getActodocumento().getActo();
			if(acto instanceof HibernateProxy){
				acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
				mesa.getActodocumento().setActo(acto);
				acto.setSuboperacion(null);
				acto.setExpediente(null);
				mesa.getActodocumento().setNotario(null);
			}
			mesa.getActodocumento().getFormatoPdf().setDetalleList(null);
		}
		
		return mesa;
	}

	@Override
	public MesaCtrl findNoPaso(Escritura esc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<MesaCtrl> queryResult =  em.createNamedQuery("MesaCtrl.findNopaso",MesaCtrl.class);
		queryResult.setParameter("numeroescritura",esc.getDsnumescritura());
		try {

			MesaCtrl mesa = null;
			List<MesaCtrl> mesas = queryResult.getResultList();
			if(mesas.size()>0){
				mesa = mesas.get(0);
			}
			return mesa;
		} catch (NoResultException e) {
			return null;
		}finally {
			em.close();
		}
	}

	@Override
	public List<MesaCtrl> findByEscritura(Escritura escritura) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<MesaCtrl> queryResult =  em.createNamedQuery("MesaCtrl.findEscritura",MesaCtrl.class);
		queryResult.setParameter("idescritura",escritura.getIdescritura());
		try {
			return queryResult.getResultList();
		} catch (NoResultException e) {
			return null;
		}finally {
			em.close();
		}
	}
	
	/*@Override
	public AlertaDto askAlerta(AlertaDto alertaDto,String url)throws NotariaException{
		PostGet cr = new PostGet(url);
		JsonNode respuestaJson = cr.getPost(alertaDto);
		alertaDto= (AlertaDto) PostGet.setMapperJsonToPojo(respuestaJson, AlertaDto.class);
		return alertaDto;
	}
	
	private AlertaDto setAlertaRemote(AlertaDto alertaDto,String tipoAlerta) throws NotariaException{
		alertaDto.setTipoobjeto(tipoAlerta);
		alertaDto = askAlerta(alertaDto,URL);
		return alertaDto;
		
	}*/
	
	
}
