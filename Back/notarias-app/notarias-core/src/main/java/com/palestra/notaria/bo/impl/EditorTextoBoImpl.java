package com.palestra.notaria.bo.impl;

import com.palestra.notaria.bo.EditorTextoBo;
import com.palestra.notaria.bo.GenericBo;
import com.palestra.notaria.dao.EditorTextoDao;
import com.palestra.notaria.dao.impl.EditorTextoDaoImpl;
import com.palestra.notaria.modelo.EditorTexto;

public class EditorTextoBoImpl extends GenericBoImpl<EditorTexto> implements
		GenericBo<EditorTexto>, EditorTextoBo {

	private EditorTextoDao editorDao;
	
	public EditorTextoBoImpl(){
		this.editorDao = new EditorTextoDaoImpl();
		super.dao = this.editorDao;
	}
}
