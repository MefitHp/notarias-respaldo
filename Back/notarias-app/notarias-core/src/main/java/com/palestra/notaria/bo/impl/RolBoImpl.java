package com.palestra.notaria.bo.impl;

import java.util.ArrayList;
import java.util.List;

import com.palestra.notaria.bo.RolBo;
import com.palestra.notaria.dao.RolDao;
import com.palestra.notaria.dao.RolGestionCompetenciaDAO;
import com.palestra.notaria.dao.impl.RolDaoImpl;
import com.palestra.notaria.dao.impl.RolGestionCompetenciasDAOImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Rol;
import com.palestra.notaria.modelo.RolGestionCompetencia;

public class RolBoImpl extends GenericBoImpl<Rol> implements RolBo {
	
	RolDao rolDao;
	
	public RolBoImpl(){
		this.rolDao = new RolDaoImpl();
		super.dao = this.rolDao;
	}

	@Override
	public List<RolGestionCompetencia> competencias(Rol rol)
			throws NotariaException {
		Rol persistido = rolDao.findById(rol.getIdrol()!=null?rol.getIdrol():"");
		if(persistido!=null){
			RolGestionCompetenciaDAO gcDAO = new RolGestionCompetenciasDAOImpl(RolGestionCompetencia.class);
			List<RolGestionCompetencia> misCompetencias = gcDAO.competencias(persistido.getIdrol());
			if(misCompetencias==null) misCompetencias = new ArrayList<RolGestionCompetencia>();
			return misCompetencias;			
		} else {
			throw new NotariaException("No se ha localizado el elemento Rol en la unidad de persistencia. Verifique ["+rol.getIdrol()!=null?rol.getIdrol():"null"+"]");
		}		
	}

	@Override
	public int registraCompetencias(List<RolGestionCompetencia> competencias)
			throws NotariaException {
		int contador = 0;
		if(competencias.size()>0){
			Rol rol;
			for(RolGestionCompetencia competencia:competencias){
				rol = competencia.getRol();
				rol = rolDao.findById(rol.getIdrol());
				if(rol!=null){
					competencia.setRol(rol);
					RolGestionCompetenciaDAO gcDAO = new RolGestionCompetenciasDAOImpl(RolGestionCompetencia.class);
					try{
						gcDAO.agregar(competencia);
						contador++;
					}catch(NotariaException e){
						if(e.getLocalizedMessage().startsWith("20001")){
							System.out.println("=====> Esta competencia ya se encuentra registrada, no es posible registrarla nuevamente.");
						} else {
							throw e;
						}
					}
				} else {
					System.out.printf("=====> No se ha localizado el elemento Rol referenciado en la competencia, no se puede crear %s.", competencia.getRol()!=null?competencia.getRol().getIdrol():"null");
				}
			}
		} else {
			System.out.println("=====> No se encontraron elementos para procesar el registro de Compentencias en el Rol.");
		}
		return contador;		
	}

	@Override
	public int actualizaModoCompetencia(String identificador, int modo)
			throws NotariaException {
		RolGestionCompetenciaDAO gcDAO = new RolGestionCompetenciasDAOImpl(RolGestionCompetencia.class);
		RolGestionCompetencia compentencia = gcDAO.actualizarModo(identificador, modo);
		return compentencia.getModo();
	}

	@Override
	public int validaCompetencia(Rol rol, String pantalla)
			throws NotariaException {
		rol = rolDao.findById(rol.getIdrol());
		if(rol!=null){		
			RolGestionCompetenciaDAO gcDAO = new RolGestionCompetenciasDAOImpl(RolGestionCompetencia.class);
			return gcDAO.compentenciaPantalla(rol.getIdrol(), pantalla);		
		} else {
			System.out.printf("=====> No se ha localizado el elemento Rol referenciado en la competencia, no se puede crear.");
			return 0;
		}
	}
	
	@Override
	public Rol rolByPrefijo(String prefijo) throws NotariaException{
		return rolDao.rolByPrefijo(prefijo);
	}

}
