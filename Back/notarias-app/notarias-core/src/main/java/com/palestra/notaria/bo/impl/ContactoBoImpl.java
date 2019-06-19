package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.ContactoBo;
import com.palestra.notaria.dao.ContactoDao;
import com.palestra.notaria.dao.impl.ContactoDaoImpl;
import com.palestra.notaria.exceptions.NotariaException;
import com.palestra.notaria.modelo.Contacto;

public class ContactoBoImpl extends GenericBoImpl<Contacto> implements ContactoBo {
	
	private ContactoDao contactoDao;
	
	public ContactoBoImpl(){
		this.contactoDao = new ContactoDaoImpl();
		super.dao = this.contactoDao;
	}
	
	public Contacto buscarXpersona(String idpersona) throws NotariaException{
				return this.contactoDao.findByPersona(idpersona);
	}

}
