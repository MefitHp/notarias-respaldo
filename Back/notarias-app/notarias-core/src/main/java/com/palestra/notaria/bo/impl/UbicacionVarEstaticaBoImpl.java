package com.palestra.notaria.bo.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.UbicacionVarEstaticaBo;
import com.palestra.notaria.dao.UbicacionVarEstaticaDao;
import com.palestra.notaria.dao.impl.UbicacionVarEstaticaDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.UbicacionVarEstatica;
import com.palestra.notaria.modelo.Variable;
import com.palestra.notaria.util.GeneradorId;

public class UbicacionVarEstaticaBoImpl extends GenericBoImpl<UbicacionVarEstatica> implements UbicacionVarEstaticaBo{

	private UbicacionVarEstaticaDao ubicacionVarEstaticaDao;
	
	public UbicacionVarEstaticaBoImpl() {
		this.ubicacionVarEstaticaDao = new UbicacionVarEstaticaDaoImpl();
		super.dao = this.ubicacionVarEstaticaDao;
	}
	
	@Override
	public List<Object> obtenerValorVarEstatica(UbicacionVarEstatica ubicacionVarEstatica, Object filtro) throws NotariaException{
		return ubicacionVarEstaticaDao.obtenerValorVarEstaticas(ubicacionVarEstatica,filtro);
	}
	
	@Override
	public List<Object[]> obtenerValoresFromHqlQuery(String query) throws NotariaException {
		return ubicacionVarEstaticaDao.obtenerValoresFromHqlQuery(query);
	}
	
	@Override
	public List<Object> getSingleValFromHqlQuery(String query) throws NotariaException {
		return ubicacionVarEstaticaDao.getSingleValFromHqlQuery(query);
	}
	
	@Override
	public List<Object[]> obtenerValoresFromSqlQuery(String query) throws NotariaException {
		return ubicacionVarEstaticaDao.obtenerValoresFromSqlQuery(query);
	}
	
	@Override
	public List<UbicacionVarEstatica> findAllComplete() throws NotariaException {
		return ubicacionVarEstaticaDao.findAllComplete();
	}
	
	@Override
	public Integer actualizar(UbicacionVarEstatica ubicacion) throws NotariaException{
		UbicacionVarEstatica actual = this.findById(ubicacion.getIdvarest());
		if(actual!=null){
			System.out.printf("=====> El objeto UbicacionVarEstatica %s se encuentra en la unidad de persistencia, se actualizara.%n", ubicacion.getIdvarest());
			ubicacion.setIdvarest(actual.getIdvarest());
			ubicacion.setVariable(actual.getVariable());
			this.update(ubicacion);
			return 0;
		}else{
			System.out.printf("=====> El objeto UbicacionVarEstatica %s no se encuentra en la unidad de persistencia, se creara el enviado desde ventana.%n", ubicacion.getIdvarest());
			ubicacion.setIdvarest(GeneradorId.generaId(ubicacion));
			this.save(ubicacion);
			return 1;
		}		
	}
	
	@Override 
	public UbicacionVarEstatica obtieneVariableEstatica(String idvariable)throws NotariaException{
		UbicacionVarEstatica calculo = ubicacionVarEstaticaDao.findByVariable(idvariable);
		if(calculo!=null){
			calculo.setVariable(null);
			System.out.println(String.format("=====> El calculo para la variable simple %s existe%n",idvariable));
		}else{
			System.out.println(String.format("=====> El calculo para la variable simple %s no existe%n",idvariable));
		}
		return calculo;
	}
	
	@Override 
	public UbicacionVarEstatica obtieneVariableEstatica(Variable variable)throws NotariaException{
		return obtieneVariableEstatica(variable.getIdvariable());
	}	
	
	@Override
	public UbicacionVarEstatica obtieneVariableEstaticaPorNombre(String nombre) throws NotariaException{
		return ubicacionVarEstaticaDao.findByNombre(nombre);
	}
	
	@Override
	public List<String> obtieneCamposEntidad(String entidad) throws NotariaException{
		if (entidad!=null && !entidad.isEmpty()){
			List<String> vuelta = new ArrayList<>();
			try {
				Class<?> klass = Class.forName("com.palestra.notaria.modelo."+entidad);
				System.out.printf("=====> Estamos listos para buscar en la clase %s%n", klass.getCanonicalName());
				for(Field field: klass.getDeclaredFields()){
					System.out.printf("La clase %s, contiene al campo %s%n", klass.getCanonicalName(), field.getName());
					if(!(field.getName().equals("tmstmp")||field.getName().contains("sesion")||field.getName().toLowerCase().contains("serialversion"))){
						vuelta.add(field.getName());
					}
				}
				return vuelta;
			} catch (ClassNotFoundException e) {				
				e.printStackTrace();
				throw new NotariaException("=====> No se localizo una clase entidad existente en el repositorio.");
			}			
		}else{
			throw new NotariaException("=====> No se localizo una entidad valida.");
		}
		
	}
}
