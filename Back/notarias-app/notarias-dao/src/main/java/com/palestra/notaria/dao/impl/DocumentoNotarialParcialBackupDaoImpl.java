package com.palestra.notaria.dao.impl;

import com.palestra.notaria.dao.DocumentoNotarialParcialBackupDao;
import com.palestra.notaria.modelo.DocumentoNotarialParcialBackup;

public class DocumentoNotarialParcialBackupDaoImpl extends
		GenericDaoImpl<DocumentoNotarialParcialBackup, Integer> implements
		DocumentoNotarialParcialBackupDao {

	public DocumentoNotarialParcialBackupDaoImpl(){
		super(DocumentoNotarialParcialBackup.class);
	}
}
