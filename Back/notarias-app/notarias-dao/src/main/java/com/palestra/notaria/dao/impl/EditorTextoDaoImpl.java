package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.EditorTextoDao;
import com.palestra.notaria.modelo.EditorTexto;

public class EditorTextoDaoImpl extends GenericDaoImpl<EditorTexto, Integer>
		implements EditorTextoDao {

	public EditorTextoDaoImpl(){
		super(EditorTexto.class);
	}
}
