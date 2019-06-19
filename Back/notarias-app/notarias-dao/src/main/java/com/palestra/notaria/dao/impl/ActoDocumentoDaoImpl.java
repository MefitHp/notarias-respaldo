package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.proxy.HibernateProxy;


import com.palestra.notaria.dao.ActoDocumentoDao;
import com.palestra.notaria.dao.DocumentoSuboperacionDao;
import com.palestra.notaria.dao.GestorDao;
import com.palestra.notaria.dao.MesaCtrlDao;
import com.palestra.notaria.dato.DatoActo;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.Gestor;
import com.palestra.notaria.modelo.GrupoTrabajo;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Usuario;

public class ActoDocumentoDaoImpl extends GenericDaoImpl<ActoDocumento, Integer> implements ActoDocumentoDao{

	public ActoDocumentoDaoImpl() {
		super(ActoDocumento.class);
	}

	@Override
	public List<DatoActoDocumento> obtenerPrevios(String idexpediente,
			String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoDocumento> query;
		List<ActoDocumento> lista;
		DatoActoDocumento dad;
		List<DatoActoDocumento> listaDatosActoDocumentos = new ArrayList<DatoActoDocumento>();
		try {
			
			// previos
			if(idacto==null){
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente AND documento.tipodoc.dselemento = 'Previo' " +
									" OR formatoPdf.tipodoc.dselemento = 'Previo' ORDER BY acto.suboperacion.dsnombre", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);				
			}else{
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente AND acto.idacto= :idacto AND documento.tipodoc.dselemento = 'Previo'" +
									" OR formatoPdf.tipodoc.dselemento = 'Previo'", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
				query.setParameter("idacto", idacto);
			}
			lista = query.getResultList();
			if(lista!=null){
				for(ActoDocumento ad:lista){
					dad = new DatoActoDocumento();
					if(ad.getDocumento()!=null){
						dad.setNombre(ad.getDocumento().getDstitulo());
						dad.setIsgestionado(ad.getDocumento().getIsgestionado());
					}else{
						dad.setNombre(ad.getFormatoPdf().getDstitulo());
						dad.setIsgestionado(ad.getFormatoPdf().getIsgestionado());
					}
					dad.setIdactodoc(ad.getIdactodoc());
					dad.setActo(ad.getActo().getDsnombre());
					dad.setFechasolicitud(ad.getFechasolicitud());
					dad.setFechaentrega(ad.getFechaentrega());
					dad.setRutaArchivo(ad.getDsrutaformato());
					dad.setRutaEvidencia(ad.getDsruta());
					Suboperacion subop = ad.getActo().getSuboperacion();
					if(subop instanceof HibernateProxy){
						subop = (Suboperacion) ((HibernateProxy)subop).getHibernateLazyInitializer().getImplementation();
					}
					dad.setOperacion(subop.getOperacion().getDsnombre());
					if(ad.getNotario()!=null){
						dad.setNotario(ad.getNotario().getDsiniciales());
					}else{						
						dad.setNotario(getNotario().getDsiniciales());
					}
					//System.out.println("ad.getGestor() ::: "+ad.getGestor());
					if(ad.getGestor()!=null){
						dad.setGestor(ad.getGestor());
					}else{
						dad.setGestor(null);
					}
					System.out.println("nombre doc: "+dad.getNombre());
					listaDatosActoDocumentos.add(dad);
				}
				
			}
//			----------
//			@omarete aqui puede haber un error porque se asume que viene un formatoPdf y mas arriba se hace un OR entre
//				el pdf y el documento
			if(idacto==null){
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente " +
									" AND formatoPdf.tipodoc.dselemento = 'Previo' ORDER BY acto.suboperacion.dsnombre", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
			} else {
				query = em.createQuery("from ActoDocumento where acto.expediente.idexpediente = :idexpediente AND acto.idacto= :idacto" +
									" AND formatoPdf.tipodoc.dselemento = 'Previo'", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
				query.setParameter("idacto", idacto);
			}
		
			lista = query.getResultList();
			if(lista!=null){
				DocumentoSuboperacionDao dsoDAO = new DocumentoSuboperacionDaoImpl();
				for(ActoDocumento ad:lista){
					dad = new DatoActoDocumento();					
					DocumentoSuboperacion dso = dsoDAO.getDocumentoSubOperacion(ad.getActo().getSuboperacion().getIdsuboperacion(),
							ad.getActo().getExpediente().getTramite().getLocacion().getIdelemento(),
							ad.getDocumento()!=null?ad.getDocumento().getIddocumento():ad.getFormatoPdf().getIdentificador());
					if(ad.getDocumento()!=null){
						dad.setNombre(ad.getDocumento().getDstitulo());
					}else{
						dad.setNombre(ad.getFormatoPdf().getDstitulo());
					}
					
					dad.setIsentregado(ad.getIsentregado());
					dad.setIsaprobado(ad.getIsaprovado());
					dad.setIsgestionado(dso!=null?dso.getIsgestionado():"");
					dad.setIdactodoc(ad.getIdactodoc());
					dad.setActo(ad.getActo().getDsnombre());
					dad.setFechasolicitud(ad.getFechasolicitud());
					dad.setFechaentrega(ad.getFechaentrega());
					dad.setRutaArchivo(ad.getDsrutaformato());
					dad.setRutaEvidencia(ad.getDsruta());
					Suboperacion subop = ad.getActo().getSuboperacion();
					if(subop instanceof HibernateProxy){
						subop = (Suboperacion) ((HibernateProxy)subop).getHibernateLazyInitializer().getImplementation();
					}
					dad.setOperacion(subop.getOperacion().getDsnombre());
					if(ad.getNotario()!=null){
						System.out.printf("El documento tiene asignado al notario %s.%n", ad.getNotario().getDsiniciales());
						dad.setNotario(ad.getNotario().getDsiniciales());
						//dad.setObjNotario(ad.getNotario());
					}else{						
						System.out.printf("No nay notario asignado, se asegnará uno por default");
						Usuario notario = getNotario();
						ad.setNotario(notario);
						ActoDocumentoDao daoAD = new ActoDocumentoDaoImpl();
						daoAD.update(ad);
						dad.setNotario(ad.getNotario().getDsiniciales());
						//dad.setObjNotario(ad.getNotario());
						System.out.printf("Se asigno el notario %s.%n", dad.getNotario());
					}
					if(ad.getGestor()!=null){
						dad.setGestor(ad.getGestor());
					}else{
						//se localizan los gestores de la locación del tramite, en caso de encontrar alguno se asigna al primero
						GestorDao gestorDao = new GestorDaoImpl();
						List<Gestor> gestores = gestorDao.findByLocacion(ad.getActo().getExpediente().getTramite().getLocacion());
						if(gestores!=null && gestores.size()>0){
							ad.setGestor(gestores.get(0));
							ActoDocumentoDao daoAD = new ActoDocumentoDaoImpl();
							daoAD.update(ad);							
							
							dad.setGestor(ad.getGestor());
						}else{
							dad.setGestor(null);
						}
					}
					if(ad.getValuador()!=null){
						dad.setValuador(ad.getValuador());
					}else{
						dad.setValuador(null);
					}
					if(dso!=null){
						dad.setInorden((dso.getInorden()!=null)?dso.getInorden():0);
						listaDatosActoDocumentos.add(dad);
					}
					
				}
				
			}			
		
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			listaDatosActoDocumentos = null;
			throw new NotariaException(e.getCause());
		}  finally {
			em.close();
		}
		return listaDatosActoDocumentos;
	}

	@Override
	public Usuario switchNotario(String idusuario) throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
//			se hardcodea el rol de notario
			Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.idusuario != ?1 AND u.rol.idrol = md5(9)");
			q.setParameter(1, idusuario);
			return (Usuario) q.getSingleResult();
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally{
			em.close();
		}
	}
	
	@Override
	public Usuario getNotario() throws NotariaException{
		EntityManager em = factory.createEntityManager();
		try{
//			se hardcodea el rol de notario
			TypedQuery<Usuario> q = em.createQuery("SELECT u FROM Usuario u WHERE u.rol.idrol = md5(9)", Usuario.class);
			List<Usuario> usuarios = q.getResultList();
			if(usuarios!=null && usuarios.size()>0){
				return usuarios.get(0);
			}else{
				return null;
			}
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}
	
	@Override
	public List<DatoActoDocumento> obtenerPosteriores(String idexpediente,
			String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoDocumento> query;
		List<ActoDocumento> lista;
		List<DatoActoDocumento> listaDatosActoDocumentos = new ArrayList<DatoActoDocumento>();
		try {
			// posteriores
			if(idacto==null){
				//(documento.tipodoc.dselemento = 'Posteriores' " "OR 
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente AND formatoPdf.tipodoc.dselemento = 'Posterior' ORDER BY acto.suboperacion.dsnombre ", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
			}else{
				//documento.tipodoc.dselemento = 'Posteriores' 
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente AND acto.idacto= :idacto AND " +
									" formatoPdf.tipodoc.dselemento = 'Posterior' ORDER BY acto.suboperacion.dsnombre ", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
				query.setParameter("idacto", idacto);
			}
			//System.out.printf("query.toString():::%s%nidexpediente:%s%nidacto:%s%n",query.toString(),idexpediente, idacto);
			lista = query.getResultList();
			if(lista!=null){
				DocumentoSuboperacionDao dsoDAO = new DocumentoSuboperacionDaoImpl();
				for(ActoDocumento ad:lista){
					DatoActoDocumento dad = ActoDocToDatoActoDoc(ad,dsoDAO);
					// Agrego mesa de control para que los abogados puedan utilizarlo
					MesaCtrlDao mesaDao = new MesaCtrlDaoImpl();
					dad.setMesacontrol(mesaDao.findByActoDocumento(dad.getIdactodoc()));
					listaDatosActoDocumentos.add(dad);						
				}
				
			}
			
		
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return listaDatosActoDocumentos;
	}

	@Override
	public String buscarArchivoPorId(String iddocumento) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<String> query;
		List<String> lista;
		String archivo= null;
		try {
			query = em.createQuery("SELECT dsruta FROM ActoDocumento WHERE idactodoc = :iddocumento", String.class);
			query.setParameter("iddocumento", iddocumento);
			lista = query.getResultList();
			if(lista!=null && !lista.isEmpty()){
				archivo = lista.get(0);
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return archivo;
	}

	@Override
	public boolean actualizarRutaArchivo(String iddocumento, String ruta) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			
			tx.begin();
			query = em.createQuery("update ActoDocumento set dsruta = :ruta, fechaArchivo = :fechaArchivo where idactodoc = :iddocumento");
			query.setParameter("fechaArchivo", new Date(), TemporalType.DATE);
			query.setParameter("ruta", ruta);
			query.setParameter("iddocumento", iddocumento);
			
			int result = query.executeUpdate();
			
			tx.commit();
			if(result > 0)
				b= true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}

		return b;
	}
	
	@Override
	public boolean actualizarRutaArchivoFormato(String idactdoc, String ruta) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			query = em
					.createQuery("update ActoDocumento set dsrutaformato = :dsrutaformato where idactodoc = :idactdoc");
			query.setParameter("dsrutaformato", ruta);
			query.setParameter("idactdoc", idactdoc);
			int result = query.executeUpdate();
			tx.commit();
			if (result > 0) {
				return true;
			} else {
				return false;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		}finally {
			em.close();
		}
	}

	@Override
	public boolean marcarDocumentoAprovado(String iddocumento, boolean marcar) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoDocumento> query;
		try {			
			query = em.createQuery("SELECT ad FROM ActoDocumento ad WHERE idactodoc = :iddocumento", ActoDocumento.class);
			query.setParameter("iddocumento", iddocumento);
			List<ActoDocumento> lista = query.getResultList();
			Boolean issolicitado=false;
			ActoDocumento documento = lista.get(0);
			if(lista!=null && !lista.isEmpty()){
				issolicitado = documento.getIssolicitado();
			}
			if(issolicitado==null || issolicitado==false) {
				
				documento.setFechaaprobacion(new Date());
				documento.setIsaprovado(true);
				documento = update(documento);
				
			}else{
				if(issolicitado==true){
					documento.setFechaaprobacion(new Date());
					documento.setIsaprovado(false);
					documento = update(documento);
				}
			}
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	@SuppressWarnings("unchecked")
	@Override
	public boolean marcarDocumentoEntregado(String iddocumento, boolean marcar) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			query = em.createQuery("select isentregado from ActoDocumento where idactodoc = :iddocumento");
			query.setParameter("iddocumento", iddocumento);
			List<Boolean> lista = query.getResultList();
			Boolean isentregado=false;
			int result=0;
			if(lista!=null && !lista.isEmpty())
				isentregado = lista.get(0);
			
			if(isentregado == null || isentregado==false){
				query = em.createQuery("update ActoDocumento set fechaentrega = :fechaentrega, isentregado = :isentregado where idactodoc = :iddocumento");
				query.setParameter("fechaentrega", new Date(), TemporalType.DATE);
				query.setParameter("isentregado", true);
				query.setParameter("iddocumento", iddocumento);
				result = query.executeUpdate();
			}else{
				if(isentregado==true){
					query = em.createQuery("update ActoDocumento set fechaentrega = :fechaentrega, isentregado = :isentregado where idactodoc = :iddocumento");
					query.setParameter("fechaentrega", null);
					query.setParameter("isentregado", false);
					query.setParameter("iddocumento", iddocumento);
					result = query.executeUpdate();
				}
			}
			
			
			
			tx.commit();
			if(result > 0)
				b= true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return b;
	}

	@Override
	public String buscarIdExpPorDocumento(String iddocumento) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<String> query;
		List<String> lista;
		String exp= null;
		try {
			query = em.createQuery("SELECT acto.expediente.idexpediente FROM ActoDocumento WHERE idactodoc = :iddocumento", String.class);
			query.setParameter("iddocumento", iddocumento);
			lista = query.getResultList();
			if(lista!=null && !lista.isEmpty()){
				exp = lista.get(0);
			}		
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

		return exp;
	}
	
	@Override
	public ActoDocumento obtenerCompletoPorId(String idactodoc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoDocumento> query;
		List<ActoDocumento> lista;
		ActoDocumento actoDoc;
		Acto acto;
		Documento documento;
		try {
			query = em.createQuery("FROM ActoDocumento WHERE idactodoc = :idactodoc", ActoDocumento.class);
			query.setParameter("idactodoc", idactodoc);
			lista = query.getResultList();
			if(lista==null || lista.isEmpty()){
				return null;
			}else{
				actoDoc = lista.get(0);
				acto = actoDoc.getActo();
				if (acto instanceof HibernateProxy) {
					acto = (Acto) ((HibernateProxy) acto).getHibernateLazyInitializer().getImplementation();
					//acto.setSuboperacion(null);
					Suboperacion suboperacion = acto.getSuboperacion();
					if(suboperacion instanceof HibernateProxy){
						suboperacion = (Suboperacion)((HibernateProxy) suboperacion).getHibernateLazyInitializer().getImplementation();
						acto.setSuboperacion(suboperacion);
					}
					acto.setExpediente(null);
				}
				actoDoc.setActo(acto);
				documento = actoDoc.getDocumento();
				if(documento instanceof HibernateProxy){
					documento = (Documento) ((HibernateProxy) documento).getHibernateLazyInitializer().getImplementation();
					documento.setTipodoc(null);
				}
				actoDoc.setDocumento(documento);
				return actoDoc;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public boolean actualizaTexto(String idactodoc, String txtFormato) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		try {
			tx.begin();
			query = em.createQuery("update ActoDocumento set txtFormato = :txtFormato, tmstmp =:tmstmp  where idactodoc = :idactodoc");
			query.setParameter("tmstmp", new Timestamp((new Date()).getTime()), TemporalType.TIMESTAMP);
			query.setParameter("idactodoc", idactodoc);
			query.setParameter("txtFormato", txtFormato);
			int result = query.executeUpdate();
			tx.commit();
			if(result > 0){
				b= true;
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return b;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String obtenerIdTramitePorDocumento(String idactodoc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String idtramite= null;
		try {
			query = em.createQuery("select acto.expediente.tramite.idtramite from ActoDocumento where idactodoc = :idactodoc ");
			query.setParameter("idactodoc", idactodoc);
			lista = query.getResultList();
			if(lista == null || lista.isEmpty()){
				return null;
			}
			idtramite = lista.get(0);
			if(idtramite==null || idtramite.isEmpty()){
				return null;
			}
			return idtramite;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}
	
	@Override
	public ActoDocumento findById(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			ActoDocumento actoDoc = em.find(ActoDocumento.class, id);
			Acto acto = actoDoc.getActo();
			Usuario notario = actoDoc.getNotario();
			Documento documento = actoDoc.getDocumento();
			if(acto instanceof HibernateProxy){
				acto = (Acto)((HibernateProxy)acto).getHibernateLazyInitializer().getImplementation();
				acto.setExpediente(null);
				acto.setSuboperacion(null);
			}
			if(notario instanceof HibernateProxy){
				notario = (Usuario)((HibernateProxy)notario).getHibernateLazyInitializer().getImplementation();
			}
			if(documento instanceof HibernateProxy){
				documento = (Documento)((HibernateProxy)documento).getHibernateLazyInitializer().getImplementation();
				documento.setTipodoc(null);
			}
			return actoDoc;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}

	}

	@Override
	public boolean tieneDocActo(String idacto) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try{
			TypedQuery<ActoDocumento> query = em.createQuery("SELECT ad from ActoDocumento ad where ad.acto.idacto = :idacto",ActoDocumento.class);
			query.setParameter("idacto", idacto);
			return (query.getResultList().size()>0)?true:false;
		}catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public void borrar(ActoDocumento actoDoc) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		try {
			tx = em.getTransaction();
			tx.begin();
				ActoDocumento ad = em.find(ActoDocumento.class,actoDoc.getIdactodoc());
				// VERIFICO QUE SEA DIM O ANEXO 5 PARA VALIDAR QUE YA SE TIENE UNO EN ESTE ACTO
				Boolean hasDim = ad.getFormatoPdf().getDstitulo().contains("DIM");
				Boolean hasAnexo5 = ad.getFormatoPdf().getDstitulo().contains("Anexo 5");
				
				if(hasDim || hasAnexo5){
					Acto act = em.find(Acto.class,ad.getActo().getIdacto());
					if(hasDim){
						act.setHasdim(null);
					}
					if(hasAnexo5){
						act.setHasanexo5(null);
					}
					em.merge(act);
				}
				
				em.remove(ad);						
			tx.commit();
			return;
		} catch (PersistenceException e) {
			
		    while ((e.getCause() != null) && !(e.getCause() instanceof ConstraintViolationException)) {
		        throw new NotariaException("El documento que intentas eliminar ya se encuentra en Mesa de Control, y no es posible eliminarlo verificalo con sistemas");
		    }
			
		    try {
				tx.rollback();
			} catch (Exception e2) {
				throw new NotariaException(e.getCause());
			}

		}finally {
			em.close();
		}
		return;
		
	}

	@Override
	public List<DatoActoDocumento> obtenerXidActo(Acto acto) throws NotariaException {
		List<DatoActoDocumento> previos = obtenerPrevios(acto.getExpediente().getIdexpediente(), acto.getIdacto());
		List<DatoActoDocumento> posteriores = obtenerPosteriores(acto.getExpediente().getIdexpediente(), acto.getIdacto());
		previos.addAll(posteriores);
		return previos;
	}
	
	private DatoActoDocumento ActoDocToDatoActoDoc(ActoDocumento ad,DocumentoSuboperacionDao dsoDAO) throws NotariaException{
		DatoActoDocumento dad;
		System.out.println("**************************************");
		System.out.println("DOC POST:"+ ad.getFormatoPdf().getDstitulo());
		System.out.println("**************************************");
		dad = new DatoActoDocumento();
		DocumentoSuboperacion dso = dsoDAO.getDocumentoSubOperacion(ad.getActo().getSuboperacion().getIdsuboperacion(),
				ad.getActo().getExpediente().getTramite().getLocacion().getIdelemento(),
				ad.getDocumento()!=null?ad.getDocumento().getIddocumento():ad.getFormatoPdf().getIdentificador());
		if(ad.getDocumento()!=null){
			dad.setNombre(ad.getDocumento().getDstitulo());
		}else{
			dad.setNombre(ad.getFormatoPdf().getDstitulo());
		}
		
		if(dso!=null && dso.getSuboperacion() !=null &&dso.getSuboperacion().getOperacion()!=null){
			dad.setOperacion(dso.getSuboperacion().getOperacion().getDsnombre());
		}
		
		dad.setIsgestionado(dso!=null?dso.getIsgestionado():"");
		dad.setIdactodoc(ad.getIdactodoc());
		dad.setActo(ad.getActo().getDsnombre());
		dad.setFechasolicitud(ad.getFechasolicitud());
		dad.setFechaentrega(ad.getFechaentrega());
		if(ad.getDsrutaformato()!= null){
			dad.setRutaArchivo(ad.getDsrutaformato());
		}else{
			if(ad.getFormatoPdf()!=null && ad.getFormatoPdf().getDsruta()!=null && ad.getFormatoPdf().getDsruta().contains("http://")){
				dad.setRutaArchivo(ad.getFormatoPdf().getDsruta());
			}
		}
		dad.setRutaEvidencia(ad.getDsruta());
		dad.setIsaprobado(ad.getIsaprovado());
		dad.setIsentregado(ad.getIsentregado());
		dad.setIssolicitado(ad.getIssolicitado());
		dad.setIspagorequire(ad.getFormatoPdf().getIspagorequire());
		dad.setTienecomments(ad.getTienecomments());
		dad.setIsonline(ad.getFormatoPdf().getIsonline());
		//if(ad.getDocumento().getIsgestionado()!=null && !ad.getDocumento().getIsgestionado().isEmpty()){
		//if(dad.getIsgestionado()!=null && !dad.getIsgestionado().isEmpty()){
			if(ad.getNotario()!=null){
				dad.setNotario(ad.getNotario().getDsiniciales());
			}else{
				Usuario notario = getNotario();
				ad.setNotario(notario);
				ActoDocumentoDao daoAD = new ActoDocumentoDaoImpl();
				daoAD.update(ad);
				dad.setNotario(ad.getNotario().getDsiniciales());
				//dad.setObjNotario(ad.getNotario());
				
				dad.setNotario(getNotario().getDsiniciales());
			}
			if(ad.getGestor()!=null){
				dad.setGestor(ad.getGestor());
			}else{
				//se localizan los gestores de la locación del tramite, en caso de encontrar alguno se asigna al primero
				GestorDao gestorDao = new GestorDaoImpl();
				List<Gestor> gestores = gestorDao.findByLocacion(ad.getActo().getExpediente().getTramite().getLocacion());
				if(gestores!=null && gestores.size()>0){
					dad.setGestor(gestores.get(0));
				}else{
					dad.setGestor(null);
				}
			}
			if(ad.getValuador()!=null){
				dad.setValuador(ad.getValuador());
			}else{
				dad.setValuador(null);
			}
		//}
			if(dso!=null){
				dad.setInorden((dso.getInorden()!=null)?dso.getInorden():0);
			}
			
			return dad;
	}

	@Override
	public ArrayList<DatoActoDocumento> obtenerDocXnombre(String nombreDoc, String idexpediente, String idacto)
			throws NotariaException {
		
		EntityManager em = factory.createEntityManager();
		TypedQuery<ActoDocumento> query;
		List<ActoDocumento> lista;
		ArrayList<DatoActoDocumento> listaDatosActoDocumentos = new ArrayList<DatoActoDocumento>();
		try {
			// posteriores
			if(idacto==null){
				throw new NotariaException("El acto de los documentos no está vacio");
			}else{
				query = em.createQuery("FROM ActoDocumento WHERE acto.expediente.idexpediente = :idexpediente AND acto.idacto= :idacto AND " +
									" formatoPdf.dstitulo=:nombre  ORDER BY acto.suboperacion.dsnombre ", ActoDocumento.class);
				query.setParameter("idexpediente", idexpediente);
				query.setParameter("idacto", idacto);
				query.setParameter("nombre", nombreDoc);
			}
			//System.out.printf("query.toString():::%s%nidexpediente:%s%nidacto:%s%n",query.toString(),idexpediente, idacto);
			lista = query.getResultList();
			if(lista!=null){
				DocumentoSuboperacionDao dsoDAO = new DocumentoSuboperacionDaoImpl();
				for(ActoDocumento ad:lista){
					DatoActoDocumento dad = ActoDocToDatoActoDoc(ad,dsoDAO);
					// Agrego mesa de control para que los abogados puedan utilizarlo
					MesaCtrlDao mesaDao = new MesaCtrlDaoImpl();
					dad.setMesacontrol(mesaDao.findByActoDocumento(dad.getIdactodoc()));
					listaDatosActoDocumentos.add(dad);						
				}
				
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return listaDatosActoDocumentos;	
	}
	
}
