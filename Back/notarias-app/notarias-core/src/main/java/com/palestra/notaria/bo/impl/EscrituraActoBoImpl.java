package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.FormularioBo;
import com.palestra.notaria.bo.ValorFormularioBo;
import com.palestra.notaria.dao.EscrituraActoDao;
import com.palestra.notaria.dao.impl.EscrituraActoDaoImpl;
import com.palestra.notaria.dato.DatoActoMultiSelect;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Escritura;
import com.palestra.notaria.modelo.EscrituraActo;
import com.palestra.notaria.modelo.Formulario;
import com.palestra.notaria.modelo.ValorFormulario;

public class EscrituraActoBoImpl extends GenericBoImpl<EscrituraActo> implements
		EscrituraActoBo {

	private EscrituraActoDao escrituraActoDao;

	public EscrituraActoBoImpl() {
		this.escrituraActoDao = new EscrituraActoDaoImpl();
		super.dao = this.escrituraActoDao;
	}
	
	@Override
	public List<EscrituraActo> findByEscrituraId(String id) throws NotariaException{
		return this.escrituraActoDao.findByEscrituraId(id);
	}
	
	@Override
	public String obtenIdEscrituraPorActoId(String id)throws NotariaException {
		return this.escrituraActoDao.obtenIdEscrituraPorActoId(id);
	}
	
	@Override
	public List<EscrituraActo> buscarPorEscrituraIdCompleto(
			EscrituraActo escrituraActo) throws NotariaException{
		return this.escrituraActoDao.buscarPorEscrituraIdCompleto(escrituraActo);
	}
	
	@Override
	public List<DatoActoMultiSelect> buscarActosDisponibleParaEscritura(
			String idEscritura, String idExpediente)throws NotariaException {
		return this.escrituraActoDao.buscarActosDisponibleParaEscritura(idEscritura, idExpediente);
	}
	
	@Override
	public List<DatoActoMultiSelect> buscarActosDisponibles(String idexpediente)throws NotariaException {
		return this.escrituraActoDao.buscarActosDisponibles(idexpediente);
	}
	
	@Override
	public Boolean isValidAddActos(String idActos)throws NotariaException {
		return this.escrituraActoDao.isValidAddActos(idActos);
	}
	
	@Override
	public int deleteByEscrituraId(String idEscritura) throws NotariaException{
		return this.escrituraActoDao.deleteByEscrituraId(idEscritura);
	}
	
	@Override
	public String obtenNotarioDatoByEscrituraId(String id) throws NotariaException{
		return this.escrituraActoDao.obtenNotarioDatoByEscrituraId(id);
	}
	
	@Override
	public List<DatoActoMultiSelect> obtenActoNombrePorEscrituraId(String id)throws NotariaException {
		return this.escrituraActoDao.obtenActoNombrePorEscrituraId(id);
	}

	@Override
	public String validaCertificado(Escritura escritura) throws NotariaException {
		
		List<EscrituraActo> datos = this.escrituraActoDao.findByEscrituraId(escritura.getIdescritura());
		StringBuilder respuesta = new StringBuilder();
		ValorFormularioBo vfBo = new ValorFormularioBoImpl();
		FormularioBo frbo = new FormularioBoImpl();
		
		for(EscrituraActo esact:datos){
			
			Formulario frm = frbo.buscarFormulariosPorActoYnombrecorto(esact.getActo().getIdacto(),"gravamen");
			if(frm!=null){
				ValorFormulario vf = vfBo.valorPorNombreVariable(frm,"fecha");
				if(vf!=null){
					respuesta.append(vf.getValorcadena());
				}
			}
		}
		
		return respuesta.toString();
	}

}
