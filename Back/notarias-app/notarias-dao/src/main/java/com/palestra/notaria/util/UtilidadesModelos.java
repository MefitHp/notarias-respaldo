package com.palestra.notaria.util;

import org.hibernate.Hibernate;
import org.hibernate.proxy.HibernateProxy;

public class UtilidadesModelos {

	public UtilidadesModelos() {
		// TODO Auto-generated constructor stub
	}

	public static <T> T inicializaObjeto(T entity) {
	    if (entity != null) {
	        Hibernate.initialize(entity);
		    if (entity instanceof HibernateProxy) {
		        entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer()
		                .getImplementation();
		    }
		    
	    }
	    
	    return entity;

	    
	}
	
}
