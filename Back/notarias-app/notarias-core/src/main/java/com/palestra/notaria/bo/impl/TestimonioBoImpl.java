package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.TestimonioBo;
import com.palestra.notaria.dao.TestimonioDao;
import com.palestra.notaria.dao.impl.TestimonioDaoImpl;
import com.palestra.notaria.dato.DatoTestimonio;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Testimonio;

public class TestimonioBoImpl extends GenericBoImpl<Testimonio> implements TestimonioBo{

	private TestimonioDao testimonioDao;
	
	public TestimonioBoImpl() {
		this.testimonioDao = new TestimonioDaoImpl();
		super.dao = this.testimonioDao;
	}

	@Override
	public boolean registrarTestimonio(Testimonio test, String idexpediente,
			String idusuario, String idsesion) throws NotariaException{
		return this.testimonioDao.registrarTestimonio(test, idexpediente, idusuario, idsesion);
	}

	@Override
	public List<Testimonio> obtenerTestimoniosPorExp(String idexpediente)throws NotariaException {
		return this.testimonioDao.obtenerTestimoniosPorExp(idexpediente);
	}

	@Override
	public DatoTestimonio obtenerPorIdCompleto(String idtestimonio) throws NotariaException{
		return this.testimonioDao.obtenerPorIdCompleto(idtestimonio);
	}

	@Override
	public boolean elimiarTestimonio(String idtestimonio, String idusuario)throws NotariaException {
		return this.testimonioDao.elimiarTestimonio(idtestimonio, idusuario);
	}

	@Override
	public boolean aprobarEtapa(String idreletapatesti, String idusuario)throws NotariaException {
		return this.testimonioDao.aprobarEtapa(idreletapatesti, idusuario);
	}
}

