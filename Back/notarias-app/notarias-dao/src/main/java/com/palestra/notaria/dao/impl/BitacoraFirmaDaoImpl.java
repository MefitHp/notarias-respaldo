package com.palestra.notaria.dao.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.proxy.HibernateProxy;

import com.palestra.notaria.dao.BitacoraFirmaDao;
import com.palestra.notaria.dato.Constantes;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.helper.BitacoraGeneralHelper;
import com.palestra.notaria.modelo.BitacoraFirma;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.Persona;
import com.palestra.notaria.modelo.Usuario;
import com.palestra.notaria.util.GeneradorId;

public class BitacoraFirmaDaoImpl extends
		GenericDaoImpl<BitacoraFirma, Integer> implements BitacoraFirmaDao {

	public BitacoraFirmaDaoImpl() {
		super(BitacoraFirma.class);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BitacoraFirma> obtenerListaBitacoraFirma(
			BitacoraFirma bitacoraFirma) throws NotariaException {

		EntityManager em = factory.createEntityManager();
		List<BitacoraFirma> lista;
		try {
			StringBuilder sql = new StringBuilder();
			sql.append(" SELECT bf FROM BitacoraFirma bf ");
			Map<String, Object> params = new HashMap<String, Object>();
			boolean first = true;
			if (bitacoraFirma != null && bitacoraFirma.getEscritura() != null
					&& bitacoraFirma.getEscritura().getIdescritura() != null
					&& !bitacoraFirma.getEscritura().getIdescritura().isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("bf.escritura.idescritura = :idescritura ");
				params.put("idescritura", bitacoraFirma.getEscritura()
						.getIdescritura());
				first = false;
			}
			if (bitacoraFirma != null
					&& bitacoraFirma.getUsuariofirma() != null
					&& bitacoraFirma.getUsuariofirma().getIdusuario() != null
					&& !bitacoraFirma.getUsuariofirma().getIdusuario()
							.isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("bf.usuariofirma.idusuario = :idusuario ");
				params.put("idusuario", bitacoraFirma.getUsuariofirma()
						.getIdusuario());
				first = false;
			}
			if (bitacoraFirma != null
					&& bitacoraFirma.getCompareciente() != null
					&& bitacoraFirma.getCompareciente().getIdcompareciente() != null
					&& !bitacoraFirma.getCompareciente().getIdcompareciente()
							.isEmpty()) {
				sql.append(first ? "WHERE " : "AND ");
				sql.append("bf.compareciente.idcompareciente = :idcompareciente ");
				params.put("idcompareciente", bitacoraFirma.getCompareciente()
						.getIdcompareciente());
				first = false;
			}
			/** ordernar **/
			sql.append(" ORDER BY bf.tmstmp ");
			Query query = em.createQuery(sql.toString());
			for (String param : params.keySet()) {
				query.setParameter(param, params.get(param));
			}
			lista = (List<BitacoraFirma>) query.getResultList();
			if (lista != null) {
				Escritura escritura = null;
				Usuario usuario = null;
				Compareciente compareciente = null;
				Persona persona = null;
				for (BitacoraFirma bf : lista) {
					escritura = bf.getEscritura();
					if (escritura instanceof HibernateProxy) {
						escritura = (Escritura) ((HibernateProxy) escritura)
								.getHibernateLazyInitializer()
								.getImplementation();
						escritura.setLibro(null);
						escritura.setExpediente(null);
						escritura.setNotario(null);
					}
					bf.setEscritura(escritura);

					usuario = bf.getUsuariofirma();
					if (usuario instanceof HibernateProxy) {
						usuario = (Usuario) ((HibernateProxy) usuario)
								.getHibernateLazyInitializer()
								.getImplementation();
						usuario.setRol(null);
					}
					bf.setUsuariofirma(usuario);

					compareciente = bf.getCompareciente();
					if (compareciente instanceof HibernateProxy) {
						compareciente = (Compareciente) ((HibernateProxy) compareciente)
								.getHibernateLazyInitializer()
								.getImplementation();
						compareciente.setActo(null);
						persona = compareciente.getPersona();
						if (persona instanceof HibernateProxy) {
							persona = (Persona) ((HibernateProxy) persona)
									.getHibernateLazyInitializer()
									.getImplementation();
							persona.setTipopersona(null);
							persona.setNacionalidad(null);
						}
						compareciente.setPersona(persona);
						compareciente.setTipoCompareciente(null);
						compareciente.setRegistroRi(null);
					}
					bf.setCompareciente(compareciente);
				}
			}
			return lista;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			throw new NotariaException(e.getCause());
		}finally{
			em.close();
		}
	}

	@Override
	public Boolean saveUpdateBitacora(ArrayList<Compareciente> comparecientes,
			Usuario usuario, String idescritura, String idexpediente) throws NotariaException {
		EntityManager em = factory.createEntityManager();
		EntityTransaction tx = null;
		BitacoraGeneralHelper bitGenHelp = new BitacoraGeneralHelper();
		Query query;
		try {
			tx = em.getTransaction();
			tx.begin();
			/**
			 * eliminar los registros, se volveran a agregar por si se hizo
			 * actualizacion
			 **/
			query = em
					.createQuery("delete BitacoraFirma where escritura.idescritura = :idescritura");
			query.setParameter("idescritura", idescritura);
			query.executeUpdate();

			for (Compareciente c : comparecientes) {
				BitacoraFirma bf = new BitacoraFirma();
				/** asignar propiedades que no se capturan en pantalla **/
				bf.setIdbitfirma(GeneradorId.generaId(bf));
				bf.setIdsesion(usuario.getIdsesionactual());
				bf.setTmstmp(new Timestamp((new Date()).getTime()));
				bf.setCompareciente(c);
				Escritura escritura = new Escritura();
				escritura.setIdescritura(idescritura);
				bf.setEscritura(escritura);
				bf.setUsuariofirma(usuario);
				/** guardar la bitacora firma **/
				em.persist(bf);
				bitGenHelp.crearBitacora(usuario.getIdusuario(),
						idexpediente, null, "Bitácora Firma",
						Constantes.OPERACION_REGISTRO,
						"Se guarda/actualiza una bitácora firma.");
			}
			tx.commit();
			return true;
		} catch (PersistenceException e) {
			e.printStackTrace(System.out);
			tx.rollback();
			throw new NotariaException(e.getCause());
		} finally {
			em.close();
		}
	}

}
