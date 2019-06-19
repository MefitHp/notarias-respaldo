package com.palestra.notaria.uif.core.bo;

import com.palestra.notaria.uif.core.dao.PersonaDao;
import com.palestra.notaria.uif.core.dao.PersonaDaoImp;
import com.palestra.notaria.uif.core.models.Persona;
import com.palestra.notaria.uif.exceptions.NotariaException;

public class PersonaBo {

	private  PersonaDao personadao;
	
	public PersonaBo() {
		personadao = new PersonaDaoImp();// TODO Auto-generated constructor stub
	}
	
	public  Persona findById(String id) throws NotariaException{
		Persona respuesta = this.personadao.findById(id);
		return respuesta;
	}
	
	

}
