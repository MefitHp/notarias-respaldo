package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.BitacoraDocumentoBo;
import com.palestra.notaria.dao.BitacoraDocumentoDao;
import com.palestra.notaria.dao.impl.BitacoraDocumentoDaoImpl;
import com.palestra.notaria.modelo.BitacoraDocumento;

public class BitacoraDocumentoBoImpl extends GenericBoImpl<BitacoraDocumento> implements BitacoraDocumentoBo{

	private BitacoraDocumentoDao bitacoraDocumentoDao;
	
	public BitacoraDocumentoBoImpl() {
		this.bitacoraDocumentoDao = new BitacoraDocumentoDaoImpl();
		super.dao = this.bitacoraDocumentoDao;
	}
}
