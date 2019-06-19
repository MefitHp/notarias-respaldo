package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.DocumentoSuboperacionDao;
import com.palestra.notaria.dao.ExpedienteDao;
import com.palestra.notaria.dao.TramiteDao;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.dato.DatoActo;
import com.palestra.notaria.dato.DatoActoDocumento;
import com.palestra.notaria.dato.DatoOperacion;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.Acto;
import com.palestra.notaria.modelo.ActoDocumento;
import com.palestra.notaria.modelo.BitacoraGeneral;
import com.palestra.notaria.modelo.Comentario;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Documento;
import com.palestra.notaria.modelo.DocumentoSuboperacion;
import com.palestra.notaria.modelo.ElementoCatalogo;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Expediente;
import com.palestra.notaria.modelo.FormatoPDF;
import com.palestra.notaria.modelo.FormatoPDFDetalle;
import com.palestra.notaria.modelo.Operacion;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Suboperacion;
import com.palestra.notaria.modelo.Tramite;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class ExpedienteDaoImpl extends GenericDaoImpl<Expediente, Integer> implements ExpedienteDao {

	public ExpedienteDaoImpl() {
		super(Expediente.class);
	}

	@Override
	public Expediente buscarPorIdCompleto(Expediente expediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Expediente exp = null;
		List<Expediente> list;
		try {
			TypedQuery<Expediente> query = em.createQuery("FROM Expediente WHERE idexpediente = :idexpediente",
					Expediente.class);
			query.setParameter("idexpediente", expediente.getIdexpediente());
			
			list = query.getResultList();
						
			if (list != null && list.size() > 0) {
				exp = list.get(0);
			} else {
				exp = null;
			}
			
					
			// Tramite tramite;
			Usuario abogado;
			Usuario usuario;
			List<Comentario> comentarios;

			if (exp != null) {

				abogado = exp.getAbogado();
				if (abogado instanceof HibernateProxy) {
					abogado = (Usuario) ((HibernateProxy) abogado).getHibernateLazyInitializer().getImplementation();
					abogado.setRol(null);
				}
				exp.setAbogado(abogado);

				// comentarios = exp.getListaComentarios();

				/*
				 * for (Comentario com : comentarios) {
				 * 
				 * usuario = com.getUsuario(); if (usuario instanceof
				 * HibernateProxy) { usuario = (Usuario) ((HibernateProxy)
				 * usuario) .getHibernateLazyInitializer() .getImplementation();
				 * usuario.setRol(null); } com.setUsuario(usuario); }
				 */

				// exp.setListaComentarios(comentarios);
				exp.getTramite().getCliente().setTipopersona(null);
				exp.getTramite().getCliente().setNacionalidad(null);
			}

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return exp;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ArrayList<DatoOperacion> buscarOperaciones(String idExpediente, String idOperacion) throws Exception {
		EntityManager em = factory.createEntityManager();
		List<Acto> lista = null;
		ArrayList<DatoOperacion> listaResultado = null;
		try {
			Query query;

			if (idOperacion == null)
				query = em.createQuery("from Acto a where expediente.idexpediente = '" + idExpediente
						+ "' AND a.isactivo = true" + " order by suboperacion.operacion.idoperacion ");
			else
				query = em.createQuery("from Acto where expediente.idexpediente = '" + idExpediente
						+ "' and suboperacion.operacion.idoperacion='" + idOperacion + "'");

			lista = query.getResultList();
			listaResultado = new ArrayList<DatoOperacion>();
			DatoOperacion datoOperacionActual = new DatoOperacion();
			Operacion operacionActual = new Operacion();
			Operacion oper;
			Suboperacion suboperacion;
			Documento docaux;
			if (lista != null && !lista.isEmpty()) {

				operacionActual = lista.get(0).getSuboperacion().getOperacion();
				datoOperacionActual.setDsnombre(operacionActual.getDsnombre());
				datoOperacionActual.setIdoperacion(operacionActual.getIdoperacion());
				oper = operacionActual;
				if (oper instanceof HibernateProxy) {
					oper = (Operacion) ((HibernateProxy) oper).getHibernateLazyInitializer().getImplementation();
				}
				datoOperacionActual.setOperacion(oper);
				listaResultado.add(datoOperacionActual);
				ArrayList<DatoActo> listaActos = new ArrayList<DatoActo>();

				DatoActo datoActo = new DatoActo();
				// String id_Operacion, idsuboperacion, nombreOperacion,
				// nombresuboperacion;
				for (Acto act : lista) {

					if (datoOperacionActual.getIdoperacion()
							.equals(act.getSuboperacion().getOperacion().getIdoperacion())) {
						

						datoActo = new DatoActo();
						datoActo.setIdSuboperacion(act.getSuboperacion().getIdsuboperacion());
						datoActo.setDsnombre(act.getDsnombre());
						suboperacion = act.getSuboperacion();
						if (suboperacion instanceof HibernateProxy) {
							suboperacion = (Suboperacion) ((HibernateProxy) suboperacion).getHibernateLazyInitializer()
									.getImplementation();
						}
						// suboperacion.setOperacion(null);
						datoActo.setSuboperacion(suboperacion);
						datoActo.setIdSuboperacion(act.getSuboperacion().getIdsuboperacion());
						datoActo.setIdacto(act.getIdacto());
						datoActo.setNumacto(act.getNumacto());
						datoActo.setHasProceso(act.getHasProceso());
						listaActos.add(datoActo);
						// @omarete: pinches muebladas pendejas, llenas un
						// objeto
						// datoActo que se lo agregas a listaActos y después no
						// se lo agregas
						// al objeto del objeto datoOperacionActual, conclusión:
						// P. M. P.
						datoOperacionActual.setListaActos(listaActos);

					} else {

						datoOperacionActual = new DatoOperacion();
						datoOperacionActual.setIdoperacion(act.getSuboperacion().getOperacion().getIdoperacion());
						datoOperacionActual.setDsnombre(act.getSuboperacion().getOperacion().getDsnombre());

						datoOperacionActual.setOperacion(act.getSuboperacion().getOperacion());
						// datoOperacionActual.setOperacion(act.getSuboperacion()
						// .getOperacion());
						listaResultado.add(datoOperacionActual);
						listaActos = new ArrayList<DatoActo>();
						datoOperacionActual.setListaActos(listaActos);
						datoActo = new DatoActo();
						datoActo.setIdSuboperacion(act.getSuboperacion().getIdsuboperacion());
						datoActo.setDsnombre(act.getDsnombre());
						datoActo.setIdSuboperacion(act.getSuboperacion().getIdsuboperacion());
						suboperacion = act.getSuboperacion();
						if (suboperacion instanceof HibernateProxy) {
							suboperacion = (Suboperacion) ((HibernateProxy) suboperacion).getHibernateLazyInitializer()
									.getImplementation();
						}
						suboperacion.setOperacion(null);
						datoActo.setSuboperacion(suboperacion);
						datoActo.setIdacto(act.getIdacto());
						datoActo.setNumacto(act.getNumacto());
						listaActos.add(datoActo);

					}

				}
				datoOperacionActual.setListaActos(listaActos);

				List<ActoDocumento> listaDocActo;
				ArrayList<DatoActoDocumento> previos;
				ArrayList<DatoActoDocumento> posteriores;
				DatoActoDocumento datoActoDoc;
				for (DatoOperacion doper : listaResultado) {

					listaActos = doper.getListaActos();

					if (listaActos != null && !listaActos.isEmpty()) {

						for (DatoActo da : listaActos) {
							da.getSuboperacion().setOperacion(null);
							// previos
							query = em.createQuery("from ActoDocumento where acto.idacto = '" + da.getIdacto() + "'"
							// +
							// "' and (documento.tipodoc.dselemento = 'Previo' "
							// +
									+ " AND formatoPdf.tipodoc.dselemento = 'Previo'");
							listaDocActo = query.getResultList();
							query = em.createQuery("from ActoDocumento where acto.idacto = '" + da.getIdacto()
									+ "' AND documento.tipodoc.dselemento = 'Previo'");
							listaDocActo.addAll(query.getResultList());
							if (listaDocActo != null && !listaDocActo.isEmpty()) {
								previos = new ArrayList<DatoActoDocumento>();
								for (ActoDocumento ad : listaDocActo) {
									System.out.println("formato pdf " + ad.getFormatoPdf());
									if (ad.getFormatoPdf() != null) {
										FormatoPDF pdf = new FormatoPDF();
										datoActoDoc = new DatoActoDocumento();
										datoActoDoc.setActo(da.getIdacto());
										datoActoDoc.setIdactodoc(ad.getIdactodoc());
										datoActoDoc.setIddocumento(ad.getFormatoPdf().getIdentificador());
										datoActoDoc.setNombre(ad.getFormatoPdf().getDstitulo());
										pdf = ad.getFormatoPdf();
										for (FormatoPDFDetalle detalle : pdf.getDetalleList()) {
											detalle.setIdftopdf(null);
											detalle.setSuboperacion(null);
										}
										datoActoDoc.setFormatopdf(pdf);
										previos.add(datoActoDoc);
									} else {
										datoActoDoc = new DatoActoDocumento();
										datoActoDoc.setActo(da.getIdacto());
										datoActoDoc.setIdactodoc(ad.getIdactodoc());
										datoActoDoc.setIddocumento(ad.getDocumento().getIddocumento());
										datoActoDoc.setNombre(ad.getDocumento().getDstitulo());
										docaux = ad.getDocumento();
										if (docaux instanceof HibernateProxy) {
											docaux = (Documento) ((HibernateProxy) docaux).getHibernateLazyInitializer()
													.getImplementation();
										}
										if (docaux != null) {
											docaux.setTipodoc(null);
											docaux.setTxplantilla(null);
										}
										datoActoDoc.setDocumento(docaux);

										previos.add(datoActoDoc);
									}
								}
								da.setPrevios(previos);
							}

							// posteriroes
							query = em.createQuery("from ActoDocumento where acto.idacto = '" + da.getIdacto()
									+ "' and documento.tipodoc.dselemento = 'Posterior'");
							listaDocActo = query.getResultList();
							query = em.createQuery("from ActoDocumento where acto.idacto = '" + da.getIdacto()
									+ "' AND formatoPdf.tipodoc.dselemento = 'Posterior'");
							listaDocActo.addAll(query.getResultList());
							if (listaDocActo != null && !listaDocActo.isEmpty()) {
								posteriores = new ArrayList<DatoActoDocumento>();
								for (ActoDocumento ad : listaDocActo) {
									System.out.println("formato pdf " + ad.getFormatoPdf());
									if (ad.getFormatoPdf() != null) {
										FormatoPDF pdf = new FormatoPDF();
										datoActoDoc = new DatoActoDocumento();
										datoActoDoc.setActo(da.getIdacto());
										datoActoDoc.setIdactodoc(ad.getIdactodoc());
										datoActoDoc.setIddocumento(ad.getFormatoPdf().getIdentificador());
										datoActoDoc.setNombre(ad.getFormatoPdf().getDstitulo());
										pdf = ad.getFormatoPdf();
										for (FormatoPDFDetalle detalle : pdf.getDetalleList()) {
											detalle.setIdftopdf(null);
											detalle.setSuboperacion(null);
										}
										datoActoDoc.setFormatopdf(pdf);
										posteriores.add(datoActoDoc);
									} else {
										datoActoDoc = new DatoActoDocumento();
										datoActoDoc.setActo(da.getIdacto());
										datoActoDoc.setIdactodoc(ad.getIdactodoc());
										datoActoDoc.setNombre(ad.getDocumento().getDstitulo());
										datoActoDoc.setIddocumento(ad.getDocumento().getIddocumento());
										docaux = ad.getDocumento();
										if (docaux instanceof HibernateProxy) {
											docaux = (Documento) ((HibernateProxy) docaux).getHibernateLazyInitializer()
													.getImplementation();
										}
										if (docaux != null) {
											docaux.setTipodoc(null);
											docaux.setTxplantilla(null);
										}
										datoActoDoc.setDocumento(docaux);
										posteriores.add(datoActoDoc);
									}
								}
								da.setPosteriores(posteriores);
							}
						}
					}
				}
			}
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return listaResultado;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean eliminarOperacion(String idExpediente, String idOperacion, String idusuario)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		boolean b = false;
		Query query;

		EntityTransaction tx = em.getTransaction();
		List<Acto> listaActos;
		List<ActoDocumento> listaDocumentos;

		BitacoraGeneral bg;
		try {

			tx.begin();

			query = em.createQuery("from ActoDocumento where acto.expediente.idexpediente = '" + idExpediente
					+ "' and acto.suboperacion.operacion.idoperacion='" + idOperacion + "'");
			listaDocumentos = query.getResultList();
			if (listaDocumentos != null && !listaDocumentos.isEmpty()) {

				for (ActoDocumento ad : listaDocumentos) {

					em.remove(em.contains(ad) ? ad : em.merge(ad));
					bg = bitGenHelp.crearBitacora(idusuario, idExpediente, null, "ActoDocumento", "Eliminar",
							"Eliminacion del Documento ");
					em.persist(bg);
				}
			}

			query = em.createQuery("from Acto where expediente.idexpediente = '" + idExpediente
					+ "' and suboperacion.operacion.idoperacion='" + idOperacion + "'");
			listaActos = query.getResultList();
			if (listaActos != null && !listaActos.isEmpty()) {

				for (Acto act : listaActos) {
					System.out.println("acto a borrar " + act.getIdacto());
					em.remove(em.contains(act) ? act : em.merge(act));
					bg = bitGenHelp.crearBitacora(idusuario, idExpediente, null, "Acto", "Eliminar",
							"Eliminacion del Acto " + act.getDsnombre());
					em.persist(bg);
				}
			}

			tx.commit();
			b = true;
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
	public boolean guardarOperacion(DatoOperacion datoOperacion, String idexpediente, String idsesion, Timestamp tstmp,
			String idusuario) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = false;
		Query query;
		EntityTransaction tx = em.getTransaction();
		ArrayList<DatoActo> listaActos = datoOperacion.getListaActos();
		Acto acto;
		Suboperacion subOperacion;
		Expediente exp = new Expediente();
		exp.setIdexpediente(idexpediente);
		List<DocumentoSuboperacion> previos = new ArrayList<DocumentoSuboperacion>();
		List<DocumentoSuboperacion> posteriores = new ArrayList<DocumentoSuboperacion>();
		DocumentoSuboperacionDao docSubopDao = new DocumentoSuboperacionDaoImpl();
		ActoDocumento actoDocumento;
		// Documento documento;
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		BitacoraGeneral bg;
		try {
			query = em.createQuery(
					"select max(numacto) from Acto where expediente.idexpediente = '" + idexpediente + "'");
			Integer nummax = (Integer) query.getSingleResult();
			if (nummax == null)
				nummax = 0;

			tx.begin();
			// System.out.println("LISTA ACTOS "+listaActos);
			if (listaActos != null && !listaActos.isEmpty()) {
				for (DatoActo da : listaActos) {
					acto = new Acto();
					subOperacion = new Suboperacion();
					subOperacion.setIdsuboperacion(da.getIdSuboperacion());
					acto.setIdacto(GeneradorId.generaId(acto));
					acto.setDsnombre(da.getDsnombre());
					acto.setIsactivo(Boolean.TRUE);
					acto.setExpediente(exp);
					acto.setIdsesion(idsesion);
					acto.setSuboperacion(subOperacion);
					acto.setTmstmp(tstmp);
					nummax++;
					acto.setNumacto(nummax);
					acto.setDsdescripcion(String.valueOf(nummax));
//					acto.setHasProceso(Boolean.TRUE);
					em.persist(acto);
					da.setIdacto(acto.getIdacto());
					bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "Acto", "Registrar",
							"Registro del Acto " + acto.getDsnombre());
					em.persist(bg);

					// se iteran los previos y posteriores por default y se
					// agregan a listas de previos y posteriores
					// System.out.println("locacion
					// "+datoOperacion.getLocacion().getIdelemento());
					// System.out.println("suboperacion
					// "+da.getIdSuboperacion());
					if (datoOperacion.getLocacion() == null) {
						TramiteDao tramiteDao = new TramiteDaoImpl();
						String idtramite = this.obtenerTramitePorExpedienteId(idexpediente);
						Tramite tramite = tramiteDao.buscarPorIdCompleto(new Tramite(idtramite));
						if (tramite.getLocacion() != null) {
							datoOperacion.setLocacion(tramite.getLocacion());
						} else {
//							System.out.println(
//									"parece que el tramite, es null por lo que no se puede obtener la localidad y la"
//											+ " locacion en datoOperacion.getlocacion tambien!");
//							omar 4/oct/17 se asigna por default una localidad, porque en el caso de Exp Judiciales no se tiene ese dato
							datoOperacion.setLocacion(new ElementoCatalogo("1c383cd30b7c298ab50293adfecb7b18"));
						}
					}
					List<DocumentoSuboperacion> docsList = docSubopDao.listarPreviosPorSubopAndLocalidad(
							datoOperacion.getLocacion().getIdelemento(), da.getIdSuboperacion());
					for (DocumentoSuboperacion doc : docsList) {

						if (doc.getDocumento() != null) {
							if (doc.getDocumento().getTipodoc().getIdelemento().equals(Constantes.ID_DOCUMENTO_PREVIO))
								previos.add(doc);
							else if (doc.getDocumento().getTipodoc().getIdelemento()
									.equals(Constantes.ID_DOCUMENTO_POSTERIOR))
								posteriores.add(doc);
						} else if (doc.getFormatopdf().getTipodoc().getIdelemento()
								.equals(Constantes.ID_DOCUMENTO_PREVIO))
							previos.add(doc);
						else if (doc.getFormatopdf().getTipodoc().getIdelemento()
								.equals(Constantes.ID_DOCUMENTO_POSTERIOR))
							posteriores.add(doc);
					}

					// guardado de previos
					if (previos != null && !previos.isEmpty()) {
						for (DocumentoSuboperacion dad : previos) {
							actoDocumento = new ActoDocumento();
							// comprobar de donde corresponde el iddocumento
							// Documento doc = em.find(Documento.class,
							// dad.getIddocumento());
							// FormatoPDF pdf = em.find(FormatoPDF.class,
							// dad.getIddocumento());

							actoDocumento.setActo(acto);
							actoDocumento.setIdactodoc(GeneradorId.generaId(actoDocumento));
							actoDocumento.setDocumento(dad.getDocumento());
							actoDocumento.setFormatoPdf(dad.getFormatopdf());
							actoDocumento.setTmstmp(tstmp);
							actoDocumento.setIdsesion(idsesion);
							em.persist(actoDocumento);
							bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento", "Registrar",
									"Registro de Documento Previo ");
							em.persist(bg);

						}
					}

					// guardado de posteriores
					// Se quitan los posteriores por que ya no se agregan el automático, el abogado los asigna
					/*if (posteriores != null && !posteriores.isEmpty()) {
						for (DocumentoSuboperacion dad : posteriores) {
							actoDocumento = new ActoDocumento();
							// Documento doc = em.find(Documento.class,
							// dad.getIddocumento());
							// FormatoPDF pdf = em.find(FormatoPDF.class,
							// dad.getIddocumento());

							actoDocumento.setActo(acto);
							actoDocumento.setIdactodoc(GeneradorId.generaId(actoDocumento));
							actoDocumento.setDocumento(dad.getDocumento());
							actoDocumento.setFormatoPdf(dad.getFormatopdf());
							actoDocumento.setTmstmp(tstmp);
							actoDocumento.setIdsesion(idsesion);
							em.persist(actoDocumento);
							bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento", "Registrar",
									"Registro de Documento Posterior ");
							em.persist(bg);
						}
					}*/

				}
			}

			tx.commit();
			b = true;
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
	public boolean actualizarOperacion(DatoOperacion datoOperacion, String idexpediente, String idsesion,
			Timestamp tstmp, String idusuario) throws NotariaException {
		boolean b = false;
		Query query;
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = em.getTransaction();
		ArrayList<DatoActo> listaActos = datoOperacion.getListaActos();
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		BitacoraGeneral bg;
		// int i = 0;
		try {

			tx.begin();
			List<ActoDocumento> lista;

			// guardado de la nueva operacion
			query = em.createQuery(
					"select max(numacto) from Acto where expediente.idexpediente = '" + idexpediente + "'");
			Integer nummax = (Integer) query.getSingleResult();
			Acto acto;
			Suboperacion subOperacion;
			// @omarete se comentan previos y posteriores que son de wrapper del
			// mueble y no se usan porque se cambia
			// la forma de guardar previos y posteriores
			// ArrayList<DatoActoDocumento> previos;
			// ArrayList<DatoActoDocumento> posteriores;
			ArrayList<DocumentoSuboperacion> previos = new ArrayList<DocumentoSuboperacion>();
			ArrayList<DocumentoSuboperacion> posteriores = new ArrayList<DocumentoSuboperacion>();
			ActoDocumento actoDocumento;
			Documento documento;
			FormatoPDF pdf = null;
			DocumentoSuboperacionDao docSubopDao = new DocumentoSuboperacionDaoImpl();
			Expediente exp = new Expediente();
			exp.setIdexpediente(idexpediente);
			ArrayList<ActoDocumento> inserciones = new ArrayList<ActoDocumento>();
			ArrayList<ActoDocumento> eliminaciones = new ArrayList<ActoDocumento>();
			if (listaActos != null && !listaActos.isEmpty()) {
				for (DatoActo da : listaActos) {
					acto = new Acto();

					switch (da.getStatus()) {
					case "X":
						acto.setIdacto(da.getIdacto());
						break;
					case "C":
						subOperacion = new Suboperacion();
						subOperacion.setIdsuboperacion(da.getIdSuboperacion());
						System.out.println("acto nombre en dao " + da.getDsnombre());
						acto.setDsnombre(da.getDsnombre());
						acto.setExpediente(exp);
						acto.setIdsesion(idsesion);
						acto.setSuboperacion(subOperacion);
						acto.setIsactivo(Boolean.TRUE);
						acto.setTmstmp(tstmp);
						acto.setIdacto(GeneradorId.generaId(acto));
						nummax++;
						acto.setNumacto(nummax);
						acto.setDsdescripcion(String.valueOf(nummax));
//						acto.setHasProceso(Boolean.TRUE);
						em.persist(acto);
						da.setIdacto(acto.getIdacto());
						// @omarete AQUI SE VAN A ASIGNAR LOS PREVIOS Y
						// POSTERIORES DEL ACTO QUE SE VAV A GUARDAR
						// @omarete GUARDADO DE PREVIOS Y POSTERIORES que si
						// funciona
						if (datoOperacion.getLocacion() == null) {
							TramiteDao tramiteDao = new TramiteDaoImpl();
							String idtramite = this.obtenerTramitePorExpedienteId(idexpediente);
							Tramite tramite = tramiteDao.buscarPorIdCompleto(new Tramite(idtramite));
							if (tramite != null) {
								datoOperacion.setLocacion(tramite.getLocacion());
							} else {
								System.out.println(
										"parece que el tramite, es null por lo que no se puede obtener la localidad y la"
												+ " locacion en datoOperacion.getlocacion tambien!");
							}
						}
						List<DocumentoSuboperacion> docsList = docSubopDao.listarPreviosPorSubopAndLocalidad(
								datoOperacion.getLocacion().getIdelemento(), da.getIdSuboperacion());
						for (DocumentoSuboperacion doc : docsList) {

							if (doc.getDocumento() != null) {
								if (doc.getDocumento().getTipodoc().getIdelemento()
										.equals(Constantes.ID_DOCUMENTO_PREVIO))
									previos.add(doc);
								else if (doc.getDocumento().getTipodoc().getIdelemento()
										.equals(Constantes.ID_DOCUMENTO_POSTERIOR))
									posteriores.add(doc);
							} else if (doc.getFormatopdf().getTipodoc().getIdelemento()
									.equals(Constantes.ID_DOCUMENTO_PREVIO))
								previos.add(doc);
							else if (doc.getFormatopdf().getTipodoc().getIdelemento()
									.equals(Constantes.ID_DOCUMENTO_POSTERIOR))
								posteriores.add(doc);
						}

						// guardado de previos
						if (previos != null && !previos.isEmpty()) {
							for (DocumentoSuboperacion dad : previos) {
								actoDocumento = new ActoDocumento();
								// comprobar de donde corresponde el iddocumento
								// Documento doc = em.find(Documento.class,
								// dad.getIddocumento());
								// FormatoPDF pdf = em.find(FormatoPDF.class,
								// dad.getIddocumento());

								actoDocumento.setActo(acto);
								actoDocumento.setIdactodoc(GeneradorId.generaId(actoDocumento));
								actoDocumento.setDocumento(dad.getDocumento());
								actoDocumento.setFormatoPdf(dad.getFormatopdf());
								actoDocumento.setTmstmp(tstmp);
								actoDocumento.setIdsesion(idsesion);
								em.persist(actoDocumento);
								bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento",
										"Registrar", "Registro de Documento Previo ");
								em.persist(bg);

							}
						}

						// guardado de posteriores

						if (posteriores != null && !posteriores.isEmpty()) {
							for (DocumentoSuboperacion dad : posteriores) {
								actoDocumento = new ActoDocumento();
								// Documento doc = em.find(Documento.class,
								// dad.getIddocumento());
								// FormatoPDF pdf = em.find(FormatoPDF.class,
								// dad.getIddocumento());

								actoDocumento.setActo(acto);
								actoDocumento.setIdactodoc(GeneradorId.generaId(actoDocumento));
								actoDocumento.setDocumento(dad.getDocumento());
								actoDocumento.setFormatoPdf(dad.getFormatopdf());
								actoDocumento.setTmstmp(tstmp);
								actoDocumento.setIdsesion(idsesion);
								em.persist(actoDocumento);
								bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento",
										"Registrar", "Registro de Documento Posterior ");
								em.persist(bg);
							}
						}
						break;
					case "E":// eliminar el acto y sus documentos asociados
						// acto.setIdacto(da.getIdacto());
						if (da.getIdacto() != null && !da.getIdacto().isEmpty())
							acto = em.find(Acto.class, da.getIdacto());
						else
							acto = null;

						if (acto != null) {
							query = em.createQuery("from ActoDocumento where acto.idacto='" + acto.getIdacto() + "'");
							lista = query.getResultList();
							if (lista != null && !lista.isEmpty()) {

								for (ActoDocumento ad : lista) {
									em.remove(em.contains(ad) ? ad : em.merge(ad));
									bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento",
											"Eliminar",
											"Eliminacion del Documento "
													+ ad.getDocumento().getTipodoc().getDselemento() + " "
													+ ad.getDocumento().getDstitulo());
									em.persist(bg);
								}
							}
							em.remove(em.contains(acto) ? acto : em.merge(acto));
							bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "Acto", "Eliminar",
									"Eliminacion del Acto " + acto.getDsnombre());
							em.persist(bg);
							acto = null;

						}

						break;

					}
					// @omarete SE COMENTA ESTE CODIGO PORQUE SUPUESTAMENTE AQUI
					// SE ASIGNAN LOS PREVIOS Y POSTERIORES
					// PERO LO QUE SEA QUE SE PRETENDIA ESTABA MAL
					/**
					 * if (acto != null) {
					 * 
					 * // guardado de previos previos = da.getPrevios();
					 * 
					 * if (previos != null && !previos.isEmpty()) { for
					 * (DatoActoDocumento dad : previos) {
					 * 
					 * switch (dad.getStatus()) { case "C": actoDocumento = new
					 * ActoDocumento(); System.out.println("id doc" +
					 * dad.getIddocumento()); documento =
					 * em.find(Documento.class, dad.getIddocumento()); if
					 * (documento == null) { pdf = em.find(FormatoPDF.class,
					 * dad.getIddocumento()); } actoDocumento.setActo(acto);
					 * actoDocumento.setIdactodoc(GeneradorId
					 * .generaId(actoDocumento));
					 * actoDocumento.setDocumento(documento);
					 * actoDocumento.setFormatoPdf(pdf);
					 * actoDocumento.setTmstmp(tstmp);
					 * actoDocumento.setIdsesion(idsesion); //
					 * super.getEntityManager().persist(actoDocumento);
					 * inserciones.add(actoDocumento); break;
					 * 
					 * case "E": actoDocumento = em.find( ActoDocumento.class,
					 * dad.getIdactodoc()); if (actoDocumento != null)
					 * eliminaciones.add(actoDocumento); //
					 * super.getEntityManager().remove(getEntityManager().contains(actoDocumento)
					 * // ? actoDocumento : //
					 * getEntityManager().merge(actoDocumento));
					 * 
					 * break;
					 * 
					 * }
					 * 
					 * } }
					 * 
					 * // guardado de posteriores posterioes =
					 * da.getPosteriores(); if (posterioes != null &&
					 * !posterioes.isEmpty()) { for (DatoActoDocumento dad :
					 * posterioes) {
					 * 
					 * switch (dad.getStatus()) { case "C": actoDocumento = new
					 * ActoDocumento(); documento = em.find(Documento.class,
					 * dad.getIddocumento()); if (documento == null) { pdf =
					 * em.find(FormatoPDF.class, dad.getIddocumento()); }
					 * actoDocumento.setActo(acto);
					 * actoDocumento.setIdactodoc(GeneradorId
					 * .generaId(actoDocumento));
					 * actoDocumento.setDocumento(documento);
					 * actoDocumento.setFormatoPdf(pdf);
					 * actoDocumento.setTmstmp(tstmp);
					 * actoDocumento.setIdsesion(idsesion); //
					 * super.getEntityManager().persist(actoDocumento);
					 * inserciones.add(actoDocumento); break;
					 * 
					 * case "E": actoDocumento = em.find( ActoDocumento.class,
					 * dad.getIdactodoc()); if (actoDocumento != null)
					 * eliminaciones.add(actoDocumento); //
					 * super.getEntityManager().remove(getEntityManager().contains(actoDocumento)
					 * // ? actoDocumento : //
					 * getEntityManager().merge(actoDocumento));
					 * 
					 * break;
					 * 
					 * }
					 * 
					 * } }
					 * 
					 * }
					 **/

				}
			}
			for (ActoDocumento ad : eliminaciones) {
				em.remove(em.contains(ad) ? ad : em.merge(ad));
				String text = "";
				if (ad.getDocumento() == null)
					text = ad.getFormatoPdf().getTipodoc().getDselemento() + " " + ad.getFormatoPdf().getDstitulo();
				else
					text = ad.getDocumento().getTipodoc().getDselemento() + " " + ad.getDocumento().getDstitulo();
				bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento", "Eliminar",
						"Eliminacion del Documento " + text);
				em.persist(bg);
			}

			for (ActoDocumento ad : inserciones) {
				em.persist(ad);
				String text = "";
				if (ad.getDocumento() == null)
					text = ad.getFormatoPdf().getTipodoc().getDselemento() + " " + ad.getFormatoPdf().getDstitulo();
				else
					text = ad.getDocumento().getTipodoc().getDselemento() + " " + ad.getDocumento().getDstitulo();
				bg = bitGenHelp.crearBitacora(idusuario, idexpediente, null, "ActoDocumento", "Registrar",
						"Registro del Documento " + text);
				em.persist(bg);
			}

			tx.commit();
			b = true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return b;
	}

	// @omarete 07/10/2014 SE CONSIDERA REMOVER ESTE METODO QUE SOLO BUSCA QUE
	// HAYA UN UNICO ACTO POR EXPEDIENTE, YA QUE PUEDE HABER VARIOS
	@SuppressWarnings("unchecked")
	@Override
	public boolean esoperacionUnica(String idExpediente, String idOperacion) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		boolean b = true;
		try {
			Query query = em.createQuery(
					"from Acto where suboperacion.operacion.idoperacion = :idoperacion and expediente.idexpediente = :idexpediente ");
			query.setParameter("idoperacion", idOperacion);
			query.setParameter("idexpediente", idExpediente);

			List<Acto> lista = query.getResultList();
			if (lista != null && !lista.isEmpty())
				b = false;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
		return b;
	}

	@Override
	public String obtenerNumExpedientePorId(String id) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT numexpediente FROM Expediente ");
			sql.append(" WHERE idexpediente = :id");

			Query query = em.createQuery(sql.toString());
			query.setParameter("id", id);

			@SuppressWarnings("unchecked")
			List<String> resultados = query.getResultList();
			if (resultados == null || resultados.isEmpty()) {
				return null;
			}
			String numExpediente = resultados.get(0);
			if (numExpediente == null || numExpediente.trim().isEmpty()) {
				return null;
			}
			return numExpediente;

		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Expediente> obtenerExpedientes(Expediente expediente, Compareciente... compareciente)
			throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Expediente> expedienteList;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT e FROM Expediente e ");
			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (expediente.getNumexpediente() != null && !expediente.getNumexpediente().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("e.numexpediente = '" + expediente.getNumexpediente() + "' ");
				params.put("numexpediente", expediente.getNumexpediente());
				first = false;
			}
			if (expediente.getTramite() != null && expediente.getTramite().getCliente() != null
					&& expediente.getTramite().getCliente().getDsnombrecompleto() != null
					&& !expediente.getTramite().getCliente().getDsnombrecompleto().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				String nombre = expediente.getTramite().getCliente().getDsnombrecompleto();
				sql.append("e.tramite.cliente.dsnombrecompleto LIKE '%" + nombre + "%' ");
				first = false;
			}
			// @omarete ----------------------------------------------
			if (expediente.getAbogado() != null && ((!expediente.getAbogado().getDsnombre().isEmpty()
					&& expediente.getAbogado().getDsnombre() != null)
					|| (!expediente.getAbogado().getDsmaterno().isEmpty()
							&& expediente.getAbogado().getDsmaterno() != null)
					|| (!expediente.getAbogado().getDspaterno().isEmpty()
							&& expediente.getAbogado().getDspaterno() != null))) {
				boolean andClause = false;
				sql.append(first ? "WHERE " : "AND ");
				if (expediente.getAbogado().getDsnombre() != null && !expediente.getAbogado().getDsnombre().isEmpty()) {
					sql.append("e.abogado.dsnombre LIKE '%" + expediente.getAbogado().getDsnombre() + "%' ");
					andClause = true;
				}
				if (expediente.getAbogado().getDspaterno() != null
						&& !expediente.getAbogado().getDspaterno().isEmpty()) {
					sql.append(andClause ? "AND " : "");
					sql.append(" e.abogado.dspaterno LIKE '%" + expediente.getAbogado().getDspaterno() + "%' ");
					andClause = true;
				}
				if (expediente.getAbogado().getDsmaterno() != null
						&& !expediente.getAbogado().getDsmaterno().isEmpty()) {
					sql.append(andClause ? "AND" : "");
					sql.append(" e.abogado.dsmaterno LIKE '%" + expediente.getAbogado().getDsmaterno() + "%' ");
				}
				params.put("dsnombre", expediente.getAbogado().getDsnombre());
				params.put("dspaterno", expediente.getAbogado().getDspaterno());
				params.put("dsmaterno", expediente.getAbogado().getDsmaterno());
				first = false;
			}
			if (expediente.getDsreferencia() != null && !expediente.getDsreferencia().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("e.dsreferencia LIKE '%" + expediente.getDsreferencia() + "%'");
				params.put("dsref", expediente.getDsreferencia());
				first = false;
			}
			if (compareciente.length > 0 && compareciente[0] != null && compareciente[0].getPersona() != null
					&& compareciente[0].getPersona().getDsnombrecompleto() != null
					&& !compareciente[0].getPersona().getDsnombrecompleto().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("e.idexpediente IN (SELECT comp.acto.expediente.idexpediente FROM Compareciente comp ");
				sql.append("WHERE comp.persona.dsnombrecompleto LIKE '%"
						+ compareciente[0].getPersona().getDsnombrecompleto() + "%')");
				first = false;
			}
			if (expediente.getFechainicial() != null && expediente.getFechafinal() != null) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("e.fechainicial BETWEEN '" + expediente.getFechainicialstr() + "' AND '"
						+ expediente.getFechafinalstr() + "'");
			}
			// -------------------------------------------------------
			/** ordenar **/
			sql.append("AND e.tramite.idtramite !=NULL ORDER BY e.numexpediente ");
			System.out.println("SQL:" + sql);
			Query query = em.createQuery(sql.toString());
			// for (String param : params.keySet()) {
			// System.out.println("parametro "+param);
			// System.out.println("param "+params.get(param));
			// query.setParameter(param, params.get(param));
			// }
			expedienteList = (List<Expediente>) query.getResultList();
			if (expedienteList != null) {
				ComparecienteDaoImpl compDao = new ComparecienteDaoImpl();
				for (Expediente e : expedienteList) {
					e.setComparecientesList(
							new ArrayList<Compareciente>(compDao.obtenerPorExpediente(e.getIdexpediente())));
					if (e.getComparecientesList() != null && e.getComparecientesList().size() > 0) {
						for (Compareciente comp : e.getComparecientesList()) {
							comp.setFirma(null);
							comp.setActo(null);
							comp.setTipoCompareciente(null);
							comp.setRegistroRi(null);
							comp.getPersona().setNacionalidad(null);
							comp.getPersona().setDomicilio(null);
							
							comp.getPersona().setTipopersona(null);
							if (comp.getContacto() != null) {
								comp.getContacto().setPersona(null);
							}
						}
					}

					e.getTramite().getCliente().setNacionalidad(null);
					e.getTramite().getCliente().setTipopersona(null);

				}
			}
			return expedienteList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Persona> obtenerClientesDeExpediente() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		List<Persona> personaList;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT e.tramite.cliente FROM Expediente e ");
			/** ordernar **/
			sql.append(" ORDER BY e.tramite.cliente.dsnombrecompleto ");
			Query query = em.createQuery(sql.toString());
			personaList = (List<Persona>) query.getResultList();
			if (personaList != null) {
				for (Persona p : personaList) {
					p.setTipopersona(null);
					p.setNacionalidad(null);
				}
			}
			return personaList;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String obtenerTramitePorExpedienteId(String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		String idtramite = null;
		try {
			query = em.createQuery("select tramite.idtramite from Expediente where idexpediente = :idexpediente ");
			query.setParameter("idexpediente", idexpediente);
			lista = query.getResultList();
			if (lista == null || lista.isEmpty()) {
				return null;
			}
			idtramite = lista.get(0);
			if (idtramite == null || idtramite.isEmpty()) {
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

	@SuppressWarnings("unchecked")
	@Override
	public boolean exiteTramiteRegistrado(String idtramite) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		Query query;
		List<String> lista;
		try {
			query = em.createQuery("select idexpediente from Expediente where tramite.idtramite = :idtramite ");
			query.setParameter("idtramite", idtramite);
			lista = query.getResultList();
			if (lista == null || lista.isEmpty()) {
				return false;
			}
			String result = lista.get(0);
			if (result == null || result.isEmpty()) {
				return false;
			}
			/** el tramite dado, ya esta registrado en la BD. **/
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Expediente> listaExpedientes() throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Expediente> query = em.createQuery("FROM Expediente ORDER BY tmstmp DESC", Expediente.class);
			List<Expediente> expedientes = query.getResultList();
			for (Expediente expediente : expedientes) {

				Tramite t = expediente.getTramite();
				if (t instanceof HibernateProxy) {
					t = (Tramite) ((HibernateProxy) t).getHibernateLazyInitializer().getImplementation();
					Usuario abogado = t.getAbogado();
					if (abogado instanceof HibernateProxy) {
						abogado = (Usuario) ((HibernateProxy) t).getHibernateLazyInitializer().getImplementation();
						t.setAbogado(abogado);
					}
					Persona cliente = t.getCliente();
					if (cliente instanceof HibernateProxy) {
						cliente = (Persona) ((HibernateProxy) t).getHibernateLazyInitializer().getImplementation();
						ElementoCatalogo nacionalidad = cliente.getNacionalidad();
						if (nacionalidad instanceof HibernateProxy) {
							nacionalidad = (ElementoCatalogo) ((HibernateProxy) t).getHibernateLazyInitializer()
									.getImplementation();
							cliente.setNacionalidad(null);
						}
						cliente.setNacionalidad(null);
						cliente.setTipopersona(null);
						t.setCliente(cliente);
					}
					t.setStatus(null);
					expediente.setTramite(t);
				}
				Persona c = expediente.getTramite().getCliente();
				expediente.setTramite(t);
				expediente.getTramite().setCliente(c);
				expediente.getTramite().getCliente().setNacionalidad(null);
				expediente.getTramite().getCliente().setTipopersona(null);

				System.out.println("=====>   expediente: " + expediente.getIdexpediente());
				System.out.println("=====>   tramite: " + expediente.getTramite().getIdtramite());
				System.out.println("=====>   abogado: " + expediente.getTramite().getAbogado().getDsiniciales());
				System.out.println("=====>   cliente: " + expediente.getTramite().getCliente().getDsnombre());
			}
			return expedientes;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

	@Override
	public List<Expediente> obtenerExpedienteXAbogado(String idabogado) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Expediente> query = em
					.createQuery("FROM Expediente WHERE idabogado = :idabogado ORDER BY tmstmp DESC", Expediente.class);
			query.setParameter("idabogado", idabogado);
			List<Expediente> expedientes = query.getResultList();
			for (Expediente expediente : expedientes) {
				expediente.setTramite(null);
			}
			return expedientes;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

	@Override
	public List<Expediente> obtenerExpedienteXAbogado(String idabogado, String year) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		try {

			TypedQuery<Expediente> query = em
					.createQuery("FROM Expediente WHERE idabogado = :idabogado AND numexpediente LIKE '" + year + "/"
							+ "%' ORDER BY tmstmp DESC", Expediente.class);
			query.setParameter("idabogado", idabogado);
			List<Expediente> expedientes = query.getResultList();
			return expedientes;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}

	}

}
