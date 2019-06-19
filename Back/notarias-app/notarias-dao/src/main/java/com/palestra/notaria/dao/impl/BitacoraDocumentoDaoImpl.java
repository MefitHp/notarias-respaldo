package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.BitacoraDocumentoDao;
import com.palestra.notaria.modelo.BitacoraDocumento;

public class BitacoraDocumentoDaoImpl extends GenericDaoImpl<BitacoraDocumento, Integer> implements
		BitacoraDocumentoDao {

	public BitacoraDocumentoDaoImpl(){
		super(BitacoraDocumento.class);
	}
}
