package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.FormatoPDFBO;
import com.palestra.notaria.dao.FormatoPDFDao;
import com.palestra.notaria.dao.impl.FormatoPDFDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.FormatoPDF;

public class FormatoPDFBOImpl extends GenericBoImpl<FormatoPDF> implements
		FormatoPDFBO {
	
	private FormatoPDFDao formatoDao;
	
	public FormatoPDFBOImpl(){
		this.formatoDao = new FormatoPDFDaoImpl();
		super.dao = this.formatoDao;
	}

	@Override
	public FormatoPDF buscarXNombre(String nombre)throws NotariaException{
		return this.formatoDao.buscarXNombre(nombre);
	}
	
//	@Override
//	public void eliminaDetalles(String identificador) throws NotariaException {
//		FormatoPDFDao dao = new FormatoPDFDaoImpl();
//		dao.eliminaDetalles(identificador);
//	}
//
//	@Override
//	public List<FormatoPDFDetalle> listarFormatoDetalle(String identificador)
//			throws NotariaException {
//		FormatoPDFDao dao = new FormatoPDFDaoImpl();
//		return dao.formatoDetalle(identificador);
//	}
//
//	@Override
//	public List<FormatoPDFDetalle> agregarDetalle(FormatoPDFDetalle detalle)
//			throws NotariaException {
//		List<FormatoPDFDetalle> existentes = listarFormatoDetalle(detalle
//				.getIdftopdf().getIdentificador());
//		List<FormatoPDFDetalle> nuevaLista = new ArrayList<>();
//		if (!existe(existentes, detalle.getDsvariable())) {
//			FormatoPDFDetalleDao dao = new FormatoPDFDetalleDaoImpl();
//			dao.save(detalle);
//			nuevaLista.add(detalle);
//		}
//		nuevaLista.addAll(existentes);
//		return nuevaLista;
//
//	}
//
//	@Override
//	public List<FormatoPDFDetalle> agregarDetalle(
//			List<FormatoPDFDetalle> detalles) throws NotariaException {
//
//		if (detalles.size() > 0) {
//			List<FormatoPDFDetalle> existentes = listarFormatoDetalle(detalles
//					.get(0).getIdftopdf().getIdentificador());
//			List<FormatoPDFDetalle> nuevaLista = new ArrayList<>();
//			for (FormatoPDFDetalle detalle : detalles) {
//				if (!existe(existentes, detalle.getDsvariable())) {
//					FormatoPDFDetalleDao dao = new FormatoPDFDetalleDaoImpl();
//					dao.save(detalle);
//					nuevaLista.add(detalle);
//				}
//			}
//			nuevaLista.addAll(existentes);
//			return nuevaLista;
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public List<FormatoPDFDetalle> quitarDetalle(FormatoPDFDetalle detalle)
//			throws NotariaException {
//		String identificador = detalle.getIdftopdf().getIdentificador();
//		FormatoPDFDetalleDao dao = new FormatoPDFDetalleDaoImpl();
//		dao.delete(detalle);
//		return listarFormatoDetalle(identificador);
//	}
//
//	private boolean existe(List<FormatoPDFDetalle> existentes, String detalle)
//			throws NotariaException {
//		for (FormatoPDFDetalle existente : existentes) {
//			if (existente.equals(detalle)) {
//				return true;
//			}
//		}
//		return false;
//	}

}
