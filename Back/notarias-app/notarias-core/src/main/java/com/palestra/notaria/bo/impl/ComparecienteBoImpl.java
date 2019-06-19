package com.palestra.notaria.bo.impl;

import java.util.List;

import com.palestra.notaria.bo.ComparecienteBo;
import com.palestra.notaria.bo.ComparecienteConyugeBo;
import com.palestra.notaria.bo.EscrituraActoBo;
import com.palestra.notaria.bo.EscrituraBo;
import com.palestra.notaria.dao.ComparecienteDao;
import com.palestra.notaria.dao.impl.ComparecienteDaoImpl;
import com.palestra.notaria.dato.DatoBusquedaCompareciente;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Compareciente;
import com.palestra.notaria.modelo.ComparecienteConyuge;
import com.palestra.notaria.modelo.ComparecienteRepresentante;
import com.palestra.notaria.modelo.Escritura;

public class ComparecienteBoImpl extends GenericBoImpl<Compareciente> implements ComparecienteBo{

	private ComparecienteDao comparecienteDao;
	
	public ComparecienteBoImpl() {
		this.comparecienteDao = new ComparecienteDaoImpl();
		super.dao = this.comparecienteDao;
	}
	
	@Override
	public List<DatoBusquedaCompareciente> findByActoId(String id) {
		try{
			return comparecienteDao.findByActoId(id);
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	@Override
	public Compareciente buscarPorIdCompleto(Compareciente compareciente) throws NotariaException {
		return this.comparecienteDao.buscarPorIdCompleto(compareciente);
	}
	
	@Override
	public List<Compareciente> listadoComparecientes(Compareciente compareciente, String... tipoComp) throws NotariaException {
		return this.comparecienteDao.listadoComparecientes(compareciente,tipoComp);
	}

	@Override
	public List<Compareciente> listadoComparecientesByActo(String acto, String tipoCompareciente) throws NotariaException{
		return this.comparecienteDao.listadoComparecientes(acto, tipoCompareciente);
	}
	@Override
	public List<Compareciente> listadoComparecientesByActo(String acto) throws NotariaException{
		return this.comparecienteDao.listadoComparecientes(acto);
	}
	
	@Override
	public Boolean registrarCompareciente(Compareciente compareciente, List<ComparecienteRepresentante> cr) throws NotariaException {
		validaNopaso(compareciente);
		return this.comparecienteDao.registrarCompareciente(compareciente,cr);
	}
	
	@Override
	public Boolean eliminarCompareciente(Compareciente compareciente) throws NotariaException {
		validaNopaso(compareciente);
		ComparecienteConyugeBo compconBo = new ComparecienteConyugeBoImpl();
		
		return this.comparecienteDao.eliminarCompareciente(compareciente);
	}
	
	@Override
	public Boolean actualizarCompareciente(Compareciente compareciente,
			List<ComparecienteRepresentante> cr) throws NotariaException {
		validaNopaso(compareciente);
		return this.comparecienteDao.actualizarCompareciente(compareciente, cr);
	}
	
	@Override
	public List<Compareciente> disponiblesPorActoEscritura(String id) throws NotariaException {
		return this.comparecienteDao.disponiblesPorActoEscritura(id);
	}
	
	@Override
	public Boolean actualizarRepresentantes(Compareciente compareciente,Compareciente representante,
			List<ComparecienteRepresentante> cr) throws NotariaException {
		validaNopaso(compareciente);
		return this.comparecienteDao.actualizarRepresentantes(compareciente,representante, cr);
	}
	
	@Override
	public Boolean actualizaRegistroRi(String idcompareciente) throws NotariaException {
		return this.comparecienteDao.actualizaRegistroRi(idcompareciente);
	}
	
	@Override
	public List<Compareciente> getByIdPersona(String idpersona) throws NotariaException{
		return this.comparecienteDao.getByIdPersona(idpersona);
	}
	
	@Override
	public boolean escNoPaso(Compareciente compareciente)throws NotariaException{
		EscrituraActoBo esactbo = new EscrituraActoBoImpl();
		String idesc = esactbo.obtenIdEscrituraPorActoId(compareciente.getActo().getIdacto());
		if(idesc != null){
			EscrituraBo escbo = new EscrituraBoImpl();
			Escritura esc = escbo.findById(idesc);
			if(esc!=null){
				if(esc.getNopaso()){
					return true;
				}
			}
		}
		return false;
	}
	
	
	
	private void validaNopaso(Compareciente compareciente)throws NotariaException{
		if(compareciente.getActo()!=null){
			if(escNoPaso(compareciente)){
				throw new NotariaException("La escritura se encuentra como \"NO PASO\" y no es posible guardar o modificar un compareciente");
			}
		}
		
	}

	@Override
	public Escritura obtieneEscritura(String idacto) throws NotariaException {
		EscrituraActoBo esactbo = new EscrituraActoBoImpl();
		String idesc = esactbo.obtenIdEscrituraPorActoId(idacto);
		if(idesc != null){
			EscrituraBo escbo = new EscrituraBoImpl();
			Escritura esc = escbo.findById(idesc);
 			if(esc!=null){
				esc.setExpediente(null);
				esc.setNotario(null);
				esc.setLibro(null);
				return esc;
			}
		}
		return null;
	}
	
}
